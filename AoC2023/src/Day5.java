import org.antlr.v4.runtime.tree.Tree;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.TreeMap;

import static net.sf.saxon.trans.DecimalSymbols.INFINITY;

public class Day5 {
    PriorityQueue<Integer> locations;
    ArrayList<Integer> seeds;
    TreeMap<int[], Integer> seedToSoil;
    TreeMap<int[], Integer> soilToFertilizer;
    TreeMap<int[], Integer> fertilizerToWater;
    TreeMap<int[], Integer> waterToLight;
    TreeMap<int[], Integer> lightToTemperature;
    TreeMap<int[], Integer> temperatureToHumidity;
    TreeMap<int[], Integer> humidityToLocation;
    class Comp implements Comparator<int[]> {
        @Override
        public int compare(int[] o1, int[] o2) {
            return o1[0] - o2[0];
        }
    }
    public Day5() {
        Comp c = new Comp();
        seedToSoil = new TreeMap<>(c);
        soilToFertilizer = new TreeMap<>(c);
        fertilizerToWater = new TreeMap<>(c);
        waterToLight = new TreeMap<>(c);
        lightToTemperature = new TreeMap<>(c);
        temperatureToHumidity = new TreeMap<>(c);
        humidityToLocation = new TreeMap<>(c);
        // locations = new PriorityQueue<>();
    }

    private void addToMap(TreeMap<int[], Integer> map, int[] vals) {
        int destStart = vals[0];
        int sourceStart = vals[1];
        int range = vals[2];
        map.put(new int[]{sourceStart, range}, destStart);
    }

    private int[] findNextVal(TreeMap<int[], Integer> map, int source, int sourceRange) {
        int dest;
        int destRange = 1; // default for vals that aren't found
        int[] checkKey = map.floorKey(new int[]{source, 1});
        if (checkKey == null) {
            dest = source;
        } else {
            dest = map.get(checkKey);
            destRange = checkKey[1];
        }
        return new int[]{dest, destRange};
    }

    private int findLocationFromSeed(int seed) {
        int location = 0;
        return location;
    }

    public int returnMinLocation() {
        int min = INFINITY;
        for (int seed : seeds) {
            min = Math.min(min, findLocationFromSeed(seed));
        }
        return min;
    }
}
