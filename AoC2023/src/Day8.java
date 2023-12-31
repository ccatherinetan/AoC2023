import edu.princeton.cs.algs4.In;

import java.util.HashMap;

public class Day8 {
    String sequence;
    HashMap<String, Node> nodes;
    private class Node {
        String node;
        Node left;
        Node right;

        public Node(String n, Node l, Node r) {
            node = n;
            left = l;
            right = r;
            nodes.put(node, this);
        }

        public Node nextNode(char direction) {
            if (direction == 'L') {
                return left;
            } else if (direction == 'R') {
                return right;
            }
            return null;
        }
    }
    public Day8(String file) {
        nodes = new HashMap();
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
                Node curr = findNode(name);

                String[] lr = split[1].split(",[ ]+");
                String lName = lr[0].substring(1);
                String rName = lr[1].substring(0, lr[1].length() - 1);
                Node left = findNode(lName);
                Node right = findNode(rName);

                curr.left = left;
                curr.right = right;
            }
        }
    }

    public int numSteps() {
        int steps = 0;
        // StringBuilder pathTracker = new StringBuilder(sequence);
        Node curr = nodes.get("AAA");
        Node end = nodes.get("ZZZ");
        while (curr != end) {
//            if (pathTracker.isEmpty()) {
//                pathTracker.append(sequence);
//            }
            char move = sequence.charAt(steps % sequence.length());
            curr = curr.nextNode(move);
            steps++;
        }
        return steps;
    }

}
