package com.ss.aoc;

import java.util.Arrays;
import java.util.List;

public class Day03 extends AOCDay {

    @Override
    long task1(boolean isTest) {
        List<String> lines = getDataAsStringLines(isTest);
        int[] bitCount = getBitCount(lines);
        int[] rates = getRates(bitCount, lines.size());
        return (long) rates[0] * rates[1];
    }

    private int[] getRates(int[] bitCount, int size) {
        StringBuilder gamma = new StringBuilder();
        StringBuilder epsilon = new StringBuilder();
        int half = size /2;
        for (int bit : bitCount) {
            if (bit > half) {
                gamma.append(1);
                epsilon.append(0);
            } else {
                gamma.append(0);
                epsilon.append(1);
            }
        }
        return new int[]{Integer.parseInt(gamma.toString(), 2), Integer.parseInt(epsilon.toString(), 2)};
    }

    private int[] getBitCount(List<String> lines) {
        int[] bitCount =  new int[lines.get(0).length()];
        for (String line : lines) {
            for (int i = 0; i < line.length(); i++) {
                if (line.charAt(i) == '1') {
                    bitCount[i]++;
                }
            }
        }
        return bitCount;
    }

    @Override
    long task2(boolean isTest) {
        List<String> lines = getDataAsStringLines(isTest);
        ValidLines oxygenGoodLines = new ValidLines(lines.size());
        ValidLines co2GoodLines = new ValidLines(lines.size());

        int oxygenRating = getRating(lines, oxygenGoodLines, '1', '0');
        int co2Rating = getRating(lines, co2GoodLines, '0', '1');

        return (long) oxygenRating * co2Rating;
    }

    private int getRating(List<String> lines, ValidLines validLines, char majorChar, char minorChar) {
        int bitPosition = 0;
        while(validLines.goodLines > 1 && bitPosition < lines.get(0).length()) {
            int indexBitCount = getIndexBitCount(lines, validLines, bitPosition);
            char mostCommon = indexBitCount *2 >= validLines.goodLines ? majorChar : minorChar;
            for (int i = 0; i < lines.size(); i++) {
                if(validLines.validLines[i]) {
                    if (lines.get(i).charAt(bitPosition) != mostCommon) {
                        validLines.invalidateLine(i);
                    }
                }
            }
            bitPosition++;
        }
        return Integer.parseInt(lines.get(validLines.getGoodLine()), 2);
    }

    int getIndexBitCount(List<String> lines, ValidLines validLines, int index) {
        int indexBitCount = 0;
        for(int lineIndex = 0; lineIndex < lines.size(); lineIndex++) {
            if (validLines.validLines[lineIndex] && lines.get(lineIndex).charAt(index) == '1') {
                    indexBitCount++;
            }
        }
        return indexBitCount;
    }

    private static class ValidLines {
        boolean[] validLines;
        int goodLines;

        public ValidLines(int numberOfLines) {
            validLines = new boolean[numberOfLines];
            goodLines = numberOfLines;
            Arrays.fill(validLines, true);
        }

        public void invalidateLine(int lineNumber) {
            validLines[lineNumber] = false;
            goodLines--;
        }

        public int getGoodLine() {
            for (int i = 0; i < validLines.length; i++) {
                if (validLines[i]) {
                    return i;
                }
            }
            return -1;
        }
    }
}
