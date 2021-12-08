package com.ss.aoc;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day08 extends AOCDay {
    private static Map<String, Integer> NUMBERS_MAP;

    public Day08() {
        if (NUMBERS_MAP == null) {
            NUMBERS_MAP = new HashMap<>();
            NUMBERS_MAP.put("cf", 1);
            NUMBERS_MAP.put("acf", 7);
            NUMBERS_MAP.put("bcdf", 4);
            NUMBERS_MAP.put("abcdefg", 8);
            NUMBERS_MAP.put("acdeg", 2);
            NUMBERS_MAP.put("acdfg", 3);
            NUMBERS_MAP.put("abdfg", 5);
            NUMBERS_MAP.put("abcefg", 0);
            NUMBERS_MAP.put("abdefg", 6);
            NUMBERS_MAP.put("abcdfg", 9);
        }
    }

    @Override
    long task1(boolean isTest) {
        List<String> lines = getDataAsStringLines(isTest);
        int uniqueDigits = 0;
        for (String line : lines) {
            String[] chunks = line.split("\\|");
            String[] endingDigits = chunks[1].trim().split(" ");
            for (String endingDigit : endingDigits) {
                if (endingDigit.length() < 5 || endingDigit.length() == 7) {
                    uniqueDigits++;
                }
            }
        }
        return uniqueDigits;
    }

    @Override
    long task2(boolean isTest) {
        List<String> lines = getDataAsStringLines(isTest);
        int decodedNumber = 0;
        for (String line : lines) {
            decodedNumber += getDecodedNumber(line);
        }
        return decodedNumber;
    }

    private int getDecodedNumber(String line) {
        String[] chunks = line.split("\\|");
        String[] alphabet = chunks[0].trim().split(" ");
        Arrays.sort(alphabet, Comparator.comparingInt(String::length));
        String[] endingDigits = chunks[1].trim().split(" ");
        Map<Character, Character> wrongToReal = new HashMap<>();
        Map<Character, Character> realToWrong =  new HashMap<>();
        // 1 and 7 -> get A
        char wrongA = getDiffCharFrom(alphabet[0], alphabet[1]);
        wrongToReal.put(wrongA, 'a');
        realToWrong.put('a', wrongA);
        // 4 and 8 > cut 4 from 8 and you end up with fakeE and fakeG
        Set<Character> fakeEG = getFakeEG(alphabet[2], alphabet[9], wrongA);
        // fE + fG + a 5CharsDigit -> identify number 2.
        int indexOfTwo = getIndexOfTwo(alphabet, fakeEG);
        // strip e,g,a from nr2 and -> fakeC and fakeD -> unfake them by comparing with 1: find C,D, F
        findLettersCDF(alphabet[indexOfTwo], alphabet[0], fakeEG, wrongA, wrongToReal, realToWrong);
        // find B from number4 as we know A,C,D,F
        findMissingLetter(alphabet[2], 'b', wrongToReal, realToWrong);
        // which 5 letter seq has B? (fakeB)? well, it's nr5. Identify number 5 and extract G
        int indexOfFive = getIndexOfFive(alphabet, realToWrong.get('b'));
        findMissingLetter(alphabet[indexOfFive], 'g', wrongToReal, realToWrong);
        // from 8 find E
        findMissingLetter(alphabet[9], 'e', wrongToReal, realToWrong);


        StringBuilder number = new StringBuilder();
        char[] chars;
        for (String endingDigit : endingDigits) {
            chars = new char[endingDigit.length()];
            for (int i = 0; i < endingDigit.length(); i++) {
                chars[i] = wrongToReal.get(endingDigit.charAt(i));
            }
            Arrays.sort(chars);
            number.append(NUMBERS_MAP.get(new String(chars)));
        }
        return Integer.parseInt(number.toString());
    }

    private int getIndexOfFive(String[] alphabet, Character b) {
        for (int i = 3; i<= 5; i++) {
            for (char c : alphabet[i].toCharArray()) {
                if (c == b) {
                    return i;
                }
            }
        }
        return -1;
    }

    private void findMissingLetter(String number, char missingLetter, Map<Character, Character> wrongToReal, Map<Character, Character> realToWrong) {
        for (char c : number.toCharArray()) {
            if (!wrongToReal.containsKey(c)) {
                wrongToReal.put(c, missingLetter);
                realToWrong.put(missingLetter, c);
                break;
            }
        }
    }

    private void findLettersCDF(String nr2, String nr1, Set<Character> chunkOf8, char wrongA, Map<Character, Character> wrongToReal, Map<Character, Character> realToWrong) {
        Set<Character> nr2AsSet = asSet(nr2);
        Set<Character> nr1AsSet = asSet(nr1);
        nr2AsSet.remove(wrongA);
        nr2AsSet.removeAll(chunkOf8); // now you got fakeC and fakeD remaining in two
        for (char c : nr2AsSet) {
            if (nr1AsSet.contains(c)) {
                // this is wrongC pointing to realC
                wrongToReal.put(c, 'c');
                realToWrong.put('c', c);
                nr1AsSet.remove(c);
            } else {
                // this is fakeD pointing to realD
                wrongToReal.put(c, 'd');
                realToWrong.put('d', c);
            }
        }
        // nr1AsSet only has fakeF pointing to F
        char wrongF = nr1AsSet.iterator().next();
        wrongToReal.put(wrongF, 'f');
        realToWrong.put('f', wrongF);
    }

    private int getIndexOfTwo(String[] alphabet, Set<Character> chunkOfEight) {
        for (int i = 3; i <= 5; i++) {
            int containedChar = 0;
            for (char c : alphabet[i].toCharArray()) {
                if (chunkOfEight.contains(c)) {
                    containedChar++;
                }
            }
            if (containedChar == 2) {
                return i;
            }
        }
        return -1;
    }

    private Set<Character> getFakeEG(String four, String eight, char wrongA) {
        Set<Character> eightSet = asSet(eight);
        eightSet.remove(wrongA);
        for (char c : four.toCharArray()) {
            eightSet.remove(c);
        }
        return eightSet;
    }

    private char getDiffCharFrom(String shortString, String longString) {
        for (int i = 0; i < longString.length(); i++) {
            if (!shortString.contains(String.valueOf(longString.charAt(i)))) {
                return longString.charAt(i);
            }
        }
        return '0';
    }

    private Set<Character> asSet(String s) {
        Set<Character> asSet = new HashSet<>();
        for (char c : s.toCharArray()) {
            asSet.add(c);
        }
        return asSet;
    }
}
