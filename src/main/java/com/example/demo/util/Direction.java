package com.example.demo.util;

import java.util.Arrays;

public enum Direction {
    NORTH("N"),
    EAST("E"),
    SOUTH("S"),
    WEST("W");

    private final String title;

    Direction(String name) {
        this.title = name;
    }

    public String getTitle() {
        return title;
    }

    public static Direction valueOfTitle(String string){
        return Arrays.stream(values())
                .filter((Direction d) -> d.title.equals(string) || d.name().equals(string))
                .findAny()
                .orElse(null);
    }
}
