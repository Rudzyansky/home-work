package com.sbrf.reboot.calculator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CalculatorTest {

    @Test
    void getAddition() {
        assertEquals(9, new Calculator().getAddition(4, 5));
    }

    @Test
    void getSubtraction() {
        assertEquals(-1, new Calculator().getSubtraction(4, 5));
    }

    @Test
    void getMultiplication() {
        assertEquals(20, new Calculator().getMultiplication(4, 5));
    }

    @Test
    void getDivision() {
        assertEquals(3, new Calculator().getDivision(9, 3));
    }

    @Test
    void getRemainder() {
        assertEquals(-2, new Calculator().getRemainder(-5, -3));
    }

    @Test
    void getXor() {
        assertEquals(6, new Calculator().getXor(5, 3));
    }

    @Test
    void getPowerOfTwo() {
        assertEquals(65536, new Calculator().getPowerOfTwo(16));
    }

    @Test
    void classHasSevenMethods() {
        assertEquals(7, Calculator.class.getMethods().length - Object.class.getMethods().length);
    }
}