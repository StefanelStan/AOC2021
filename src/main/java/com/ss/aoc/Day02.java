package com.ss.aoc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day02 extends AOCDay {

    @Override
    long task1(boolean isTest) {
        int depth = 0;
        int hPos = 0;
        List<String> lines = getDataAsStringLines(isTest);
        for (String line : lines) {
            String[] chunk = line.split(" ");
            if (chunk[0].equals("forward")) {
                hPos += Integer.parseInt(chunk[1]);
            } else {
                depth += (getSign(chunk[0]) * Integer.parseInt(chunk[1]));
            }
        }
        return ((long) depth * hPos);
    }

    @Override
    long task2(boolean isTest) {
        int depth = 0;
        int hPos = 0;
        int aim = 0;
        List<String> lines = getDataAsStringLines(isTest);
        for (String line : lines) {
            String[] chunks = line.split(" ");
            if (!chunks[0].equals("forward")) {
                aim += (getSign(chunks[0]) * Integer.parseInt(chunks[1]));
            } else { // forward
                hPos += Integer.parseInt(chunks[1]);
                depth += aim * Integer.parseInt(chunks[1]);
            }
        }
        return ((long) depth * hPos);
    }

    private int getSign(String direction) {
        return direction.equals("up") ? -1 : 1;
    }
}
