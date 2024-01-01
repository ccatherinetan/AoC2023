import edu.princeton.cs.algs4.In;

import java.util.ArrayList;

public class Day9 {
    private class Report {
        int[] values;
        ArrayList<int[]> nextLevels = new ArrayList<>();
        public Report(int[] v) {
            values = v;
            int[] curr = values;
            while (!completed(curr)) {
                curr = getNextLevel(curr);
                nextLevels.add(curr);
            }
        }

        private static int[] getNextLevel(int[] a) {
            int[] next = new int[a.length - 1];
            for (int i = 0; i < next.length; i++) {
                next[i] = a[i + 1] - a[i];
            }
            return next;
        }

        private static boolean completed(int[] a) {
            for (int i = 0; i < a.length; i++) {
                if (a[i] != 0) {
                    return false;
                }
            }
            return true;
        }

        public int predictNextVal() {
            int prevVal = 0;
            for (int i = nextLevels.size() - 2; i >= 0; i--) {
                int[] level = nextLevels.get(i);
                int last = level[level.length - 1];
                prevVal += last;
             }
            return prevVal + values[values.length - 1];
        }

        public int predictPrevVal() {
            int saved = 0;
            for (int i = nextLevels.size() - 2; i >= 0; i--) {
                int[] level = nextLevels.get(i);
                int first = level[0];
                saved = first - saved;
            }
            return values[0] - saved;
        }

    }

    public Day9(String file) {
        readFile(file);
    }

    public void readFile(String file) {
        In in = new In(file);
        int sum = 0;
        while (in.hasNextLine()) {
            String line = in.readLine();
            int[] vals = stringToIntArray(line.split("[ ]+"));
            Report r = new Report(vals);
            sum += r.predictPrevVal();
        }
        System.out.println(sum);
    }

    private int[] stringToIntArray(String[] a) {
        int[] returnVal = new int[a.length];
        for (int i = 0; i < a.length; i++) {
            returnVal[i] = Integer.parseInt(a[i]);
        }
        return returnVal;
    }

}
