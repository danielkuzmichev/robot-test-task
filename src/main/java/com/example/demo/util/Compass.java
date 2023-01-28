package com.example.demo.util;

import java.util.ArrayList;
import java.util.Arrays;

import static com.example.demo.util.Direction.*;

/**
 * Описание логики при изменении одного направления из четырех сторон света при повороте.
 *
 * @see Direction
 */
public final class Compass {

    /**
     * Список сторон света с учетом их фиксированного порядка.
     */
    private static final ArrayList<Direction> list = new ArrayList<>(Arrays.asList(NORTH, EAST, SOUTH, WEST));

    /**
     * Получение направления при повороте вправо относительно заданной стороны света.
     *
     * @param direction заданная сторона света
     * @return направление при повороте вправо, если указанная сторона есть в компасе, иначе {@code null}
     */
    public static String spinRight(String direction) {
        if (list.contains(Direction.valueOfTitle(direction))) {
            return list.get((list.indexOf(Direction.valueOfTitle(direction)) + 1) % 4).getTitle();
        }
        return null;
    }

    /**
     * Получение направления при повороте влево относительно заданной стороны света.
     *
     * @param direction заданная сторона света
     * @return направление при повороте влево, если указанная сторона есть в компасе, иначе {@code null}
     */
    public static String spinLeft(String direction) {
        if (list.contains(Direction.valueOfTitle(direction))) {
            return list.get((list.indexOf(Direction.valueOfTitle(direction)) + 3) % 4).getTitle();
        }
        return null;
    }

    private Compass() {
    }
}
