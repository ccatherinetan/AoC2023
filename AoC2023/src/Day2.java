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
    public Day2(String file) {
        RGBbyGame = new HashMap<>();
        readFile(file);
    }
    public void readFile(String file) {
        In in = new In(file);
        while (in.hasNextLine()) {
            int[] maxRGBValues = new int[3];
            String[] line = in.readLine().split(": ");
            int gameNum = getGameNum(line[0]);
            String[] gameSets = line[1].split("; ");
            for (int i = 0; i < 3; i++) {
                maxRGBValues = udpateMaxRGBValues(gameSets[i], maxRGBValues);
            }
            RGBbyGame.put(gameNum, maxRGBValues);
        }
    }

    private int getGameNum(String s) {
        return Integer.parseInt(s.substring(5));
    }

    private int[] udpateMaxRGBValues(String s, int[] tobeUpdated) {
        Pattern pattern = Pattern.compile("\\b(\\d+)\\b");
        Matcher matcher = pattern.matcher(s);
        int i = 0;
        while (matcher.find()) { // should only be 3
            int number = Integer.parseInt(matcher.group(1));
            if (number > tobeUpdated[i]) {
                tobeUpdated[i] = number;
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
