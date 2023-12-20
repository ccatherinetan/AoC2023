import edu.princeton.cs.algs4.In;

import java.util.HashSet;

public class Day3 {
    public final int LINE_LENGTH = 140;
//    public HashSet<int[]> symbolCoords;
    public HashSet<NumberNode> numberNodes;
    boolean[][] validSpots;
    private class NumberNode {
        int startX;
        int endX;
        int y;
        int number;
        NumberNode(int startX, int endX, int y, int number) {
            this.startX = startX;
            this.endX = endX;
            this.y = y;
            this.number = number;
        }
    }

    public Day3(String file) {
//        symbolCoords = new HashSet<>();
        numberNodes = new HashSet<>();
        validSpots = new boolean[LINE_LENGTH][LINE_LENGTH];
        readFile(file);
    }
    public void readFile(String file) {
        In in = new In(file);
        int lineNum = 0;
        while (in.hasNextLine()) {
            String line = in.readLine();
            readLine(line, lineNum);
            lineNum++;
        }
    }

    private boolean isSymbol(char c) {
        return !Character.isDigit(c) && c != '.';
    }

    private boolean validCoord(int x, int y) {
        return 0 <= x && x <= LINE_LENGTH && 0 <= y && y <= LINE_LENGTH;
    }

    private void markAroundSymbol(int symbolX, int symbolY) {
        for (int x = symbolX - 1; x <= symbolX + 1; x++) {
            for (int y = symbolY - 1; y <= symbolY + 1; y++) {
                if (validCoord(x, y)) {
                    validSpots[x][y] = true;
                }
            }
        }
    }

    private NumberNode createNumNode(String line, int startX, int y) {
        StringBuilder numString = new StringBuilder();
        int x = startX;
        while (Character.isDigit(line.charAt(x))) {
            numString.append(line.charAt(x));
            x++;
        }
        int numLength = numString.length();
        int number = Integer.parseInt(numString.toString());
        return new NumberNode(startX, startX + numLength, y, number);
    }

    private void readLine(String line, int lineNum) {
        for (int x = 0; x < LINE_LENGTH; x++) {
            if (isSymbol(line.charAt(x))) {
                markAroundSymbol(x, lineNum);
//                int[] coords = new int[]{x, lineNum};
//                symbolCoords.add(coords);
            } else if (Character.isDigit(line.charAt(x))) {
                NumberNode nn = createNumNode(line, x, lineNum);
                numberNodes.add(nn);
            }
        }
    }

    private boolean validPartNumber(NumberNode nn) {
        for (int x = nn.startX; x <= nn.endX; x++) {
            if (validSpots[x][nn.y]) {
                return true;
            }
        }
        return false;
    }

    public int returnSum() {
        int sum = 0;
        for (NumberNode nn : numberNodes) {
            if (validPartNumber(nn)) {
                sum += nn.number;
            }
        }
        return sum;
    }
 }
