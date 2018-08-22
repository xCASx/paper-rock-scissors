package ru.itskills.prs.util;

public class NumberUtil {

    /**
     * Null object for non-negative numbers
     */
    public static final int UNKNOWN = -1;

    public static boolean isUnknown(int number) {
        return number == UNKNOWN;
    }
}
