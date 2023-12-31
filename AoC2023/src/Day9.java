import java.util.ArrayList;

public class Day9 {
    private class Report {
        int[] values;
        // int currDepth;
        int maxDepth;
        ArrayList<int[]> nextLevels = new ArrayList<>();
        public Report(int[] v) {
            values = v;
            int[] curr = values;
            while (!completed(curr)) {
                int[] next = getNextLevel(curr);
                nextLevels.add(next);
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
    }

    public Day9(String file) {
        readFile(file);
    }

    public void readFile(String file) {

    }
}
