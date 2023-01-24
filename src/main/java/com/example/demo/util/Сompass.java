package com.example.demo.util;

import java.util.ArrayList;
import java.util.Arrays;

public final class Сompass {

    static final char NORTH = 'N';
    static final char SOUTH = 'S';
    static final char EAST = 'E';
    static final char WEST = 'W';
    private static final ArrayList<Character> list = new ArrayList<>(Arrays.asList(NORTH,EAST,SOUTH,WEST));

    public static char spinRight(char direction) {
        return list.get((list.indexOf(direction)+1)%4);
    }

    public static char spinLeft(char direction) {
        return list.get((list.indexOf(direction)+3)%4);
    }

    private Сompass() {
    }
}
