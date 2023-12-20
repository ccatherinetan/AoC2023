import edu.princeton.cs.algs4.In;
import java.util.HashMap;

public class Day2 {
    public  HashMap<Integer, int[]> RGBbyGame;
    public final int[] MAX_RGB = new int[]{12, 13, 14};
    public final String[] colors = new String[]{"red", "green", "blue"};
    public Day2(String file) {
        RGBbyGame = new HashMap<>();
        readFile(file);
    }
    public void readFile(String file) {
        In in = new In(file);
        while (in.hasNextLine()) {
            int[] maxRGBValues = new int[3];
            // get gameNum of current game
            String[] line = in.readLine().split(": ");
            int gameNum = getGameNum(line[0]);

            // create maxRGBValues set for curr game
            String[] colorSets = line[1].split("[;,] ");
            for (int i = 0; i < colorSets.length; i++) {
                String oneSet = colorSets[i];
                maxRGBValues = updateNumber(oneSet, maxRGBValues);
            }
            RGBbyGame.put(gameNum, maxRGBValues);
        }
    }

    private int getGameNum(String s) {
        return Integer.parseInt(s.substring(5));
    }

    /**
     * Update the maxVals array with the current grab.
     * @param currGrab: string, in the form of "[no.] [color]" e.g. "5 red"
     * @param tobeUpdated an array of max values of each color, RGB in that order
     * @return tobeUpdated
     */
    private int[] updateNumber(String currGrab, int[] tobeUpdated) {
        int i = 0;
        for (String color: colors) {
            int stopIndex = currGrab.indexOf(color);
            if (stopIndex != -1) {
                int number = Integer.parseInt(currGrab.substring(0, stopIndex - 1));
                if (number > tobeUpdated[i]) {
                    tobeUpdated[i] = number;
                }
            }
            i++;
        }
        return tobeUpdated;
    }

    private boolean isValid(int[] vals) {
        for (int i = 0; i < 3; i++) {
            if (vals[i] > MAX_RGB[i]) {
                return false;
            }
        }
        return true;
    }
    public int returnSum() {
        int sum = 0;
        for (int key : RGBbyGame.keySet()) {
            if (isValid(RGBbyGame.get(key))) {
                // System.out.println(key);
                sum += key;
            }
        }
        return sum;
    }

    private int getPower(int[] a) {
        int power = 1;
        for (int i = 0; i < 3; i++) {
            power *= a[i];
        }
        return power;
    }
    public int returnPowerSum() {
        int powerSum = 0;
        for (int key : RGBbyGame.keySet()) {
            powerSum += getPower(RGBbyGame.get(key));
        }
        return powerSum;
    }

    public void printGameVals(int gameNum) {
        int[] vals = RGBbyGame.get(gameNum);
        String toPrint = "";
        for (int i = 0; i < 3; i++) {
            toPrint += vals[i];
            if (i < 2) {
                toPrint += ", ";
            }
        }
        System.out.println(toPrint);
    }
}
