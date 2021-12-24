package com.sbrf.reboot.calculator;

public class Calculator {
    public int getAddition(int a, int b) {
        return a + b;
    }

    public int getSubtraction(int a, int b) {
        return a - b;
    }

    public int getMultiplication(int a, int b) {
        return a * b;
    }

    public int getDivision(int a, int b) {
        return a / b;
    }

    public int getRemainder(int a, int b) {
        return a % b;
    }

    public int getXor(int a, int b) {
        return a ^ b;
    }

    public int getPowerOfTwo(int exp) {
        return 1 << exp;
    }
}
