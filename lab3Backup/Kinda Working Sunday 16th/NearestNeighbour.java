
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
        System.out.println("cs[]: ");
        for (int j = 0; j < cs.length; j++) {
            System.out.print(cs[j] + " ");
        }
        System.out.println("");

        double best = cost(cs, table);
        System.out.println("COST: " + best);
        int numCities = cs.length;
        int visited = 0;
        int current = 0;
        int[] newTour = cs;
        while (visited < numCities) {
            for (int i = 0; i < numCities - 1; i++) {
                for (int j = i + 1; j < numCities; j++) {
                    int[] newerTour = Swap(i, j, newTour);
                    // System.out.println("newTour[]: ");
                    // for (int k = 0; k < newerTour.length; k++) {
                    // System.out.print(newerTour[k] + " ");
                    // }
                    // System.out.println("");
                    double newDistance = cost(newerTour, table);
                    // System.out.println("newDistance:" + newDistance);
                    // System.out.println("newDistance: " + newDistance);
                    if (newDistance < best) {
                        // System.out.println("newDistance < best");
                        visited = 0;
                        best = newDistance;
                        newTour = newerTour;
                        System.out.println("newTour[]: ");
                        for (int k = 0; k < newTour.length; k++) {
                            System.out.print(newTour[k] + " ");
                        }
                        System.out.println("");
                    }
                }
            }
            visited++;

        }
        // System.out.println("newTour[]: ");
        // for (int j = 0; j < newTour.length; j++) {
        // System.out.print(newTour[j] + " ");
        // }
        // System.out.println("");
        return newTour;
    }

    private static double cost(int[] newTour, double[][] table) {
        // System.out.println("newTour[]: ");
        // for (int j = 0; j < newTour.length; j++) {
        // System.out.print(newTour[j] + " ");
        // }
        // System.out.println("");
        double cost = 0;
        for (int i = 0; i < newTour.length - 1; i++) {
            cost += table[newTour[i]][newTour[i + 1]];
        }
        cost += table[newTour[newTour.length - 1]][newTour[0]];
        // System.out.println(cost);
        return cost;
    }

    private static int[] Swap(int i, int j, int[] tour) {
        int size = tour.length;
        int[] newerTour = new int[tour.length];
        for (int c = 0; c <= i - 1; c++) {
            newerTour[c] = tour[c];
        }
        int change = 0;
        for (int d = i; d <= j; d++) {
            newerTour[d] = tour[j - change];
            change++;
        }
        for (int e = j + 1; e < size; e++) {
            newerTour[e] = tour[e];
        }
        return newerTour;

    }
}