import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

public class RecursiveWayWithMemory {
    private static boolean isThereSubsetSum(
        List<Integer> arr, int arraySize, Integer sum,
        boolean hasMatchOnly,
        List<Integer> combination, List<List<Integer>> matchingCombinations,
        Map<String, Boolean> stateCalcLookup) {

        // Recursive methods with memory:
        // see https://favtutor.com/blogs/subset-sum-problem
        // https://www.geeksforgeeks.org/subset-sum-problem-dp-25/

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

        // construct a unique map key from dynamic elements of the input
        Collections.sort(combination);

        String key = sum + "|" + arraySize + combination.stream()
            .map(String::valueOf)
            .collect(Collectors.joining("_"));

        // if the subproblem is seen for the first time, solve it and
        // store its result in a map
        if (stateCalcLookup.containsKey(key))
        {
            //System.out.println("using caching");
            return stateCalcLookup.get(key);
        }

        boolean result;
        // Calculate state and cached it
        if (arr.get(arraySize - 1) > sum) {
            // if element is greater than sum, skip it
            result = isThereSubsetSum(arr, arraySize - 1, sum, hasMatchOnly, combination, matchingCombinations, stateCalcLookup);
        } else {
            // Try with array element included in subset
            List<Integer> copyWithElement = new ArrayList<>();
            if (!hasMatchOnly) {
                copyWithElement.addAll(combination);
                copyWithElement.add(arr.get(arraySize - 1));
            }
            var withElementIncluded = isThereSubsetSum(arr, arraySize - 1, sum - arr.get(arraySize - 1),
                hasMatchOnly, copyWithElement, matchingCombinations, stateCalcLookup);

            // Try with array element not included in subset
            List<Integer> copyWithoutElement = new ArrayList<>();
            if (!hasMatchOnly) {
                copyWithoutElement.addAll(combination);
            }
            var withoutElement = isThereSubsetSum(arr, arraySize - 1, sum,
                hasMatchOnly, copyWithoutElement, matchingCombinations, stateCalcLookup);

            result = withElementIncluded|| withoutElement;
        }
        stateCalcLookup.put(key, result);
        return result;
    }

    static List<List<Integer>> findSubsets(List<Integer> transactionList, Integer sum) {
        var combination = new ArrayList<Integer>();
        var matchingCombinations = new ArrayList<List<Integer>>();
        // create a map to store solutions to subproblems
        Map<String, Boolean> stateCalcLookup = new HashMap<>();
        isThereSubsetSum(transactionList, transactionList.size(), sum, false, combination, matchingCombinations, stateCalcLookup);
        return matchingCombinations;
        //System.out.println("Recursive Way With Memory\n Matching suggestions found: " + matchingCombinations.size() + "\n Duration (sec): " + Duration.between(start, Instant.now()).toSeconds());
//        if (matchingCombinations.size() <5) {
//            System.out.println(matchingCombinations.toString());
//        }
    }

    static boolean hasMatch(List<Integer> transactionList, Integer sum) {
        var combination = new ArrayList<Integer>();
        var matchingCombinations = new ArrayList<List<Integer>>();
        // create a map to store solutions to subproblems
        Map<String, Boolean> stateCalcLookup = new HashMap<>();
        return isThereSubsetSum(transactionList, transactionList.size(), sum, true, combination, matchingCombinations, stateCalcLookup);
        //System.out.println("Recursive Way\n Has match: " + matchFound + "\n Duration (sec): " + Duration.between(start, Instant.now()).toSeconds());
    }

    static ResultModel getTestResult(List<Integer> transactionList, Integer sum) {
        var start = Instant.now();
        var hasMatch = hasMatch(transactionList, sum);
        var end1 =  Instant.now();
        var hasMatchDuration = Duration.between(start, end1).toMillis();
        var matches = findSubsets(transactionList, sum);
        return new ResultModel("recursive wit memo", transactionList.size(), sum, hasMatch, hasMatchDuration, matches.size(), Duration.between(end1, Instant.now()).toMillis());
    }

}
