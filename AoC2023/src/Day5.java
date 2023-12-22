import edu.princeton.cs.algs4.In;
import java.util.*;

import static net.sf.saxon.trans.DecimalSymbols.INFINITY;

public class Day5 {
    // PriorityQueue<Integer> locations;
    long[] seeds;
    TreeMap<Long, long[]> seedToSoil;
    TreeMap<Long, long[]> soilToFertilizer;
    TreeMap<Long, long[]> fertilizerToWater;
    TreeMap<Long, long[]> waterToLight;
    TreeMap<Long, long[]>lightToTemperature;
    TreeMap<Long, long[]> temperatureToHumidity;
    TreeMap<Long, long[]> humidityToLocation;
//    class Comp implements Comparator<int[]> {
//        @Override
//        public int compare(int[] o1, int[] o2) {
//            return o1[0] - o2[0];
//        }
//    }
    public Day5(String file) {
//        Comp c = new Comp();
        seedToSoil = new TreeMap<>();
        soilToFertilizer = new TreeMap<>();
        fertilizerToWater = new TreeMap<>();
        waterToLight = new TreeMap<>();
        lightToTemperature = new TreeMap<>();
        temperatureToHumidity = new TreeMap<>();
        humidityToLocation = new TreeMap<>();
        // locations = new PriorityQueue<>();
        readFile(file);
    }

    public void readFile(String file) {
        In in = new In(file);
        TreeMap<Long, long[]> currMap = null;
//        boolean addToMap = false;
        while (in.hasNextLine()) {
            String line = in.readLine();
            if (line != "") { // not an empty line
                // addToMap = false;
                if (line.contains("seeds")) {
                    String[] seedsStr = line.split("\\:[ ]+")[1].split("[ ]+");
                    seeds = stringToLongArray(seedsStr);
                } else if (line.contains("seed-to-soil")) {
//                addToMap = true;
                    currMap = seedToSoil;
                } else if (line.contains("soil-to-fertilizer")) {
//                addToMap = true;
                    currMap = soilToFertilizer;
                } else if (line.contains("fertilizer-to-water")) {
//                addToMap = true;
                    currMap = fertilizerToWater;
                } else if (line.contains("water-to-light")) {
//                addToMap = true;
                    currMap = waterToLight;
                } else if (line.contains("light-to-temperature")) {
//                addToMap = true;
                    currMap = lightToTemperature;
                } else if (line.contains("temperature-to-humidity")) {
//                addToMap = true;
                    currMap = temperatureToHumidity;
                } else if (line.contains("humidity-to-location")) {
//                addToMap = true;
                    currMap = humidityToLocation;
                } else {
                    long[] vals = stringToLongArray(line.split("[ ]+"));
                    long source = vals[1];
                    long[] destAndRange = new long[]{vals[0], vals[2]};
                    currMap.put(source, destAndRange);
                }
            }
        }
    }

    private long[] stringToLongArray(String[] a) {
        long[] returnVal = new long[a.length];
        for (int i = 0; i < a.length; i++) {
            returnVal[i] = Long.parseLong(a[i]);
        }
        return returnVal;
    }

    private void addToMap(TreeMap<int[], Integer> map, int[] vals) {
        int destStart = vals[0];
        int sourceStart = vals[1];
        int range = vals[2];
        map.put(new int[]{sourceStart, range}, destStart);
    }

    private boolean rangesOverlap(long start1, long end1, long start2, long end2) {
        return (start2 <= start1 && start1 <= end2) || (start2 <= end1 && end1 <= end2);
    }

    private Set<Long> possibleKeys(TreeMap<Long, long[]> map, long input, long inputRange) {
        Long first = map.floorKey(input);
        if (first == null) {
            first = map.firstKey();
        }
        Long last = map.higherKey(input + inputRange - 1);
        if (last == null) {
            last = map.lastKey();
        }
        Set<Long> returnVal = map.subMap(first, last).keySet();
        return returnVal;
    }

    private long[] findNextVal(TreeMap<Long, long[]> map, long[] inputAndRange) {
        long input = inputAndRange[0];
        long inputRange = inputAndRange[1];
        // default for vals that aren't found
        long dest = input;
        long destRange = 1;

        // check for possible match (the greatest element that's smaller than input)
        for (long source : possibleKeys(map, input, inputRange)) {
            // Integer source = map.floorKey(input);
            long[] destData = map.get(source);
            long sourceRange = destData[1];
            if (rangesOverlap(input, input + inputRange - 1, source, source + sourceRange - 1)) {
                return destData;
            }
//            if (source != null) {
//            }
        }
        return new long[]{dest, destRange};
    }

    private long findLocationFromSeed(long seed) {
        long[] soilAndRange = findNextVal(seedToSoil, new long[] {seed, 1});
        long[] fertilizerAndRange = findNextVal(soilToFertilizer, soilAndRange);
        long[] waterAndRange = findNextVal(fertilizerToWater, fertilizerAndRange);
        long[] lightAndRange = findNextVal(waterToLight, waterAndRange);
        long[] tempAndRange = findNextVal(lightToTemperature, lightAndRange);
        long[] humidityAndRange = findNextVal(temperatureToHumidity, tempAndRange);
        long[] locationAndRange = findNextVal(humidityToLocation, humidityAndRange);
        return locationAndRange[0];
    }

    public long returnMinLocation() {
        long min = INFINITY;
        for (long seed : seeds) {
            min = Math.min(min, findLocationFromSeed(seed));
        }
        return min;
    }
}
