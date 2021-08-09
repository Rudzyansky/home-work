package com.sbrf.reboot.calculator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CalculatorTest {

    @Test
    void getAddition() {
        Assertions.assertEquals(9, Calculator.getAddition(4, 5));
    }

    @Test
    void getSubtraction() {
        Assertions.assertEquals(-1, Calculator.getSubtraction(4, 5));

    }

    @Test
    void getMultiplication() {
        Assertions.assertEquals(20, Calculator.getMultiplication(4, 5));

    }

    @Test
    void getDivision() {
        Assertions.assertEquals(3, Calculator.getDivision(9, 3));

    }

    @Test
    void classHasSevenMethods(){
        Assertions.assertEquals(7,Calculator.class.getMethods().length-Object.class.getMethods().length);
    }
}