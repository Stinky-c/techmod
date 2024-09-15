package com.buckydev.techmod.utils;

/**
 * I wanted cool simple things for already simple things
 */
public class Mth {

    /**
     * Inclusive range check
     */
    static boolean contains(int value, int lower, int upper) {
        return value >= lower && value <= upper;
    }
    /**
     * Non-inclusive range check
     */
    static boolean between(int value, int lower, int upper) {
        return value > lower && value < upper;
    }
}
