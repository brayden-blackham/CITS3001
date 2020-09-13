
/**
 * Implements the Nearest Neighbour algorithm for the TSP, and
 * an iterative improvement method that uses 2-OPT greedily.
 * Results are returned as an array of indices into the table argument, 
 * e.g. if the table has length four, a valid result would be {2,0,1,3}. 
 */
import java.util.*;

public class NearestNeighbour {
    private NearestNeighbour() {
    }

    /**
     * Returns the shortest tour found by exercising the NN algorithm from each
     * possible starting city in table. table[i][j] == table[j][i] gives the cost of
     * travel between City i and City j.
     */
    public static int[] tspnn(double[][] table) {
        // paths is a 2D array of the shortest paths from each starting city
        int[][] paths = new int[table.length][table.length];
        double[] costs = new double[table.length];
        for (int i = 0; i < paths.length; i++) {
            // The Path, set all to -1 to indicate they haven't been visted.
            int[] path = new int[table.length];
            for (int k = 0; k < path.length; k++) {
                path[k] = -1;
            }
            // The cost
            double cost = 0;
            // The length path
            int pathLen = 0;
            // The current City
            int currentCity = i;
            // Set first path
            path[0] = currentCity;
            while (pathLen < path.length - 1) {
                // Set a large min
                double min = 1000000000;
                int nextCity = -1;
                // Adds the starting city to path
                for (int j = 0; j < table.length; j++) {
                    boolean containsJ = false;
                    for (int element : path) {
                        if (element == j) {
                            containsJ = true;
                            break;
                        }
                    }
                    if (!containsJ) {
                        if (table[currentCity][j] < min && table[currentCity][j] != 0) {
                            min = table[currentCity][j];
                            nextCity = j;
                        }
                    }
                }
                // System.out.println("Path[" + (pathLen) + "]: " + currentCity);
                // System.out.println("Cost Total =" + cost);

                path[pathLen + 1] = nextCity;
                cost += table[currentCity][nextCity];
                currentCity = nextCity;
                pathLen++;
            }
            // System.out.println("Path[" + (pathLen) + "]: " + currentCity);
            // System.out.println("Cost Total =" + cost);
            cost += table[path[0]][path[path.length - 1]];
            costs[i] = cost;
            paths[i] = path;

            // System.out.print("Paths[" + i + "]: ");
            // for (int j = 0; j < paths[i].length; j++) {
            // System.out.print(paths[i][j] + " ");
            // }
            // System.out.print(" COST: " + costs[i]);
            // System.out.println("");
            // System.out.println("");
        }
        double min = 1000000000;
        int lowest = -1;
        for (int j = 0; j < costs.length; j++) {
            if (costs[j] < min) {
                min = costs[j];
                lowest = j;
            }
        }
        return paths[lowest];
    }

    /**
     * Uses 2-OPT repeatedly to improve cs, choosing the shortest option in each
     * iteration. You can assume that cs is a valid tour initially. table[i][j] ==
     * table[j][i] gives the cost of travel between City i and City j.
     */
    public static int[] tsp2opt(int[] cs, double[][] table) {
        boolean improvement = true;
        int[] newTour = new int[cs.length];
        int[] best = cs;
        while (improvement) {
            improvement = false;
            for (int i = 1; i < cs.length - 2; i++) {
                for (int j = i + 1; j < cs.length; j++) {
                    if ((table[i][i - 1] + table[j + 1][j]) > (table[i][j + 1] + table[i - 1][j])) {
                        // add point up to i to newTour
                        for (int k = 0; k < i; k++) {
                            newTour[k] = cs[k];
                        }
                        // swap i and j into newTour
                        int count = 0;
                        for (int k = i; k <= j; k++) {
                            newTour[k] = cs[j - count];
                            count++;
                        }
                        // add point after j to newTour
                        for (int k = j + 1; k < cs.length; k++) {
                            newTour[k] = cs[k];
                        }
                        System.out.println("Made it here");
                    }

                    double bestCost = 0;
                    for (int k = 1; k < best.length; k++) {
                        bestCost += table[best[k - 1]][best[k]];
                    }
                    bestCost += table[0][best.length - 1];

                }
            }

        }
        // COMPLETE THIS METHOD
        return new int[] {};
    }
}