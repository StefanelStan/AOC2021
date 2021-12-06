package com.ss.aoc;

import java.util.ArrayList;
import java.util.List;

public class Day06 extends AOCDay {
    @Override
    long task1(boolean isTest) {
        String line = getDataAsString(isTest);
        long[] fishStates = getFish(line);
        spawnFish(80, fishStates);
        long total = 0;
        for (long fishState : fishStates) {
            total += fishState;
        }
        return total;
    }

    private void spawnFish(int days, long[] fishStates) {
        long new8;
        while (days > 0) {
            new8 = fishStates[0];
            System.arraycopy(fishStates, 1, fishStates, 0, fishStates.length - 1);
            fishStates[6] += new8;
            fishStates[8] = new8;
            days--;
        }
    }

    private long[] getFish(String line) {
        long[] fish = new long[9];
        for (int i = 0; i < line.length(); i += 2) {
            fish[line.charAt(i) - '0']++;
        }
        return fish;
    }

    @Override
    long task2(boolean isTest) {
        String line = getDataAsString(isTest);
        long[] fishStates = getFish(line);
        spawnFish(256, fishStates);
        long total = 0;
        for (long fishState : fishStates) {
            total += fishState;
        }
        return total;
    }
}
