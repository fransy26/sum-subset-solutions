import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class RecursiveWay {
    private static boolean isThereSubsetSum(List<Integer> arr, int arraySize, Integer sum, List<Integer> combination, List<List<Integer>> matchingCombinations) {
        // Recursive methods:
        // see https://favtutor.com/blogs/subset-sum-problem

        if (sum.equals(0)) {
            // subset match found
            matchingCombinations.add(combination);
            return true;
        }
        if (arraySize == 0) {
            return false;
        }

        if (arr.get(arraySize - 1) > sum) {
            // if element is greater than sum, skip it
            return isThereSubsetSum(arr, arraySize - 1, sum, combination, matchingCombinations);
        }

        // Try with array element included in subset
        List<Integer> copyWithElement = new ArrayList<>();
        copyWithElement.addAll(combination);
        copyWithElement.add(arr.get(arraySize-1));
        var withElementIncluded = isThereSubsetSum(arr, arraySize - 1, sum - arr.get(arraySize - 1), copyWithElement, matchingCombinations);

        // Try with array element not included in subset
        List<Integer> copyWithoutElement = new ArrayList<>();
        copyWithoutElement.addAll(combination);
        var withoutElement = isThereSubsetSum(arr, arraySize - 1, sum, copyWithoutElement, matchingCombinations);

        return withElementIncluded || withoutElement;
    }

    static void findSubsets(List<Integer> transactionList, Integer sum) {
        var start = Instant.now();
        var combination = new ArrayList<Integer>();
        var matchingCombinations = new ArrayList<List<Integer>>();
        isThereSubsetSum(transactionList, transactionList.size(), sum, combination, matchingCombinations);
        System.out.println("Recursive Way\n Matching suggestions found: " + matchingCombinations.size() + "\n Duration (sec): " + Duration.between(start, Instant.now()).toSeconds());
        if (matchingCombinations.size() <5) {
            System.out.println(matchingCombinations.toString());
        }
    }

}
