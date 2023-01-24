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


@Component
public class RobotWalkingThread extends Thread {

    private static final RoutePoint startPosition = new RoutePoint(getTimeStamp(),0, 0, 'N');

    private static final char GO = 'G';
    private static final char LEFT = 'L';
    private static final char RIGHT = 'R';
    @Autowired
    private Robot robot;
    private Command commands;

    private boolean circularRoute;

    private List<RoutePoint> routePoints;

    private boolean circularCommand;



    private boolean executing;

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

    @Override
    public void run() {
        this.executing = true;
        while (!isInterrupted()) {
            if (this.executing) {
                RoutePoint cmdStartPos = new RoutePoint(robot.getX(), robot.getY(), robot.getDirection());
                getCommandsFromString(commands.getLine()).forEach((Character c) -> {
                    this.interpretCommand(c);
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

    private boolean checkForCircularCommand(RoutePoint cmdStartPos, Robot robot) {
        return cmdStartPos.getX() != robot.getX() && cmdStartPos.getY() != robot.getY() ||
                cmdStartPos.getDirection() != robot.getDirection();
    }

    public void interpretCommand(char command) {
        switch (command) {
            case GO -> robot.go();
            case LEFT -> robot.turnToLeft();
            case RIGHT -> robot.turnToRight();
            default -> {
                break;
            }
        }
    }

    private void changeRoutePoints() {
        if (getLastRoutePoint().getX() != this.robot.getX() || getLastRoutePoint().getY() != this.robot.getY()) {
            fillRoute(routePoints.get(0).getRouteId(), this.robot, this.commands);
        }
        if (getLastRoutePoint().getDirection() != this.robot.getDirection() && !circularRoute) {
            getLastRoutePoint().setDirection(this.robot.getDirection());
        }
    }

    public Point getPosition() {
        return new Point(this.robot.getX(), this.robot.getY());
    }

    private RoutePoint getLastRoutePoint() {
        return this.routePoints.get(routePoints.size() - 1);
    }

    public void reset() {
        this.robot.returnToStart();
        this.circularRoute = false;
        this.routePoints.clear();
        this.routePoints.add(new RoutePoint(getTimeStamp(), 0, 0, 'N'));
    }

    public boolean isCircularRoute() {
        return circularRoute;
    }

    private boolean checkForCircularRoute(List<RoutePoint> routePoints) {
        return isEqualsState(routePoints.get(0), getLastRoutePoint());
    }

    private boolean isEqualsState(RoutePoint p1, RoutePoint p2) {
        return Objects.equals(p1.getX(), p2.getX()) && Objects.equals(p1.getY(), p2.getY()) && Objects.equals(p1.getDirection(), p2.getDirection());
    }

    public void setUpNewCommand(Command command) {
        this.circularRoute = false;
        this.commands = command;
        this.executing = true;
    }

    private List<Character> getCommandsFromString(String string) {
        return string.toUpperCase().chars().mapToObj(c -> (char) c).toList();
    }

    private void fillRoute(Long routeId, Robot robot, Command cmd) {
        this.routePoints.add(new RoutePoint(routeId, robot.getX(), robot.getY(), robot.getDirection(), cmd));
    }

    @PostConstruct
    public void init() {
        RoutePoint lastRoutePoints = routePointsRepository.findFirstByOrderByIdDesc();
        if (lastRoutePoints != null) {
            this.robot.setX(lastRoutePoints.getX());
            this.robot.setY(lastRoutePoints.getY());
            this.robot.setDirection(lastRoutePoints.getDirection());
            this.routePoints = new CopyOnWriteArrayList<>(List.of(new RoutePoint(lastRoutePoints.getRouteId(),
                    lastRoutePoints.getX(),
                    lastRoutePoints.getY(),
                    lastRoutePoints.getDirection(),
                    lastRoutePoints.getCmd())));
        } else {
            this.routePoints = new CopyOnWriteArrayList<>(List.of(startPosition));
        }
    }

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

    public Character getDirection() {
        return this.robot.getDirection();
    }

    private static long getTimeStamp(){
        return (new Date()).getTime();
    }
}
