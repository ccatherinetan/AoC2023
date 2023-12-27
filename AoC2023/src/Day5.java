import edu.princeton.cs.algs4.In;
import java.util.*;

public class Day5 {
    long[] seeds;
    TreeMap<Long, long[]> seedToSoil;
    TreeMap<Long, long[]> soilToFertilizer;
    TreeMap<Long, long[]> fertilizerToWater;
    TreeMap<Long, long[]> waterToLight;
    TreeMap<Long, long[]>lightToTemperature;
    TreeMap<Long, long[]> temperatureToHumidity;
    TreeMap<Long, long[]> humidityToLocation;

    public Day5(String file) {
        seedToSoil = new TreeMap<>();
        soilToFertilizer = new TreeMap<>();
        fertilizerToWater = new TreeMap<>();
        waterToLight = new TreeMap<>();
        lightToTemperature = new TreeMap<>();
        temperatureToHumidity = new TreeMap<>();
        humidityToLocation = new TreeMap<>();
        readFile(file);
    }

    public void readFile(String file) {
        In in = new In(file);
        TreeMap<Long, long[]> currMap = null;
        while (in.hasNextLine()) {
            String line = in.readLine();
            if (line != "") { // not an empty line
                if (line.contains("seeds")) {
                    String[] seedsStr = line.split("\\:[ ]+")[1].split("[ ]+");
                    seeds = stringToLongArray(seedsStr);
                } else if (line.contains("seed-to-soil")) {
                    currMap = seedToSoil;
                } else if (line.contains("soil-to-fertilizer")) {
                    currMap = soilToFertilizer;
                } else if (line.contains("fertilizer-to-water")) {
                    currMap = fertilizerToWater;
                } else if (line.contains("water-to-light")) {
                    currMap = waterToLight;
                } else if (line.contains("light-to-temperature")) {
                    currMap = lightToTemperature;
                } else if (line.contains("temperature-to-humidity")) {
                    currMap = temperatureToHumidity;
                } else if (line.contains("humidity-to-location")) {
                    currMap = humidityToLocation;
                } else {
                    long[] vals = stringToLongArray(line.split("[ ]+"));
                    long source = vals[1];
                    long[] destAndRange = new long[]{vals[0], vals[2]};
                    currMap.put(source, destAndRange);
                }
            }
        }
        System.out.println("Done reading file.");
    }

    private long[] stringToLongArray(String[] a) {
        long[] returnVal = new long[a.length];
        for (int i = 0; i < a.length; i++) {
            returnVal[i] = Long.parseLong(a[i]);
        }
        return returnVal;
    }

    private long findNextVal(long input, TreeMap<Long, long[]> map) {
        long returnVal = input;
        Long possibleKey = map.floorKey(input);
        if (possibleKey != null) {
            long[] destAndRange = map.get(possibleKey);
            long destStart = destAndRange[0];
            long sourceEnd = possibleKey + destAndRange[1] - 1;
            if (inRange(input, possibleKey, sourceEnd)) {
                return destStart + (input - possibleKey);
            }
        }
        return returnVal;
    }

    private boolean inRange(long val, long start, long end) {
        return start <= val && val <= end;
    }

    private long findLocationFromSeed(long seed) {
        long soil = findNextVal(seed, seedToSoil);
        long fertilizer = findNextVal(soil, soilToFertilizer);
        long water = findNextVal(fertilizer, fertilizerToWater);
        long light = findNextVal(water, waterToLight);
        long temp = findNextVal(light, lightToTemperature);
        long humidity = findNextVal(temp, temperatureToHumidity);
        long location = findNextVal(humidity, humidityToLocation);
        return location;
    }

    public long returnMinLocation() {
        long min = Long.MAX_VALUE;
        for (long seed : seeds) {
            min = Math.min(min, findLocationFromSeed(seed));
        }
        return min;
    }
}
