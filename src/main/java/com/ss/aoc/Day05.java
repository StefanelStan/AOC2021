package com.ss.aoc;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Day05 extends AOCDay {
    @Override
    long task1(boolean isTest) {
        List<String> lines = getDataAsStringLines(isTest);
        List<Point> vhPoints = getVHPoints(lines);
        int[][] map = new int[vhPoints.get(vhPoints.size() - 1).x][vhPoints.get(vhPoints.size() - 1).y];
        return drawMap(vhPoints, map);
    }

    private List<Point> getVHPoints(List<String> lines) {
        List<Point> points = new ArrayList<>(lines.size() * 2 + 1);
        int xMax = 0, yMax = 0;
        for (String line : lines) {
            String[] chunks = line.split(" -> ");
            Point p1 = getCoords(chunks[0]);
            Point p2 = getCoords(chunks[1]);
            if (p1.x == p2.x || p1.y == p2.y) {
                points.add(p1);
                points.add(p2);
            }
            xMax = Math.max(xMax, Math.max(p1.x, p2.x));
            yMax = Math.max(yMax, Math.max(p1.y, p2.y));
        }
        points.add(new Point(xMax + 1, yMax + 1));
        return points;
    }

    private Point getCoords(String chunk) {
        String[] bits = chunk.split(",");
        return new Point(Integer.parseInt(bits[0]), Integer.parseInt(bits[1]));
    }

    private int drawMap(List<Point> vhPoints, int[][] map) {
        int overlapping = 0, constant, start, end;
        for (int i = 0; i < vhPoints.size() - 2; i += 2) {
            if (vhPoints.get(i).x == vhPoints.get(i + 1).x) {
                constant = vhPoints.get(i).x;
                start = Math.min(vhPoints.get(i).y, vhPoints.get(i + 1).y);
                end = Math.max(vhPoints.get(i).y, vhPoints.get(i + 1).y);
                overlapping += markLinePoints(map, constant, start, end);
            } else {
                constant = vhPoints.get(i).y;
                start = Math.min(vhPoints.get(i).x, vhPoints.get(i + 1).x);
                end = Math.max(vhPoints.get(i).x, vhPoints.get(i + 1).x);
                overlapping += markColPoints(map, constant, start, end);
            }
        }
        return overlapping;
    }

    private int markLinePoints(int[][] map, int line, int start, int end) {
        int overlap = 0;
        for (; start <= end; start++) {
            if (map[line][start]++ == 1) {
                overlap++;
            }
        }
        return overlap;
    }

    private int markColPoints(int[][] map, int col, int start, int end) {
        int overlap = 0;
        for (; start <= end; start++) {
            if (map[start][col]++ == 1) {
                overlap++;
            }
        }
        return overlap;
    }

    @Override
    long task2(boolean isTest) {
        List<String> lines = getDataAsStringLines(isTest);
        List<Point> vhPoints = getPoints(lines);
        int[][] map = new int[vhPoints.get(vhPoints.size() - 1).x][vhPoints.get(vhPoints.size() - 1).y];
        return drawMultiDirectionalMap(vhPoints, map);
    }

    private List<Point> getPoints(List<String> lines) {
        List<Point> points = new ArrayList<>(lines.size() * 2 + 1);
        int xMax = 0, yMax = 0;
        for (String line : lines) {
            String[] chunks = line.split(" -> ");
            Point p1 = getCoords(chunks[0]);
            Point p2 = getCoords(chunks[1]);
            points.add(p1);
            points.add(p2);
            xMax = Math.max(xMax, Math.max(p1.x, p2.x));
            yMax = Math.max(yMax, Math.max(p1.y, p2.y));
        }
        points.add(new Point(xMax + 1, yMax + 1));
        return points;
    }

    private int drawMultiDirectionalMap(List<Point> vhPoints, int[][] map) {
        int overlapping = 0, startX, endX, startY, endY, constant;
        for (int i = 0; i < vhPoints.size() - 2; i += 2) {
            // traverse horizontally
            if (vhPoints.get(i).x == vhPoints.get(i + 1).x) {
                startX = vhPoints.get(i).x;
                startY = Math.min(vhPoints.get(i).y, vhPoints.get(i + 1).y);
                endY = Math.max(vhPoints.get(i).y, vhPoints.get(i + 1).y);
                overlapping += markLinePoints(map, startX, startY, endY);
            // traverse vertically
            } else if (vhPoints.get(i).y == vhPoints.get(i + 1).y) {
                startY = vhPoints.get(i).y;
                startX = Math.min(vhPoints.get(i).x, vhPoints.get(i + 1).x);
                endX = Math.max(vhPoints.get(i).x, vhPoints.get(i + 1).x);
                overlapping += markColPoints(map, startY, startX, endX);
            // traverse Diagonally
            } else {
                overlapping += traverseDiagonally(vhPoints.get(i), vhPoints.get(i + 1), map);
            }
        }
        return overlapping;
    }

    private int traverseDiagonally(Point p1, Point p2, int[][] map) {
        int overlapping = 0;
        Point lPoint = p1.y < p2.y ? p1 : p2;
        Point rPoint = lPoint == p1 ? p2 : p1;
        int direction = lPoint.x < rPoint.x ? 1 : -1;
        for (int i = lPoint.x, j = lPoint.y; j <= rPoint.y; i += direction, j++) {
            if (map[i][j]++ == 1) {
                overlapping++;
            }
        }
        return overlapping;
    }
}
