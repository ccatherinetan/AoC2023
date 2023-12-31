import edu.princeton.cs.algs4.In;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.HashSet;

public class Day8 {
    String sequence;
    HashMap<String, Node> nodes;
    HashSet<String> startNodes;
    HashSet<String> endNodes;
    private class Node {
        String node;
        String left;
        String right;
        // static HashMap<String, Node> nodes;
        public Node(String n, String l, String r) {
            node = n;
            left = l;
            right = r;
            if (n.charAt(2) == 'A') {
                startNodes.add(n);
            } else if (n.charAt(2) == 'Z') {
                endNodes.add(n);
            }
            // nodes.put(node, this);
        }

        public Node nextNode(char direction) {
            if (direction == 'L') {
                return nodes.get(left);
            } else if (direction == 'R') {
                return nodes.get(right);
            }
            return null;
        }
    }
    public Day8(String file) {
        nodes = new HashMap();
        startNodes = new HashSet<>();
        endNodes = new HashSet<>();
        readFile(file);
    }

    private Node findNode(String node) {
        return nodes.getOrDefault(node, new Node(node, null, null));
    }

    public void readFile(String file) {
        In in = new In(file);
        sequence = in.readLine();
        while (in.hasNextLine()) {
            String line = in.readLine();
            if (line != "") {
                String[] split = line.split("[ ]+=[ ]+");
                String name = split[0];

                String[] lr = split[1].split(",[ ]+");
                String lName = lr[0].substring(1);
                String rName = lr[1].substring(0, lr[1].length() - 1);

                Node curr = new Node(name, lName, rName);
                nodes.put(name, curr);
//                Node left = findNode(lName);
//                Node right = findNode(rName);
//                curr.left = left;
//                curr.right = right;
            }
        }
    }

    public int numSteps() {
        int steps = 0;
        Node curr = nodes.get("AAA");
        Node end = nodes.get("ZZZ");
        while (curr != end) {
            char move = sequence.charAt(steps % sequence.length());
            curr = curr.nextNode(move);
            steps++;
        }
        return steps;
    }

    public long ghostSteps() {
        long total = 1;
        for (String start : startNodes) {
            int steps = stepsFromStart(start);
            total = findLCM(total, steps);
        }
        return total;
    }

    private int stepsFromStart(String start) {
        int steps = 0;
        Node curr = nodes.get(start);
        while (!endNodes.contains(curr.node)) {
            char move = sequence.charAt(steps % sequence.length());
            curr = curr.nextNode(move);
            steps++;
        }
        return steps;
    }

    public void test() {
        long lcm = findLCM(55297709, 12083);
        System.out.println(lcm);
    }

    private long findLCM(long a, long b) {
        a = Math.abs(a);
        b = Math.abs(b);
        BigInteger gcd = BigInteger.valueOf(a).gcd(BigInteger.valueOf(b));
        BigInteger product = BigInteger.valueOf(a).multiply(BigInteger.valueOf(b));
        // LCM = (num1 * num2) / GCD
        return product.divide(gcd).longValue();
    }
}
