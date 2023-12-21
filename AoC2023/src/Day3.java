import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Day3 {
    public final int LINE_LENGTH = 140;
//    public HashSet<int[]> symbolCoords;
    public List<NumberNode> numberNodes;
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
        // numberNodes = new HashSet<>();
        numberNodes = new ArrayList<>();
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
        return 0 <= x && x < LINE_LENGTH && 0 <= y && y < LINE_LENGTH;
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
        while (x < line.length() && Character.isDigit(line.charAt(x))) {
            numString.append(line.charAt(x));
            x++;
        }
        int endX = startX + numString.length() - 1;
        int number = Integer.parseInt(numString.toString());
        return new NumberNode(startX, endX, y, number);
    }

    public void test() {
        String line = ".........398.............551.....................452..................712.996.................646.40...1.....875..958.553...................\n";
        readLine(line, 0);
        for (NumberNode nn : numberNodes) {
            System.out.println("number: " + nn.number);
            System.out.println("startX: " + nn.startX);
            System.out.println("endX: " + nn.endX);
        }

//        NumberNode nn = createNumNode(line, 1, 0);
//        System.out.println("number: " + nn.number);
//        System.out.println("startX: " + nn.startX);
//        System.out.println("endX: " + nn.endX);
    }

    private void readLine(String line, int lineNum) {
        int x = 0;
        while (x < line.length()) {
            if (Character.isDigit(line.charAt(x))) {
                NumberNode nn = createNumNode(line, x, lineNum);
                numberNodes.add(nn);
                x = nn.endX + 1;
            } else if (isSymbol(line.charAt(x))) {
                markAroundSymbol(x, lineNum);
                x++;
//                int[] coords = new int[]{x, lineNum};
//                symbolCoords.add(coords);
            } else {
                x++;
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
