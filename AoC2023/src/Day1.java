import edu.princeton.cs.algs4.In;
import java.util.HashMap;

public class Day1 {
    public HashMap<String, Integer> digitMap = new HashMap<>();

    public Day1(String file) {
        fillDigitMap();
        readFile(file);
    }

    void fillDigitMap() {
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

    public void readFile(String file) {
        In in = new In(file);
        int sum = 0;
        while (in.hasNextLine()) {
            String line = in.readLine();
            sum += findCalibrationValue(line);
        }
        System.out.println("Sum: " + sum);
    }

    int findFirstInt(String s) {
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

    int findSecondInt(String s) {
        for (int i = s.length() - 1; i >= 0; i--) {
            char c = s.charAt(i);
            if (c >= '0' && c <= '9') {
                return Integer.parseInt(String.valueOf(c));
            }
        }
        return -1;
    }

    private int getIndexOfFirstDigit(String s) {
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                return i;
            }
        }
        return -1; // if a digit is not found in s
    }
    private int[] findFirstWordNum(String s, boolean reversed) {
        int minIndex = s.length(); // an impossible index
        int num = -1;
        for (String w : digitMap.keySet()) {
            if (reversed) {
                w = new StringBuilder(w).reverse().toString();
            }
            int index = s.indexOf(w);
            if (index != -1 && index < minIndex) {
                minIndex = index;
                if (reversed) {
                    w = new StringBuilder(w).reverse().toString();
                }
                num = digitMap.get(w);
            }
        }
        if (num == -1) {
            return null; //if there are no wordStrings in s
        }
        return new int[]{minIndex, num};
    }

    int findFirstInt(String s, boolean reversed) {
        if (reversed) {
            s = new StringBuilder(s).reverse().toString();
        }
        // find first int
        int firstIntIndex = getIndexOfFirstDigit(s);
        // find first word digit
        int[] wordNum = findFirstWordNum(s, reversed);
        if (wordNum == null || firstIntIndex < wordNum[0]) {
            return s.charAt(firstIntIndex) - '0'; // the numeric val
        } else {
            return wordNum[1];
        }
    }

    int findFirstIntAdvanced(String s) {
        return findFirstInt(s, false);
    }

    int findSecondIntAdvanced(String s) {
        return findFirstInt(s, true);
    }

    int findCalibrationValue(String s) {
        int firstInt = findFirstIntAdvanced(s);
        int secondInt = findSecondIntAdvanced(s);
        return firstInt * 10 + secondInt;
    }
}
