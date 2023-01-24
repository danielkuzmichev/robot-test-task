package com.example.demo.services;

import com.example.demo.entities.Command;
import com.example.demo.repositories.CommandRepository;
import com.example.demo.repositories.RoutePointRepository;
import com.example.demo.util.RobotWalkingThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RobotService {
    @Autowired
    private RobotWalkingThread robotWalkingThread;

    @Autowired
    private CommandRepository cmdRep;

    @Autowired
    private RoutePointRepository routePointsRep;

    public void endRobotWalking() {
        this.robotWalkingThread.stopWalking();
        this.routePointsRep.saveAll(robotWalkingThread.getRoutePoints());
        this.robotWalkingThread.reset();
    }

    public void insertCommand(String cmdLine) {
        Command cmd = new Command(cmdLine);
        this.robotWalkingThread.setUpNewCommand(cmd);
        if (!this.robotWalkingThread.isAlive() || this.robotWalkingThread.isInterrupted()) {
            this.robotWalkingThread.start();
        }
        cmdRep.save(cmd);
    }

    public Map<String, String> getRobotPosition() {
        return RobotServiceConverter.getRobotPositionResponseFormat(this.robotWalkingThread.getPosition());
    }

    public Map<String, String> getRobotRoute() {
        return RobotServiceConverter.getRobotRouteResponseFormat(this.robotWalkingThread.getRoutePoints(),
                this.robotWalkingThread.isCircularCommand());
    }
}
