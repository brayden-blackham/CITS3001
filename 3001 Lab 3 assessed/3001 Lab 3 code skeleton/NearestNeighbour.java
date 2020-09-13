
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
        double bestCost = 0;
        for (int i = 0; i < cs.length - 1; i++) {
            bestCost += table[cs[i]][cs[i + 1]];
        }
        bestCost += table[cs[cs.length - 1]][cs[0]];

        boolean improvement = true;
        // obTour = Overall Best Tour
        int[] obTour = cs;
        // ibTour = Iteration Best Tour
        int[] ibTour = cs;
        while (improvement) {
            improvement = false;
            for (int i = 0; i < cs.length - 1; i++) {
                for (int j = i + 1; j < cs.length; j++) {
                    // testTour = Tour used to test the swap
                    int[] testTour = optSwap(i, j, obTour);
                    double testCost = 0;
                    for (int k = 0; k < testTour.length - 1; k++) {
                        testCost += table[testTour[k]][testTour[k + 1]];
                    }
                    testCost += table[testTour[testTour.length - 1]][testTour[0]];
                    if (testCost < bestCost) {
                        improvement = true;
                        bestCost = testCost;
                        ibTour = testTour;
                    }
                }
            }
            obTour = ibTour;
        }
        return obTour;
    }

    private static int[] optSwap(int i, int j, int[] tour) {
        int[] swappedTour = new int[tour.length];
        for (int k = 0; k <= i - 1; k++) {
            swappedTour[k] = tour[k];
        }
        int dif = 0;
        for (int k = i; k <= j; k++) {
            swappedTour[k] = tour[j - dif];
            dif++;
        }
        for (int k = j + 1; k < tour.length; k++) {
            swappedTour[k] = tour[k];
        }
        return swappedTour;

    }
}