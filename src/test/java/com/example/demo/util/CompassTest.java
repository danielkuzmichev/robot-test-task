package com.example.demo.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CompassTest {

    /**
     * Проверка получения направления компаса при повороте вправо относительно указанного направления.
     */
    @Test
    void testSpinRight() {
        testSpinRightCorrectCase();
        testSpinRightIncorrectCase();
        testSpinRightIncorrectNullCase();
    }

    /**
     * Проверка получения направления компаса при повороте влево относительно указанного направления.
     */
    @Test
    void testSpinLeft() {
        testSpinLeftCorrectCase();
        testSpinLeftIncorrectCase();
        testSpinLeftIncorrectNullCase();
    }

    private void testSpinLeftIncorrectNullCase() {
        assertNull(Compass.spinLeft(null));
    }

    private void testSpinLeftIncorrectCase() {
        assertNull(Compass.spinLeft("11"));
    }

    private void testSpinLeftCorrectCase() {
        assertEquals("W", Compass.spinLeft("N"));
    }

    private void testSpinRightIncorrectNullCase() {
        assertNull(Compass.spinRight(null));
    }

    private void testSpinRightIncorrectCase() {
        assertNull(Compass.spinRight("Запад"));
    }

    private void testSpinRightCorrectCase() {
        assertEquals("N", Compass.spinRight("W"));
    }
}