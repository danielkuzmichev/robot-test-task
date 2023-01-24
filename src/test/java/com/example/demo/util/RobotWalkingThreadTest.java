package com.example.demo.util;

import com.example.demo.entities.Command;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RobotWalkingThreadTest {

    @Test
    void testInterpretCommand() {
        interpretCommandTestCase1();
        interpretCommandTestCase2();
        interpretCommandTestCase3();
        interpretCommandTestCase4();
    }

    void interpretCommandTestCase1(){
        RobotWalkingThread rwt = new RobotWalkingThread(new Robot());
        rwt.interpretCommand('G');
        assertEquals(0,rwt.getPosition().getX());
        assertEquals(1,rwt.getPosition().getY());
        assertEquals('N',rwt.getDirection());
    }

    void interpretCommandTestCase2(){
        RobotWalkingThread rwt = new RobotWalkingThread(new Robot());
        rwt.interpretCommand('O');
        assertEquals(0, rwt.getPosition().getX());
        assertEquals(0, rwt.getPosition().getY());
        assertEquals('N', rwt.getDirection());
    }

    void interpretCommandTestCase3(){
        RobotWalkingThread rwt = new RobotWalkingThread(new Robot());
        rwt.interpretCommand('L');
        assertEquals(0, rwt.getPosition().getX());
        assertEquals(0, rwt.getPosition().getY());
        assertEquals('W', rwt.getDirection());
    }

    void interpretCommandTestCase4(){
        RobotWalkingThread rwt = new RobotWalkingThread(new Robot());
        rwt.interpretCommand('R');
        assertEquals(0, rwt.getPosition().getX());
        assertEquals(0, rwt.getPosition().getY());
        assertEquals('E', rwt.getDirection());
    }
}