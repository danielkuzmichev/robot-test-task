package com.example.demo.controllers;

import com.example.demo.util.Point;
import com.example.demo.services.RobotService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("robot")
public class RobotController {

    @Autowired
    private RobotService robotService;

    @PostMapping("/reset")
    public ResponseEntity reset() {
        robotService.endRobotWalking();
        return new ResponseEntity("Reset is completed", HttpStatus.OK);
    }

    @PostMapping("/command")
    public ResponseEntity<String> executeCommand(HttpServletRequest request) {
        robotService.insertCommand(request.getParameter("command"));
        return new ResponseEntity("OK", HttpStatus.OK);
    }

    @GetMapping("/position")
    public ResponseEntity<Map<String, Point>> getRobotPosition() {
        return new ResponseEntity(robotService.getRobotPosition(), HttpStatus.OK);
    }

    @GetMapping("/route")
    public ResponseEntity<List> getRobotRoute() {
        return new ResponseEntity(robotService.getRobotRoute(), HttpStatus.OK);
    }
}
