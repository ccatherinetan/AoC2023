import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Day3 {
    public final int LINE_LENGTH = 140;
    public List<int[]> starCoords;
    public HashMap<Integer, NumberNode> numberNodes;
    boolean[][] validSpots;
    int[][] numberSpots;
    private class NumberNode {
        int startX;
        int endX;
        int y;
        int number;
        int id;
        NumberNode(int startX, int endX, int y, int number) {
            this.startX = startX;
            this.endX = endX;
            this.y = y;
            this.number = number;
            this.id = numberNodes.size() + 1;
        }
    }

    public Day3(String file) {
        numberNodes = new HashMap<>();
        starCoords = new ArrayList<>();
        validSpots = new boolean[LINE_LENGTH][LINE_LENGTH];
        numberSpots = new int[LINE_LENGTH][LINE_LENGTH];
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

    private void markAroundNumber(NumberNode nn) {
        for (int x = nn.startX; x <= nn.endX; x++) {
            numberSpots[x][nn.y] = nn.id;
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

    private void readLine(String line, int lineNum) {
        int x = 0;
        while (x < line.length()) {
            if (Character.isDigit(line.charAt(x))) {
                NumberNode nn = createNumNode(line, x, lineNum);
                // numberNodes.add(nn);
                numberNodes.put(nn.id, nn);
                markAroundNumber(nn);
                x = nn.endX + 1;
            } else if (isSymbol(line.charAt(x))) {
                if (line.charAt(x) == '*') {
                    int[] coords = new int[]{x, lineNum};
                    starCoords.add(coords);
                }
                markAroundSymbol(x, lineNum);
                x++;
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
        for (NumberNode nn : numberNodes.values()) {
            if (validPartNumber(nn)) {
                sum += nn.number;
            }
        }
        return sum;
    }

    private int getNum(int nnID) {
        return numberNodes.get(nnID).number;
    }

    private int getGearRatio(int[] coords) {
        ArrayList<Integer> numberNodeIDs = new ArrayList<>();
        int starX = coords[0];
        int starY = coords[1];
        for (int x = starX - 1; x <= starX + 1; x++) {
            for (int y = starY - 1; y <= starY + 1; y++) {
                int occupiedID = numberSpots[x][y];
                if (occupiedID > 0 && !numberNodeIDs.contains(occupiedID)) {
                    numberNodeIDs.add(occupiedID);
                }
                if (numberNodeIDs.size() > 2) { // too many, not a valid gear
                    return -1;
                }
            }
        }
        if (numberNodeIDs.size() != 2) {
            return -1; // if not a valid gear
        }
        return getNum(numberNodeIDs.get(0)) * getNum(numberNodeIDs.get(1));
    }

    public int returnGearSum() {
        int sum = 0;
        for (int[] coords : starCoords) {
            int gearRatio = getGearRatio(coords);
            if (gearRatio != -1) {
                sum += gearRatio;
            }
        }
        return sum;
    }
 }
