package com.epam.gomel.lib.common;

public class Test {
    private static String name;
    private static String suitName;
    private static String startDate;

    public static String getStartDate() {
        return startDate;
    }

    public static void setStartDate(String startDate) {
        Test.startDate = startDate;
    }

    public static String getSuitName() {
        return suitName;
    }

    public static void setSuitName(String suitName) {
        Test.suitName = suitName;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        Test.name = name;
    }
}
