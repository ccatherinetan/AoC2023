import edu.princeton.cs.algs4.In;

public class Day6 {
    long oneTime;
    long oneDist;
    int[] times;
    int[] distances;
    public Day6(String file) {
        readFile(file);
    }

    private int[] stringToIntArray(String[] a) {
        int[] returnVal = new int[a.length];
        for (int i = 0; i < a.length; i++) {
            returnVal[i] = Integer.parseInt(a[i]);
        }
        return returnVal;
    }

    private long combineInto1Num(String[] a) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < a.length; i++) {
            sb.append(a[i]);
        }
        return Long.parseLong(sb.toString());
    }

    public void readFile(String file) {
        In in = new In(file);
        for (int i = 0; i < 2; i++) {
            String line = in.readLine().split(":[ ]+")[1];
            String[] data = line.split("[ ]+");
            if (i == 0) {
                oneTime = combineInto1Num(data);
                times = stringToIntArray(data);
            } else {
                oneDist = combineInto1Num(data);
                distances = stringToIntArray(data);
            }
        }
        // h = # ms, held down
        // r = race time
        // b = best distance
        // ? h s.t. ( h * (r - h) > b)
        // ? h s.t.  - h ^ 2 + r * h - b = 0
    }

    private long[] solveQuadraticEquation(long a, long b, long c) {
        // x = (-b +/- √(b^2 - 4ac)) / 2a
        long radicand = b * b - 4 * a * c;
        double term = Math.sqrt(radicand);
        double lower = (-b - term) / (2 * a);
        double upper = (-b + term) / (2 * a);;
        return new long[]{nextLong(lower, true), nextLong(upper, false)};
    }

    private long nextLong(double d, boolean larger) {
        if (larger) {
            if (d == Math.round(d)) {
                return (long) d + 1;
            }
            return (long) Math.ceil(d);
        } // else, want next SMALLER int
        if (d == Math.round(d)) { // if d is an int value
            return (long) d - 1;
        }
        return (long) Math.floor(d);
    }

    private long numSolutions(long[] bounds) {
        // assumes bounds[1] >= bounds[0]
        return bounds[1] - bounds[0] + 1;
    }

    public long finalProduct2() {
        long[] sols = solveQuadraticEquation(1, oneTime, oneDist);
        return numSolutions(sols);
    }

    public long finalProduct() {
        long prod = 1;
        for (int i = 0; i < times.length; i++) {
            long[] sols = solveQuadraticEquation(1, -times[i], distances[i]);
            prod *= numSolutions(sols);
        }
        return prod;
    }
}
