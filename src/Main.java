import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final List<Integer> VALUES = List.of(3,4,5,2);


    private static final List<Integer> FIFTY_VALUES = List.of(1498	,
        439	,
        34	,
        243	,
        50	,
        1898	,
        109	,
        117	,
        100	,
        162	,
        109	,
        961	,
        322	,
        201	,
        135	,
        46	,
        1467	,
        54	,
        188	,
        969	,
        34	,
        132	,
        149	,
        44	,
        104	,
        33	,
        96	,
        100	,
        326	,
        54	,
        47	,
        162	,
        21	,
        461	,
        11	,
        909	,
        472	,1498	,
        439	,
        34	,
        243	,
        50	,
        1898	,
        109	,
        117	,
        100	,
        162	,
        109	,
        961	,
        322	,
        201	,
        135	,
        46	,
        1467	,
        54	,
        188	,
        969	,
        34	,
        132	,
        149	,
        44	,
        104	,
        33	,
        96	,
        100	,
        326	,
        54	,
        47	,
        162	,
        21	,
        461	,
        11	,
        909	,
        472);


    private static boolean isThereSubsetSum(List<Integer> arr, int array_size, Integer sum, List<Integer> combination) {
        // Recurisve methods:
        // see https://favtutor.com/blogs/subset-sum-problem

        if (sum.equals(0)) {
            // subset match found
            //System.out.println(combination.toString());
            return true;
        }
        if (array_size == 0)
            return false;

        if (arr.get(array_size - 1) > sum) {
            // if element is greater than sum, skip it
            return isThereSubsetSum(arr, array_size - 1, sum, combination);
        }

        // Try with array element included in subset
        List<Integer> copyWithElement = new ArrayList<>();
        copyWithElement.addAll(combination);
        copyWithElement.add(arr.get(array_size-1));
        var withElementIncluded = isThereSubsetSum(arr, array_size - 1, sum - arr.get(array_size - 1), copyWithElement);

        // Try with array element not included in subset
        List<Integer> copyWithoutElement = new ArrayList<>();
        copyWithoutElement.addAll(combination);
        var withoutElement = isThereSubsetSum(arr, array_size - 1, sum, copyWithoutElement);

        return withElementIncluded || withoutElement;
    }

    private static void findSubsets(List<Integer> transactionList, Integer sum) {
        var start = Instant.now();
        var combination = new ArrayList<Integer>();
        var found = isThereSubsetSum(transactionList, transactionList.size(), sum, combination);
        System.out.println("Sum " + sum + "\n Found: " + found + " for " + transactionList.size() + " transactions\n Duration (sec): " + Duration.between(start, Instant.now()).toSeconds());
    }
    public static void main(String[] args) {
        findSubsets(VALUES, 9);

        findSubsets(FIFTY_VALUES, 30000);
    }
}