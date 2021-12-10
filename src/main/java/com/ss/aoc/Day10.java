package com.ss.aoc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Day10 extends AOCDay {
    private static final Map<Character, Integer> CORRUPTION_POINTS = Map.of(')', 3, ']', 57, '}', 1197, '>', 25137);
    private static final Map<Character, Integer> INCOMPLETE_POINTS = Map.of(')', 1, ']', 2, '}', 3, '>', 4);
    private static final Map<Character, Character> OPENING_CLOSING = Map.of('(',')','[',']','{','}','<','>');
    private static final Map<Character, Character> CLOSING_OPENING = Map.of(')','(',']','[','}','{','>','<');

    @Override
    long task1(boolean isTest) {
        List<String> lines = getDataAsStringLines(isTest);
        List<Character> corruptedChars = new ArrayList<>();
        lines.forEach(line -> addCorruptedChar(line, corruptedChars));
        final long[] corruptionScore = {0};
        corruptedChars.forEach(entry -> corruptionScore[0] += CORRUPTION_POINTS.get(entry));
        return corruptionScore[0];
    }

    private void addCorruptedChar(String line, List<Character> corruptedChars) {
        LinkedList<Integer> openingPositions = new LinkedList<>();
        char ch = 0;
        for (int i = 0; i < line.length(); i++) {
            ch = line.charAt(i);
            if (OPENING_CLOSING.containsKey(ch)) {
                openingPositions.add(i);
            } else {
                if (openingPositions.isEmpty()) {
                    corruptedChars.add(ch);
                    break;
                }
                if (line.charAt(openingPositions.removeLast()) != CLOSING_OPENING.get(ch)) {
                    corruptedChars.add(ch);
                    break;
                }
            }
        }
    }

    @Override
    long task2(boolean isTest) {
        List<String> lines = getDataAsStringLines(isTest);
        List<String> incompletes = new ArrayList<>();
        lines.forEach(line -> addIncompleteChars(line, incompletes));
        long score = 0;
        List<Long> incompleteScore = new ArrayList<>();
        for (String incomplete : incompletes) {
            score = 0;
            for (int i = incomplete.length() -1; i>=0; i--) {
                score *= 5;
                score += INCOMPLETE_POINTS.get(OPENING_CLOSING.get(incomplete.charAt(i)));
            }
            incompleteScore.add(score);
        }
        Collections.sort(incompleteScore);
        return incompleteScore.get(incompleteScore.size() / 2);
    }

    private void addIncompleteChars(String line, List<String> incompletes) {
        StringBuilder openingPositions = new StringBuilder();
        char ch = 0;
        boolean isCorrupted = false;
        for (int i = 0; i < line.length() && !isCorrupted; i++) {
            ch = line.charAt(i);
            if (OPENING_CLOSING.containsKey(ch)) {
                openingPositions.append(ch);
            } else {
                if (openingPositions.isEmpty()) {
                    isCorrupted = true;
                }
                if (openingPositions.charAt(openingPositions.length() -1) != CLOSING_OPENING.get(ch)) {
                    isCorrupted = true;
                } else {
                    openingPositions.setLength(openingPositions.length() -1);
                }
            }
        }
        if (!isCorrupted && !openingPositions.isEmpty()) {
            incompletes.add(openingPositions.toString());
        }
    }
}
