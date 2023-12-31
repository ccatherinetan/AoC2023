import edu.princeton.cs.algs4.In;

public class Day6 {
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

    public void readFile(String file) {
        In in = new In(file);
        for (int i = 0; i < 2; i++) {
            String line = in.readLine().split(":[ ]+")[1];
            String[] data = line.split("[ ]+");
            if (i == 0) {
                times = stringToIntArray(data);
            } else {
                distances = stringToIntArray(data);
            }
        }
        // h = # ms, held down
        // r = race time
        // b = best distance
        // ? h s.t. ( h * (r - h) > b)
        // ? h s.t.  - h ^ 2 + r * h - b = 0
    }

    private int[] solveQuadraticEquation(int a, int b, int c) {
        // x = (-b +/- √(b^2 - 4ac)) / 2a
        int radicand = b * b - 4 * a * c;
        double term = Math.sqrt(radicand);
//        int lower = (int) Math.ceil((-b - term) / (2 * a));
//        int upper = (int) Math.floor(-b + term) / (2 * a);
        // return new int[]{lower, upper};
        double lower = (-b - term) / (2 * a);
        double upper = (-b + term) / (2 * a);;
        return new int[]{nextInt(lower, true), nextInt(upper, false)};
    }

    private int nextInt(double d, boolean larger) {
        if (larger) {
            if (d == Math.ceil(d)) {
                return (int) d + 1;
            }
            return (int) Math.ceil(d);
        } // else, want next SMALLER int
        if (d == Math.floor(d)) { // if d is an int value
            return (int) d - 1;
        }
        return (int) Math.floor(d);
    }

    private int numSolutions(int[] bounds) {
        // assumes bounds[1] >= bounds[0]
        return bounds[1] - bounds[0] + 1;
    }

    public void test() {
        // x^2 - 2x + 1 = 0 -> x = 1
        int[] sols = solveQuadraticEquation(1, -2, 1);
        System.out.println(sols[0] + " " + sols[1]);
        // x^2 - 3x - 4 -> x = -1, 4
        int[] sols2 = solveQuadraticEquation(1, -3, -4);
        System.out.println(sols2[0] + " " + sols2[1]);
    }

    public int finalProduct() {
        int prod = 1;
        for (int i = 0; i < times.length; i++) {
            int t = times[i];
            int d = distances[i];
            int[] sols = solveQuadraticEquation(1, -t, d);
            prod *= numSolutions(sols);
            System.out.println("Time: " + times[i]);
            System.out.println("Best Distance: " + distances[i]);
            System.out.println(sols[0] + " " + sols[1]);
            System.out.println(numSolutions(sols));
        }
        return prod;
    }
}
