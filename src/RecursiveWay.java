import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class RecursiveWay {
    private static boolean isThereSubsetSum(
        List<Integer> arr, int arraySize, Integer sum,
        boolean hasMatchOnly,
        List<Integer> combination, List<List<Integer>> matchingCombinations) {
        // Recursive methods:
        // see https://favtutor.com/blogs/subset-sum-problem

        if (sum.equals(0)) {
            // subset match found
            if (!hasMatchOnly) {
                matchingCombinations.add(combination);
            }
            return true;
        }
        if (arraySize == 0) {
            return false;
        }

        if (arr.get(arraySize - 1) > sum) {
            // if element is greater than sum, skip it
            return isThereSubsetSum(arr, arraySize - 1, sum, hasMatchOnly, combination, matchingCombinations);
        }

        // Try with array element included in subset
        List<Integer> copyWithElement = new ArrayList<>();
        if (!hasMatchOnly) {
            copyWithElement.addAll(combination);
            copyWithElement.add(arr.get(arraySize - 1));
        }
        var withElementIncluded = isThereSubsetSum(arr, arraySize - 1, sum - arr.get(arraySize - 1), hasMatchOnly, copyWithElement, matchingCombinations);

        // Try with array element not included in subset
        List<Integer> copyWithoutElement = new ArrayList<>();
        if (!hasMatchOnly) {
            copyWithoutElement.addAll(combination);
        }
        var withoutElement = isThereSubsetSum(arr, arraySize - 1, sum, hasMatchOnly, copyWithoutElement, matchingCombinations);

        return withElementIncluded || withoutElement;
    }

    static List<List<Integer>> findSubsets(List<Integer> transactionList, Integer sum) {
        var combination = new ArrayList<Integer>();
        var matchingCombinations = new ArrayList<List<Integer>>();
        isThereSubsetSum(transactionList, transactionList.size(), sum, false, combination, matchingCombinations);
        //        if (matchingCombinations.size() <5) {
//            System.out.println(matchingCombinations.toString());
//        }
        return matchingCombinations;
    }

    static boolean hasMatch(List<Integer> transactionList, Integer sum) {
        var combination = new ArrayList<Integer>();
        var matchingCombinations = new ArrayList<List<Integer>>();
        return isThereSubsetSum(transactionList, transactionList.size(), sum, true, combination, matchingCombinations);
        //System.out.println("Recursive Way\n Has match: " + matchFound + "\n Duration (sec): " + Duration.between(start, Instant.now()).toSeconds());
    }

    static ResultModel getTestResult(List<Integer> transactionList, Integer sum) {
        var start = Instant.now();
        var hasMatch = hasMatch(transactionList, sum);
        var end1 =  Instant.now();
        var hasMatchDuration = Duration.between(start, end1).toSeconds();
        var matches = findSubsets(transactionList, sum);
        return new ResultModel("recursive", transactionList.size(), sum, hasMatch, hasMatchDuration, matches.size(), Duration.between(end1, Instant.now()).toSeconds());
    }

}
