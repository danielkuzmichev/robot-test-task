package com.example.demo.util;

import org.springframework.stereotype.Component;

import static com.example.demo.util.Сompass.*;

@Component
public class Robot {

    private int x;

    private int y;

    private char direction;

    public Robot() {
        this.x = 0;
        this.y = 0;
        this.direction = NORTH;
    }

    public Robot(int x, int y, char direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void go() {
       switch (this.direction){
           case NORTH -> y++;
           case SOUTH -> y--;
           case EAST -> x++;
           case WEST -> x--;
           default -> {
               break;
           }
       }
    }

    public void turnToLeft() {
       this.direction = spinLeft(this.direction);
    }

    public void turnToRight() {
        this.direction = spinRight(this.direction);
    }

    public void returnToStart() {
        this.x = 0;
        this.y = 0;
        this.direction = NORTH;
    }

    public char getDirection() {
        return direction;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setDirection(char direction) {
        this.direction = direction;
    }
}
