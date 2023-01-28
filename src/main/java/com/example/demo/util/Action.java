package com.example.demo.util;

import java.util.Arrays;

public enum Action {
    GO("G"),
    LEFT("L"),
    RIGHT("R");

    private final String title;

    Action(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public static Action valueOfTitle(String string) {
        return Arrays.stream(values())
                .filter((Action a) -> a.title.equals(string) || a.name().equals(string))
                .findAny()
                .orElse(null);
    }
}
