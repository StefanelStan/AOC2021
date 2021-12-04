package com.ss.aoc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Day04 extends AOCDay {

    @Override
    long task1(boolean isTest) {
        List<String> lines = getDataAsStringLines(isTest);
        Set<Integer>[] numberBoards = new Set[100];
        List<Board> boards = new ArrayList<>((lines.size() -1) / 6);
        insertNumbersInBoards(lines, numberBoards, boards);
        return playBingo(lines.get(0), numberBoards, boards);
    }

    private void insertNumbersInBoards(List<String> lines, Set<Integer>[] numberBoards, List<Board> boards) {
        int lineIndex = 2, boardIndex = 0;
        while (lineIndex < lines.size()) {
            int[][] board = new int[5][5];
            for (int i = 0; i < 5; i++, lineIndex++) {
                board[i] = getNumbers(lines.get(lineIndex), boardIndex, numberBoards);
            }
            boards.add(new Board(board));
            lineIndex++;
            boardIndex++;
        }
    }

    private void mapOnBoard(int number, int boardIndex, Set<Integer>[] numberBoards) {
        Set<Integer> boards = numberBoards[number];
        if (boards == null) {
            boards = new HashSet<>();
            numberBoards[number] = boards;
        }
        boards.add(boardIndex);
    }

    private int[] getNumbers(String line, int boardIndex, Set<Integer>[] numberBoards) {
        int[] nrs = new int[5];
        int nrIndex = 0;
        StringBuilder stb = new StringBuilder();
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) != ' ') {
                stb.append(line.charAt(i));
            } else if (!stb.isEmpty()){
                nrs[nrIndex] = Integer.parseInt(stb.toString());
                mapOnBoard(nrs[nrIndex], boardIndex, numberBoards);
                nrIndex++;
                stb.setLength(0);
            }
        }
        nrs[4] = Integer.parseInt(stb.toString());
        mapOnBoard(nrs[4], boardIndex, numberBoards);
        return nrs;
    }


    private long playBingo(String line, Set<Integer>[] numberBoards, List<Board> boards) {
        String[] chunks = line.split(",");
        int number;
        for (String chunk : chunks) {
            number = Integer.parseInt(chunk);
            Set<Integer> boardsWithNumber = numberBoards[number];
            if (boardsWithNumber != null) {
                for (int boardIndex : boardsWithNumber) {
                    Board b = boards.get(boardIndex);
                    b.markNumber(number);
                    if (b.isWinner()) {
                        return (long) number * b.getFinalScore();
                    }
                }
            }
        }
        return 0L;
    }


    @Override
    long task2(boolean isTest) {
        List<String> lines = getDataAsStringLines(isTest);
        Set<Integer>[] numberBoards = new Set[100];
        List<Board> boards = new ArrayList<>((lines.size() -1) / 6);
        insertNumbersInBoards(lines, numberBoards, boards);
        return playLastBingo(lines.get(0), numberBoards, boards);
    }

    private long playLastBingo(String line, Set<Integer>[] numberBoards, List<Board> boards) {
        List<Integer> winners = new ArrayList<>();
        Set<Integer> winningBoards = new LinkedHashSet<>();
        String[] chunks = line.split(",");
        int number;
        for (String chunk : chunks) {
            number = Integer.parseInt(chunk);
            Set<Integer> boardsWithNumber = numberBoards[number];
            if (boardsWithNumber != null) {
                for (int boardIndex : boardsWithNumber) {
                    Board b = boards.get(boardIndex);
                    b.markNumber(number);
                    if (b.isWinner() && !winningBoards.contains(boardIndex)) {
                        winners.add(number * b.getFinalScore());
                        winningBoards.add(boardIndex);
                    }
                }
            }
        }
        return winners.get(winners.size() -1);
    }


    private static class Board {
        int[][] board;
        boolean[][] marked;

        public Board(int[][] board) {
            this.board = board;
            marked = new boolean[5][5];
        }

        public boolean isWinner() {
            for (int i = 0; i < board.length; i++) {
                boolean isWinner = true;
                for (int j = 0; j < board[i].length; j++) {
                    isWinner &= marked[i][j];
                }
                if (isWinner) {
                    return true;
                }
            }
            for (int j = 0; j < board[0].length; j++) {
                boolean isWinner = true;
                for (int i = 0; i < board.length; i++) {
                    isWinner &= marked[i][j];
                }
                if (isWinner) {
                    return true;
                }
            }
            return false;
        }

        public void markNumber(int number) {
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[i].length; j++) {
                    if (board[i][j] == number) {
                        marked[i][j] = true;
                    }
                }
            }
        }

        public int getFinalScore() {
            int sum = 0;
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[i].length; j++) {
                    if (!marked[i][j]) {
                        sum += board[i][j];
                    }
                }
            }
            return sum;
        }
    }
}
