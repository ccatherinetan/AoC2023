import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

public class Day7 {
    PriorityQueue<Hand> rankedHands;
//    Hand[] hands;
//    int[] bids;
    public Day7(String file) {
//        hands = new Hand[1000];
//        bids = new int[1000];
        rankedHands = new PriorityQueue<>();
        readFile(file);
    }

    private class Hand implements Comparable<Hand> {
        String hand;
        int bid;
        int type;
        public Hand(String h, int b) {
            hand = h;
            bid = b;
            type = getHandType(h);
        }

        /**
         * Returns the hand type of given hand. See key below. Larger values = higher rank.
         * @param h hand
         * @return hand type
         * 1. high card (all distinct)
         * 2. one pair
         * 3. 2 pairs
         * 4. 3 of a kind
         * 5. full house (3 x, 2 y)
         * 6. 4 of a kind
         * 7. 5 of a kind
         */
        private int getHandType(String h) {
            HashMap<Character, Integer> freq = new HashMap<>();
            for (int i = 0; i < h.length(); i++) {
                char c = h.charAt(i);
                if (freq.containsKey(c)) {
                    freq.put(c, freq.get(c) + 1);
                } else {
                    freq.put(c, 1);
                }
            }
            ArrayList<Integer> vals = new ArrayList(freq.values());
            if (vals.contains(5)) {
                return 7;
            } else if (vals.contains(4)) {
                return 6;
            } else if (vals.contains(3)) {
                if (vals.contains(2)) {
                    return 5; //full house
                }
                return 4;
            } else if (vals.contains(2)) {
                vals.remove(Integer.valueOf(2));
                if (vals.contains(2)) {
                    return 3; // 2 pairs
                }
                return 2; // 1 pair
            }
            return 1; // high card
        }

        int intVal(char c) {
            if (Character.isDigit(c)) {
                return Character.getNumericValue(c);
            } else if (c == 'T') {
                return 10;
            } else if (c == 'J') {
                return 11;
            } else if (c == 'Q') {
                return 12;
            } else if (c == 'K') {
                return 13;
            } else if (c == 'A') {
                return 14;
            }
            return 0;
        }

        @Override
        public int compareTo(Hand o) {
            if (this.type != o.type) {
                return this.type - o.type;
            }
            for (int i = 0; i < this.hand.length(); i++) {
                char cThis = this.hand.charAt(i);
                char cThat = o.hand.charAt(i);
                if (intVal(cThis) != intVal(cThat)) {
                    return intVal(cThis) - intVal(cThat);
                }
            }
            return 0;
        }
    }

    public void readFile(String file) {
        In in = new In(file);
        while (in.hasNextLine()) {
            String[] line = in.readLine().split("[ ]+");
            int bid = Integer.parseInt(line[1]);
            Hand h = new Hand(line[0], bid);
            rankedHands.add(h);
        }
    }

    public long totalWinnings() {
        long result = 0;
        int rank = 1;
        while (!rankedHands.isEmpty()) {
            Hand h = rankedHands.remove();
            result += (long) rank * h.bid;
            rank++;
        }
        return result;
    }
}
