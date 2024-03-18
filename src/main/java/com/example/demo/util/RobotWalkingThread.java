package com.example.demo.util;

import com.example.demo.entities.Command;
import com.example.demo.entities.RoutePoint;
import com.example.demo.repositories.RoutePointRepository;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.example.demo.util.Direction.NORTH;

/**
 * Класс, описывающий перемещение робота во время выполнения программы.
 * <p>
 * В классе содержатся методы по управлению роботом, получению информации
 * о состоянии робота, маршрута и о проделанном им пути, а также вспомогательные методы.
 *
 * @see Robot Robot
 */
@Component
public class RobotWalkingThread extends Thread {
    /**
     * Стартовая позиция по умолчанию
     */
    private static final RoutePoint startPosition = new RoutePoint(getTimeStamp(), 0, 0, NORTH.getTitle());
    /**
     * Перемещающийся робот, описанный в классе {@link Robot Robot}
     */
    @Autowired
    private Robot robot;

    /**
     * Команда, циклично выполняющаяся роботом, описанная в классе {@link Command Command}.
     */
    private Command commands;

    /**
     * Флаг зацикливания маршрута.
     */
    private boolean circularRoute;

    /**
     * Список точек пройденных роботом
     */
    private List<RoutePoint> routePoints;

    /**
     * Флаг команды, приводящей к циклу.
     */
    private boolean circularCommand;

    /**
     * Флаг выполнения команд перемещения робота.
     */
    private boolean executing;

    /**
     * Репозиторий для получение и сохранения данных БД.
     */
    @Autowired
    private RoutePointRepository routePointsRepository;

    public RobotWalkingThread() {
    }

    public RobotWalkingThread(Robot robot) {
        this.robot = robot;
    }

    public boolean isCircularCommand() {
        return circularCommand;
    }

    public List<RoutePoint> getRoutePoints() {
        return routePoints;
    }

