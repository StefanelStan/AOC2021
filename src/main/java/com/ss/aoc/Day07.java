package com.ss.aoc;

import java.util.HashMap;
import java.util.Map;

public class Day07 extends AOCDay {

    Map<Integer, Integer> stepsCosts = new HashMap<>();

    @Override
    long task1(boolean isTest) {
        String line = getDataAsString(isTest);
        int[] nrs = getNrs(line);
        int absMinCost = Integer.MAX_VALUE;
        int tempCost;
        for (int i = nrs[nrs.length -2]; i <= nrs[nrs.length -1]; i++) {
            tempCost = 0;
            for (int j = 0; j < nrs.length - 2; j++) {
                tempCost += Math.abs(nrs[j] - i);
            }
            absMinCost = Math.min(tempCost, absMinCost);
        }
        return absMinCost;
    }

    private int[] getNrs(String line) {
        String[] chunks = line.split(",");
        int[] nrs = new int[chunks.length + 2];
        nrs[nrs.length -2] = Integer.MAX_VALUE;
        for (int i= 0; i < chunks.length; i++) {
            nrs[i] = Integer.parseInt(chunks[i]);
            nrs[nrs.length - 2] = Math.min(nrs[nrs.length -2], nrs[i]);
            nrs[nrs.length - 1] = Math.max(nrs[nrs.length -1], nrs[i]);
        }
        return nrs;
    }

    @Override
    long task2(boolean isTest) {
        stepsCosts.put(0,0);
        stepsCosts.put(1,1);
        String line = getDataAsString(isTest);
        int[] nrs = getNrs(line);
        int absMinCost = Integer.MAX_VALUE;
        int tempCost;
        for (int i = nrs[nrs.length -2]; i <= nrs[nrs.length -1]; i++) {
            tempCost = 0;
            for (int j = 0; j < nrs.length - 2; j++) {
                tempCost += calculateCostOf(Math.abs(nrs[j] - i));
            }
            absMinCost = Math.min(tempCost, absMinCost);
        }
        return absMinCost;
    }

    private int calculateCostOf(int nrOfSteps) {
        if (!stepsCosts.containsKey(nrOfSteps)) {
            int sum = (1 + nrOfSteps) * (nrOfSteps/ 2);
            if (nrOfSteps % 2 == 1) {
                sum += (1 + nrOfSteps) / 2;
            }
            stepsCosts.put(nrOfSteps, sum);
        }
        return stepsCosts.get(nrOfSteps);

    }
}
