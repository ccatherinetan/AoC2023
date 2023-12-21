import edu.princeton.cs.algs4.In;

import java.util.PriorityQueue;

public class Day4 {
    PriorityQueue<Integer> queue;
    public int[] matchesPerCard;

    public Day4(String file) {
        matchesPerCard = new int[209];
        // matchesPerCard = instantiateArray(matchesPerCard, -1);
        queue = new PriorityQueue<>();
        readFile(file);
    }

    private int[] instantiateArray(int[] a, int initVal) {
        for (int i = 0; i < a.length; i++) {
            a[i] = initVal; // to prevent collision with 0
        }
        return a;
    }

    public void readFile(String file) {
        In in = new In(file);
        int totalScore = 0;
        while (in.hasNextLine()) {
            String[] line = in.readLine().split("\\:[ ]+");
            int gameNum = getCardNum(line[0]);
            String[] inputNums = line[1].split("[ ]+\\|[ ]+");
            int[] winningNums = stringToIntArray(inputNums[0].split("[ ]+"));
            int[] myNums = stringToIntArray(inputNums[1].split("[ ]+"));
            // update matchesPerCard (for cache)
            matchesPerCard[gameNum - 1] = numMatches(winningNums, myNums);

            // update score
            int currScore = getScore(winningNums, myNums);
            totalScore += currScore;
        }
        System.out.println(totalScore);
    }

    private int getCardNum(String s) {
        return Integer.parseInt(s.split("[ ]+")[1]);
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

    private int numMatches(int[] winningNums, int[] myNums) {
        int match = 0;
        for (int i = 0; i < myNums.length; i++) {
            if (contains(myNums[i], winningNums)) {
               match++;
            }
        }
        return match;
    }

    private void insertElementsIntoQueue(int numElements) {
        for (int i = 0; i < numElements; i++) {
            queue.add(i); // add in ascending order for improved runtime
        }
    }
    public int countTotalCards() {
        insertElementsIntoQueue(209);
        int totalCount = 209; // 209 from the original 209 cards
        while (!queue.isEmpty()) {
            int currGame = queue.remove();
            int numMatches = matchesPerCard[currGame];
            totalCount += numMatches;
            for (int i = 0; i < numMatches; i++) {
                int toAdd = currGame + 1 + i;
                queue.add(toAdd);
            }
        }
        return totalCount;
    }
}
