package com.example.demo.util;

import org.springframework.stereotype.Component;

import static com.example.demo.util.Direction.*;
import static com.example.demo.util.Compass.spinLeft;
import static com.example.demo.util.Compass.spinRight;


@Component
public class Robot {

    private int x;

    private int y;

    private String direction;

    public Robot() {
        this.x = 0;
        this.y = 0;
        this.direction = NORTH.getTitle();
    }

    public Robot(int x, int y, String direction) {
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
       switch (Direction.valueOfTitle(this.direction)){
           case NORTH -> y++;
           case SOUTH -> y--;
           case EAST -> x++;
           case WEST -> x--;
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
        this.direction = NORTH.getTitle();
    }

    public String getDirection() {
        return direction;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}
