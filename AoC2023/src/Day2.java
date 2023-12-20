import edu.princeton.cs.algs4.In;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.HashMap;

public class Day2 {
    public  HashMap<Integer, int[]> RGBbyGame;
//    public int sum;
//    public int[] redPerGame; // HashMap<Integer, Integer[]>
//    public int[] greenPerGame;
//    public int[] bluePerGame;

    public final int[] MAX_RGB = new int[]{12, 13, 14};
//    public final static int MAX_RED = 12;
//    public final static int MAX_GREEN = 13;
//    public final static int MAX_BLUE = 14;
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

            // iterate through each of the 3 rounds for each game
            String[] colorSets = line[1].split("[;,] ");
            for (int i = 0; i < colorSets.length; i++) {
                String oneSet = colorSets[i];
                maxRGBValues = updateNumber(oneSet, maxRGBValues);
            }
//            String[] gameSets = line[1].split("; ");
//            for (int i = 0; i < 3; i++) {
//                maxRGBValues = handleOneGame(gameSets[i], maxRGBValues);
//            }
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

    private int[] handleOneGame(String s, int[] tobeUpdated) {
        String[] colorSets = s.split(", ");
        for (int j = 0; j < colorSets.length; j++) {
            tobeUpdated = updateNumber(colorSets[j], tobeUpdated);
        }
        return tobeUpdated;

//        Pattern pattern = Pattern.compile("\\b(\\d+)\\b");
//        Matcher matcher = pattern.matcher(s);
//        int i = 0;
//        while (matcher.find()) { // should only be 3
//            int number = Integer.parseInt(matcher.group(1));
//            if (number > tobeUpdated[i]) {
//                tobeUpdated[i] = number;
//            }
//            i++;
//        }
//        return tobeUpdated;
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
