import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.TreeSet;

public class Day11 {
    TreeSet<Integer> expandingX;
    TreeSet<Integer> expandingY;
    ArrayList<int[]> galaxyCoords;
    final int UNIVERSE_SIZE;
    final int EXPANSION_FACTOR = 1000000;

    /**
     * Find sum of the distances between galaxies
     * @param file input universe
     *
     * LOGIC:
     * 1. READ FILE
     * find the expanding rows and cols
     * mark down galaxy coordinates (pre-expansion)
     *
     * 2. EXPAND THE UNIVERSE
     * mark down galaxy coordinates (post-expansion)
     *
     * 3. CALCULATE DISTANCES
     * find distances between each galaxy-pair -> sum them
     */
    public Day11(String file, int uSize) {
        UNIVERSE_SIZE = uSize;
        expandingX = createInitialSet();
        expandingY = createInitialSet();
        galaxyCoords = new ArrayList<>();

        // 1. READ FILE
        readFile(file);

        // 2. EXPAND THE UNIVERSE
        ArrayList<int[]> newCoords = expandUniverse();

        // 3. CALCULATE DISTANCES
        long sum = sumDist(newCoords);
        System.out.println(sum);
    }

    private TreeSet<Integer> createInitialSet() {
        TreeSet<Integer> hs = new TreeSet<>();
        for (int i = 0; i < UNIVERSE_SIZE; i++) {
            hs.add(i);
        }
        return hs;
    }

    /**
     * Creates/Updates a list of cols (expandingX) and rows (expandingY) that will expand
     * based on pre-expansion galaxy locations, read from FILE
     * @param file input universe
     */
    public void readFile(String file) {
        In in = new In(file);
        int y = 0;
        while (in.hasNextLine()) {
            String line = in.readLine();
            // remove rows/cols that contain # from list of expanding rows/cols
            if (line.contains("#")) {
                expandingY.remove(y);
                int x = line.indexOf('#');
                while (x != -1) {
                    expandingX.remove(x); // nothing happens if expandingY doesn't contain y
                    galaxyCoords.add(new int[]{x, y});
                    x = line.indexOf('#', x + 1);
                }
            }
            y++;
        }
    }

    /**
     * Returns how many values, in SEARCH, VALUE is larger than.
     * Assumes that SEARCH does not contain VALUE
     * @param value the target value
     * @param search the list in which we're searching
     * @return number of items in SEARCH that VALUE is larger than
     */
    private int findPlace(int value, TreeSet<Integer> search) {
        int i = 0;
        for (int comp : search) {
            if (value < comp) {
                return i;
            }
            i++;
        }
        return search.size();
    }

    /**
     * Return post-expansion of 1 galaxy, based on EXPANSION_FACTOR
     * @param former pre-expansion coordinates (of 1 galaxy)
     * @return post-expansion coordindates, 2-valued int array
     */
    private int[] postExpansionCoords(int[] former) {
        int formerX = former[0];
        int formerY = former[1];
        int newX = formerX + findPlace(formerX, expandingX) * (EXPANSION_FACTOR - 1);
        int newY = formerY + findPlace(formerY, expandingY) * (EXPANSION_FACTOR - 1);

        return new int[]{newX, newY};
    }

    ArrayList<int[]> expandUniverse() {
        ArrayList<int[]> newCoords = new ArrayList<>();
        for (int[] coords : galaxyCoords) {
            newCoords.add(postExpansionCoords(coords));
        }
        return newCoords;
    }

    private int distBetween(int[] c1, int[] c2) {
        int xDiff = Math.abs(c1[0] - c2[0]);
        int yDiff = Math.abs(c1[1] - c2[1]);
        return xDiff + yDiff;
    }

    public long sumDist(ArrayList<int[]> allCoords) {
        long sum = 0;
        for (int i = 0; i < allCoords.size(); i++) {
            for (int j = i + 1; j < allCoords.size(); j++) {
                int[] c1 = allCoords.get(i);
                int[] c2 = allCoords.get(j);
                sum += distBetween(c1, c2);
            }
        }
        return sum;
    }

}
