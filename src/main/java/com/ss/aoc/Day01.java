package com.ss.aoc;

import java.util.List;

public class Day01 extends AOCDay {

    public Day01() {
        this.testDataFilename = "day01test.txt";
        this.dataFileName = "day01.txt";
    }

    @Override
    long task1(boolean isTest) {
        List<String> lines = getDataAsStringLines(isTest);
        long increases = 0;
        int prev = Integer.parseInt(lines.get(0));
        int current;
        for (int i = 1; i < lines.size(); i++) {
            current = Integer.parseInt(lines.get(i));
            if (prev < current) {
                increases++;
            }
            prev = current;
        }
        return increases;
    }

    @Override
    long task2(boolean isTest) {
        List<String> lines = getDataAsStringLines(isTest);
        int increases = 0;
        int prevSum = Integer.parseInt(lines.get(0)) + Integer.parseInt(lines.get(1)) + Integer.parseInt(lines.get(2));
        int currSum;
        for (int i = 1; i < lines.size() -2; i++) {
            currSum = Integer.parseInt(lines.get(i)) + Integer.parseInt(lines.get( i + 1)) + Integer.parseInt(lines.get(i + 2));
            if (currSum > prevSum) {
                increases++;
            }
            prevSum = currSum;
        }
        return increases;
    }
}