    /**
     * Поток цикличного выполнения команд по перемещению робота.
     * <p>
     * При запуске потока циклично выполняется последовательность действий из команды
     * ({@link RobotWalkingThread#commands commands}), пока либо поток не будет прерван
     * с помощью метода {@link Thread#interrupt() interrupt()}, либо флаг
     * выполнения не будет сброшен ({@link RobotWalkingThread#executing executing}).
     */
    @Override
    public void run() {
        this.executing = true;
        while (!isInterrupted()) {
            if (this.executing) {
                RoutePoint cmdStartPos = new RoutePoint(robot.getX(), robot.getY(), robot.getDirection());
                getCommandsFromString(commands.getLine()).forEach((String c) -> {
                    this.interpretCommand(Action.valueOfTitle(c));
                    this.changeRoutePoints();
                });
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    this.executing = false;
                }
                this.circularCommand = checkForCircularCommand(cmdStartPos, this.robot);
                this.circularRoute = checkForCircularRoute(this.routePoints);
            }
        }
    }

    /**
     * Проверка команды на зацикливание.
     * <p>
     * Проверяет команду на основании начального положения робота.
     * Команда является цикличной, когда после ее выполнения либо не изменилось положение робота,
     * либо изменилось направление робота.
     *
     * @param cmdStartPos начальное положение робота до выполнения команды
     * @param robot экземпляр робота
     * @return {@code true} при зацикленной команде
     */
    private boolean checkForCircularCommand(RoutePoint cmdStartPos, Robot robot) {
        return cmdStartPos.getX() == robot.getX() && cmdStartPos.getY() == robot.getY() ||
                !Objects.equals(cmdStartPos.getDirection(), robot.getDirection());
    }

    /**
     * Выполнение действия роботом при заданной команде.
     *
     * @param command действие команды
     */
    public void interpretCommand(Action command) {
        switch (command) {
            case GO -> robot.go();
            case LEFT -> robot.turnToLeft();
            case RIGHT -> robot.turnToRight();
        }
    }

    /**
     * Изменение списка точек маршрута ({@link RobotWalkingThread#routePoints routePoints}.
     * <p>
     * Добавляет новую точку, если изменилось текущее положение робота.
     * Изменяет направление у последней точки маршрута при повороте робота на месте.
     */
    private void changeRoutePoints() {
        if (getLastRoutePoint().getX() != this.robot.getX() || getLastRoutePoint().getY() != this.robot.getY()) {
            fillRoute(routePoints.get(0).getRouteId(), this.robot, this.commands);
        }
        if (!Objects.equals(getLastRoutePoint().getDirection(), this.robot.getDirection()) && !circularRoute) {
            getLastRoutePoint().setDirection(this.robot.getDirection());
        }
    }

    public Point getPosition() {
        return new Point(this.robot.getX(), this.robot.getY());
    }

    private RoutePoint getLastRoutePoint() {
        return this.routePoints.get(routePoints.size() - 1);
    }

    /**
     * Перевод робота в изначальное положение, начало нового маршрута.
     */
    public void reset() {
        this.robot.returnToStart();
        this.circularRoute = false;
        this.routePoints.clear();
        this.routePoints.add(new RoutePoint(getTimeStamp(), 0, 0, NORTH.getTitle()));
    }

    public boolean isCircularRoute() {
        return circularRoute;
    }

    private boolean checkForCircularRoute(List<RoutePoint> routePoints) {
        return isEqualsState(routePoints.get(0), getLastRoutePoint());
    }

    private boolean isEqualsState(RoutePoint p1, RoutePoint p2) {
        return Objects.equals(p1.getX(), p2.getX()) && Objects.equals(p1.getY(), p2.getY()) &&
                Objects.equals(p1.getDirection(), p2.getDirection());
    }

    /**
     * Установка новой команды.
     *
     * @param command строка команды с последовательностью действий
     */
    public void setUpNewCommand(Command command) {
        this.circularRoute = false;
        this.commands = command;
        this.executing = true;
    }

    /**
     * Получение списка действий известных роботу из строки.
     *
     * @param string строка команды с последовательностью действий
     * @return список с действиями для робота
     */
    private List<String> getCommandsFromString(String string) {
        return string.toUpperCase()
                .chars()
                .mapToObj(c -> (char) c)
                .map(String::valueOf)
                .filter(Arrays.stream(Action.values()).map(Action::getTitle).toList()::contains)
                .toList();
    }

    private void fillRoute(Long routeId, Robot robot, Command cmd) {
        this.routePoints.add(new RoutePoint(routeId, robot.getX(), robot.getY(), robot.getDirection(), cmd));
    }

    /**
     * Инициализация изначального положения робота.
     * <p>
     * Загружает после вызова конструктора последнюю точку маршрута робота сохраненную в БД.
     * Если последней точки нет, то используется стартовую позицию по умолчанию.
     */
    @PostConstruct
    public void init() {
        RoutePoint lastRoutePoints = routePointsRepository.findFirstByOrderByIdDesc();
        if (lastRoutePoints != null) {
            setRobotPosition(lastRoutePoints);
        } else {
            this.routePoints = new CopyOnWriteArrayList<>(List.of(startPosition));
        }
    }

    private void setRobotPosition(RoutePoint routePoint) {
        this.robot.setX(routePoint.getX());
        this.robot.setY(routePoint.getY());
        this.robot.setDirection(routePoint.getDirection());
        this.routePoints = new CopyOnWriteArrayList<>(List.of(new RoutePoint(routePoint.getRouteId(),
                routePoint.getX(),
                routePoint.getY(),
                routePoint.getDirection(),
                routePoint.getCmd())));
    }

    /**
     * Сохранение пройденного роботом маршрута и остановка потока перед удалением объекта.
     */
    @PreDestroy
    public void destroy() {
        stopWalking();
        interrupt();
        routePointsRepository.saveAll(this.routePoints);
    }

    public void stopWalking() {
        this.executing = false;
    }

    public void setRobot(Robot robot) {
        this.robot = robot;
    }

    public String getDirection() {
        return this.robot.getDirection();
    }

    private static long getTimeStamp() {
        return (new Date()).getTime();
    }
}
