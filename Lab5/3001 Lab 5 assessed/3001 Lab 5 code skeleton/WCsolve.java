
/**
 * Applies uninformed search algorithms to Word Chess puzzles. 
 * For a problem like SICK -> WELL, you need to return a solution 
 * in the form <"SICK", "SILK", "SILL", "WILL", "WELL">.
 */
import java.util.*;

class LinkedWords {
    public LinkedWords prev;
    public String word;
    public int level;

    public LinkedWords(LinkedWords prev, String word, int level) {
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
        Queue<LinkedWords> frontier = new LinkedList<>();
        LinkedWords startNode = new LinkedWords(null, start, 0);
        HashSet<String> unvisited = new HashSet<>(Lab5.wordsList);
        int min = 100000;
        LinkedWords testNode;
        List<List<String>> results = new ArrayList<List<String>>();

        frontier.add(startNode);
        while (!frontier.isEmpty()) {
            testNode = frontier.remove();
            // Loop Though all the characters in the test word
            String testWord = testNode.word;
            for (int i = 0; i < testWord.length(); i++) {
                char charOrig = testWord.charAt(i);
                char[] newChar = testWord.toCharArray();
                // Check if changing a letter creates a differant word in unvisited or is the
                // target
                for (char charLoop = 'A'; charLoop <= 'Z'; charLoop++) {
                    if (charLoop == charOrig) {
                        continue;
                    }
                    newChar[i] = charLoop;
                    String newString = new String(newChar);

                    if (unvisited.contains(newString)) {
                        LinkedWords n = new LinkedWords(testNode, newString, testNode.level + 1);
                        frontier.add(n);
                        unvisited.remove(newString);
                    }

                    if (newString.equals(target)) {
                        results.add(getResult(target, testNode));
                        if (testNode.level < min) {
                            min = testNode.level;
                        } else {
                            continue;
                        }
                    }

                }
            }
            if (testNode.level > min) {
                return (ArrayList<String>) results.get(0);
            }
        }

        // System.out.println(results);
        return null;
    }

    public static List<String> getResult(String target, LinkedWords testNode) {
        List<String> result = new ArrayList<>();
        result.add(target);
        LinkedWords node = testNode;
        while (node != null) {
            result.add(node.word);
            node = node.prev;
        }
        Collections.reverse(result);

        return result;
    }
}
