package com.example.demo.util;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RobotWalkingThreadTest {

    /**
     * Проверка выполнения команд для управления роботом.
     * <p>
     * Проверяются команды из перечисления {@link Action Action}.
     */
    @Test
    void testInterpretCommand() {
        interpretGoCommandTestCase();
        interpretLeftCommandTestCase();
        interpretRightCommandTestCase();
    }

    /**
     * Проверка получения известных роботу команд из строки.
     * <p>
     *  Используются команды из перечисления {@link Action Action}.
     */
    @Test
    void testGetCommandsFromString() throws NoSuchMethodException {
        getCommandsFromCorrectString();
        getCommandsFromStringWithDifferentCase();
        getCommandsFromIncorrectStringWithUnknowedLetters();
        getCommandsFromIncorrectBlankedString();
        getCommandsFromIncorrectEmptyString();
    }

    private void getCommandsFromIncorrectEmptyString() {
        try {
            RobotWalkingThread robotWalkingThread = new RobotWalkingThread();
            String commandLine = " ";
            ArrayList<String> expectedArray = new ArrayList<>();
            Method method = RobotWalkingThread.class.getDeclaredMethod("getCommandsFromString",String.class);
            method.setAccessible(true);
            assertEquals(expectedArray,method.invoke(robotWalkingThread,commandLine));
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e){
            e.printStackTrace();
        }
    }

    private void getCommandsFromIncorrectBlankedString() {
        try {
            RobotWalkingThread robotWalkingThread = new RobotWalkingThread();
            String commandLine = "";
            ArrayList<String> expectedArray = new ArrayList<>();
            Method method = RobotWalkingThread.class.getDeclaredMethod("getCommandsFromString",String.class);
            method.setAccessible(true);
            assertEquals(expectedArray,method.invoke(robotWalkingThread,commandLine));
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e){
            e.printStackTrace();
        }
    }

    private void getCommandsFromStringWithDifferentCase() {
        try {
            RobotWalkingThread robotWalkingThread = new RobotWalkingThread();
            String commandLine = "GrL";
            ArrayList<String> expectedArray = new ArrayList<>(Arrays.asList("G","R","L"));
            Method method = RobotWalkingThread.class.getDeclaredMethod("getCommandsFromString",String.class);
            method.setAccessible(true);
            assertEquals(expectedArray,method.invoke(robotWalkingThread,commandLine));
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e){
            e.printStackTrace();
        }
    }

    private void getCommandsFromIncorrectStringWithUnknowedLetters() {
        try {
            RobotWalkingThread robotWalkingThread = new RobotWalkingThread();
            String commandLine = "GOR";
            ArrayList<String> expectedArray = new ArrayList<>(Arrays.asList("G","R"));
            Method method = RobotWalkingThread.class.getDeclaredMethod("getCommandsFromString",String.class);
            method.setAccessible(true);
            assertEquals(expectedArray,method.invoke(robotWalkingThread,commandLine));
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e){
            e.printStackTrace();
        }
    }

    private void getCommandsFromCorrectString() {
        try {
            RobotWalkingThread robotWalkingThread = new RobotWalkingThread();
            String commandLine = "GLR";
            ArrayList<String> expectedArray = new ArrayList<>(Arrays.asList("G","L","R"));
            Method method = RobotWalkingThread.class.getDeclaredMethod("getCommandsFromString",String.class);
            method.setAccessible(true);
            assertEquals(expectedArray,method.invoke(robotWalkingThread,commandLine));
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e){
            e.printStackTrace();
        }
    }

    void interpretGoCommandTestCase(){
        RobotWalkingThread rwt = new RobotWalkingThread(new Robot());
        rwt.interpretCommand(Action.GO);
        assertEquals(0,rwt.getPosition().getX());
        assertEquals(1,rwt.getPosition().getY());
        assertEquals("N",rwt.getDirection());
    }

    void interpretLeftCommandTestCase(){
        RobotWalkingThread rwt = new RobotWalkingThread(new Robot());
        rwt.interpretCommand(Action.LEFT);
        assertEquals(0, rwt.getPosition().getX());
        assertEquals(0, rwt.getPosition().getY());
        assertEquals("W", rwt.getDirection());
    }

    void interpretRightCommandTestCase(){
        RobotWalkingThread rwt = new RobotWalkingThread(new Robot());
        rwt.interpretCommand(Action.RIGHT);
        assertEquals(0, rwt.getPosition().getX());
        assertEquals(0, rwt.getPosition().getY());
        assertEquals("E", rwt.getDirection());
    }
}