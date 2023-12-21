import edu.princeton.cs.algs4.In;

public class Day4 {
    public Day4(String file) {
        readFile(file);
    }

    public void readFile(String file) {
        In in = new In(file);
        int totalScore = 0;
        while (in.hasNextLine()) {
            String line = in.readLine();
            String[] inputNums = line.split(": ")[1].split(" | ");
            int[] winningNums = stringToIntArray(inputNums[0].split("[ ]+"));
            int[] myNums = stringToIntArray(inputNums[1].split("[ ]+"));
            int currScore = getScore(winningNums, myNums);
            totalScore += currScore;
        }
        System.out.println(totalScore);
    }

    private int[] stringToIntArray(String[] a) {
        int[] returnVal = new int[a.length];
        for (int i = 0; i < a.length; i++) {
            returnVal[i] = Integer.parseInt(a[i]);
        }
        return returnVal;
    }

    private boolean contains(int val, int[] searchArray) {
        for (int i = 0; i < searchArray.length; i++) {
            if (searchArray[i] == val) {
                return true;
            }
        }
        return false;
    }

    private int getScore(int[] winningNums, int[] myNums) {
        int score = 0;
        for (int i = 0; i < myNums.length; i++) {
            if (contains(myNums[i], winningNums)) {
                if (score == 0) {
                    score++;
                } else {
                    score *= 2;
                }
            }
        }
        return score;
    }
}
