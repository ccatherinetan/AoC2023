import edu.princeton.cs.algs4.In;

import java.util.HashMap;

public class Main {
    public static HashMap<String, Integer> digitMap = new HashMap<>();
    public static void main(String[] args) {
        readFile("src/data/data.txt");
        System.out.println("Hello world!");
    }

    static void updateDigitMap() {
        // digitMap.clear();
        digitMap.put("one", 1);
        digitMap.put("two", 2);
        digitMap.put("three", 3);
        digitMap.put("four", 4);
        digitMap.put("five", 5);
        digitMap.put("six", 6);
        digitMap.put("seven", 7);
        digitMap.put("eight", 8);
        digitMap.put("nine", 9);
    }

    public static void readFile(String file) {
        updateDigitMap();
        In in = new In(file);
        int sum = 0;
        while (in.hasNextLine()) {
            String line = in.readLine();
            sum += findCalibrationValue(line);
        }
        System.out.println("Sum: " + sum);
    }

    static int findFirstInt(String s) {
        int firstInt = -1;
        int searchUpToIndex = -1;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= '0' && c <= '9') {
                firstInt = Integer.parseInt(String.valueOf(c));
                searchUpToIndex = i - 1;
                break;
                // return Integer.parseInt(String.valueOf(c));
            }
        }

        if (searchUpToIndex >= 2) { //i.e. it's at least 3 chars long
            String search = s.substring(0, searchUpToIndex);
            for (String numString : digitMap.keySet()) {
                if (search.contains(numString)) {
                    return digitMap.get(numString);
                }
            }
        }
        return firstInt;
    }

    static int findSecondInt(String s) {
        for (int i = s.length() - 1; i >= 0; i--) {
            char c = s.charAt(i);
            if (c >= '0' && c <= '9') {
                return Integer.parseInt(String.valueOf(c));
            }
        }
        return -1;
    }

    static int findCalibrationValue(String s) {
        //find firstInt
        int firstInt = findFirstInt(s);
        int secondInt = findSecondInt(s);

        return firstInt * 10 + secondInt;
    }
}
