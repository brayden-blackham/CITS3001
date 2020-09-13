package lab1;

public class sortTest {
    public static void main(String[] args) {
        int[] arr;

        if (args.length == 1)
            arr = createarray(Integer.parseInt(args[0]));
        else {
            arr = new int[0];
            printUsage();
        }

        callAllSortAlgorithms(arr);
        System.exit(0);
    }

    private static void callAllSortAlgorithms(int[] arr) {
    }

    private static int[] createarray(int parseInt) {
        return null;
    }

    private static void printUsage() {
        System.out.println("[USAGE]: java SorterTest size");
        System.out.println("[EG]: For creating a random array of size 1000");
        System.out.println("[USE]: java sortTest 1000");
        System.exit(1);
    }
}