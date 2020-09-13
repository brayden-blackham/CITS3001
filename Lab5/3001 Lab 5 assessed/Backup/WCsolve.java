
/**
 * Applies uninformed search algorithms to Word Chess puzzles. 
 * For a problem like SICK -> WELL, you need to return a solution 
 * in the form <"SICK", "SILK", "SILL", "WILL", "WELL">.
 */
import java.util.*;

class Node {
    public Node prev;
    public String word;
    public int level;

    public Node(Node prev, String word, int level) {
        this.prev = prev;
        this.word = word;
        this.level = level;
    }
}

public class WCsolve {
    public WCsolve() {
    }

    /**
     * Solves the puzzle start -> target using breadth-first search. Returns one
     * optimal solution.
     */
    public static ArrayList<String> solve(String start, String target) {
        List<String> wordList = Lab5.wordsList;

        List<List<String>> result = new ArrayList<List<String>>();

        HashSet<String> unvisited = new HashSet<>();
        unvisited.addAll(wordList);

        LinkedList<Node> queue = new LinkedList<>();
        Node node = new Node(null, start, 0);
        queue.offer(node);

        int minLen = Integer.MAX_VALUE;
        while (!queue.isEmpty()) {
            Node top = queue.poll();

            // top if have shorter result already
            if (result.size() > 0 && top.level > minLen) {
                System.out.println(result);
                // return null;
                return (ArrayList<String>) result.get(0);
            }

            for (int i = 0; i < top.word.length(); i++) {
                char c = top.word.charAt(i);
                char[] arr = top.word.toCharArray();
                for (char j = 'Z'; j >= 'A'; j--) {
                    if (j == c) {
                        continue;
                    }
                    arr[i] = j;
                    String t = new String(arr);

                    if (t.equals(target)) {
                        // add to result
                        List<String> aResult = new ArrayList<>();
                        aResult.add(target);
                        Node p = top;
                        while (p != null) {
                            aResult.add(p.word);
                            p = p.prev;
                        }

                        Collections.reverse(aResult);
                        result.add(aResult);

                        // stop if get shorter result
                        if (top.level <= minLen) {
                            minLen = top.level;
                        } else {
                            System.out.println(result);
                            // return null;
                            return (ArrayList<String>) result.get(0);
                        }
                    }

                    if (unvisited.contains(t)) {
                        Node n = new Node(top, t, top.level + 1);
                        queue.offer(n);
                        unvisited.remove(t);
                    }
                }
            }
        }

        System.out.println(result);
        return null;
        // return (ArrayList<String>) result.get(0);
    }
}
