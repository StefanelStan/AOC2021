package com.ss.aoc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Day09 extends AOCDay {
    @Override
    long task1(boolean isTest) {
        List<String> list = getDataAsStringLines(isTest);
        return getLowestPoints(list);
    }

    private long getLowestPoints(List<String> lines) {
        int lowestPoints = 0;
        char ch;
        for (int i = 0; i < lines.size(); i++) {
            for (int j = 0; j < lines.get(i).length(); j++) {
                ch = lines.get(i).charAt(j);
                if (isLowerThan(ch, i-1, j, lines)
                    && isLowerThan(ch, i, j + 1, lines)
                    && isLowerThan(ch, i + 1, j, lines)
                    && isLowerThan(ch, i, j - 1, lines)) {
                    lowestPoints += (ch - '0') + 1;
                }
            }
        }
        return lowestPoints;
    }

    private boolean isLowerThan(char ch, int i, int j, List<String> lines) {
        if (i < 0 || i >= lines.size() || j < 0 || j >= lines.get(0).length()) {
            return true;
        } else  {
            return ch < lines.get(i).charAt(j);
        }
    }


    @Override
    long task2(boolean isTest) {
        List<String> list = getDataAsStringLines(isTest);
        boolean[][] visited = new boolean[list.size()][list.get(0).length()];
        List<Integer> basinSizes = getBasins(list, visited);
        Collections.sort(basinSizes);
        return (long) basinSizes.get(basinSizes.size() - 1) * basinSizes.get(basinSizes.size() -2) * basinSizes.get(basinSizes.size() -3);
    }

    private List<Integer> getBasins(List<String> lines, boolean[][] visited) {
        List<Integer> basins = new ArrayList<>();
        char ch;
        for (int i = 0; i < lines.size(); i++) {
            for (int j = 0; j < lines.get(i).length(); j++) {
                ch = lines.get(i).charAt(j);
                if (isLowerThan(ch, i-1, j, lines)
                    && isLowerThan(ch, i, j + 1, lines)
                    && isLowerThan(ch, i + 1, j, lines)
                    && isLowerThan(ch, i, j - 1, lines)) {
                    basins.add(getBasinSize(ch, i, j, lines, visited));
                }
            }
        }
        return basins;
    }

    private Integer getBasinSize(char ch, int i, int j, List<String> lines, boolean[][] visited) {
        if ( i < 0 || j < 0 || i >= lines.size() || j >= lines.get(0).length() || visited[i][j]) {
            return 0;
        }
        int size = 0;
        visited[i][j] = true;
        if (lines.get(i).charAt(j) != '9' && ch <= lines.get(i).charAt(j)) {
            size++;
            size += getBasinSize(ch, i-1, j, lines, visited);
            size += getBasinSize(ch, i, j + 1, lines, visited);
            size += getBasinSize(ch, i + 1, j, lines, visited);
            size += getBasinSize(ch, i, j -1, lines, visited);
        }
        return size;
    }
}
