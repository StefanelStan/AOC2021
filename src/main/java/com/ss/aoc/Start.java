package com.ss.aoc;

public class Start {
    public static void main(String[] args) {
        Start start = new Start();

//        start.day01();
//        start.day02();
        start.day03();
    }

    private void day01() {
        Day01 day01 = new Day01();
//        System.out.println(day01.task1(true));
//        System.out.println(day01.task1(false));
//        System.out.println(day01.task2(true));
        System.out.println(day01.task2(false));
    }

    public void day02() {
        Day02 day02 = new Day02();
//        System.out.println(day02.task1(true));
//        System.out.println(day02.task1(false));
        System.out.println(day02.task2(false));
    }

    public void day03() {
        Day03 day03 = new Day03();

//        System.out.println(day03.task1(true));
//        System.out.println(day03.task1(false));
//        System.out.println(day03.task2(true));
        System.out.println(day03.task2(false));
    }
}
