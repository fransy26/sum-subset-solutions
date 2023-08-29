import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class DynamicWay {
    // References:
    // https://tutorialhorizon.com/algorithms/dynamic-programming-subset-sum-problem/
    // https://www.pepcoding.com/resources/data-structures-and-algorithms-in-java-levelup/dynamic-programming/print_all_paths_with_target_sum_subset/topic

    // Returns true if there exists a subsequence of array `A` with the given sum
    private static boolean[][] subsetSum(List<Integer> array, Integer sum)
    {
        int array_size = array.size();

        // Initialise a matrix in which:
        // - columns values are integer values from 0 to SUM
        // i.e if sum = 3, we create 4 columns with values: { 0, 1, 2, 3, 4}
        // - rows values are the integer values from the input array start with 0
        // i.e if array = { 2, 1 }, we create 3 rows with values { 0, 2, 1}
        //
        // `calculationMatrix[i][j]` stores true if subset with sum `j` can be attained
        // using items up to first `i` items

        // initialise calculation Matrix as
        boolean[][] calculationMatrix = new boolean[array_size + 1][sum + 1];

        // we initialise the first column for all rows:
        // Target sum 0 can be attained using item in row 0 (=0)
        for (int i = 0; i <= array_size; i++) {
            calculationMatrix[i][0] = true;
        }
        // we initialise the first row for all other columns:
        // Target sum >0 cannot be attained using item in row 0 (=0)
        for (int j = 1; j <= sum; j++) {
            calculationMatrix[0][j] = false;
        }

        // For each other row, we do the calculation whether the column value (target sum),
        // can be attained items up to first `i` items
        for (int i = 1; i <= array_size; i++)
        {
            // consider all sum from 1 to sum
            for (int j = 1; j <= sum; j++)
            {
                // for our example:
                // if i=1 and j=1, we need to check whether target sum 1 can be attained with items {0, 2} => false
                // if i=1 and j=2, we need to check whether target sum 2 can be attained with items {0, 2} => true
                // etc

                // if we could attain the target sum with the previous items of rows above, then we can surely still attain
                // it with current row
                // i.e if we can attain target sum 2 with items {0, 2}, then we can also attain it with items { 0, 2,1}

                //first copy the data from the top
                calculationMatrix[i][j] = calculationMatrix[i-1][j];

                //If calculationMatrix[i][j]==false check if can be made with or without the i'th element
                if(!calculationMatrix[i][j] && j>=array.get(i-1)) {
                    int value = array.get(i - 1);
                    var withElement = calculationMatrix[i - 1][j - value];
                    var withoutElement = calculationMatrix[i - 1][j];
                    calculationMatrix[i][j] = withElement || withoutElement;
                }
            }
        }
        return calculationMatrix;
    }

    public static class PathToSumModel {
        int array_index;
        int targetSum;
        List<Integer> elementsForSum;

        public PathToSumModel(int i, int j, List<Integer> psf) {
            this.array_index = i;
            this.targetSum = j;
            this.elementsForSum = psf;
        }
    }

    private static List<List<Integer>> findSubsets(boolean[][] calculationMatrix, List<Integer> transactionList, Integer sum) {

        List<PathToSumModel> pathToSumsModelListToSolve = new ArrayList<>();
        List<PathToSumModel> pathToFinalSumModelListSolved = new ArrayList<>();

        // initialise list of paths to solve with total sum as target
        pathToSumsModelListToSolve.add(new PathToSumModel(transactionList.size(), sum, new ArrayList<Integer>()));


        while (pathToSumsModelListToSolve.size() > 0) {
            // Get the last path added to solve
            var lastElement = pathToSumsModelListToSolve.size()-1;
            PathToSumModel pathToSumModel = pathToSumsModelListToSolve.remove(lastElement);

            if (pathToSumModel.array_index == 0 || pathToSumModel.targetSum == 0) {
                // path has been resolved
                pathToFinalSumModelListSolved.add(pathToSumModel);
            } else {
                boolean exclude_element = calculationMatrix[pathToSumModel.array_index - 1][pathToSumModel.targetSum];
                var value = transactionList.get(pathToSumModel.array_index - 1);

                boolean include_element = false;
                if (pathToSumModel.targetSum - value >= 0) {
                    include_element = calculationMatrix[pathToSumModel.array_index - 1][pathToSumModel.targetSum - transactionList.get(pathToSumModel.array_index - 1)];
                }
                // note: include and exclude conditions could both be true
                if (include_element) {
                    var elementsForSum = new ArrayList<Integer>();
                    elementsForSum.addAll(pathToSumModel.elementsForSum);
                    elementsForSum.add(value);
                    pathToSumsModelListToSolve.add(new PathToSumModel(pathToSumModel.array_index - 1, pathToSumModel.targetSum -value, elementsForSum));
                }
                if (exclude_element) {
                    var elementsForSum = new ArrayList<Integer>();
                    elementsForSum.addAll(pathToSumModel.elementsForSum);
                    pathToSumsModelListToSolve.add(new PathToSumModel(pathToSumModel.array_index - 1, pathToSumModel.targetSum, elementsForSum));
                }
            }
        }

        return pathToFinalSumModelListSolved.stream().map(path -> path.elementsForSum).toList();
    }

    private static boolean getMatchFound(boolean[][] calculationMatrix) {
        var dimension1 = calculationMatrix.length;
        var dimension2 = calculationMatrix[0].length;
        return calculationMatrix[dimension1-1][dimension2-1];
    }

    static List<List<Integer>> findSubsets(List<Integer> transactionList, Integer sum) {
        var start = Instant.now();
        var calculationMatrix = subsetSum(transactionList, sum);
        var matchingCombinations = findSubsets(calculationMatrix, transactionList, sum);
        return matchingCombinations;
        //  if (matchingCombinations.size() <5) {
//            System.out.println(matchingCombinations);
//        }
    }

    static boolean hasMatch(List<Integer> transactionList, Integer sum) {
        var calculationMatrix = subsetSum(transactionList, sum);
        return getMatchFound(calculationMatrix);
    }

    static ResultModel getTestResult(List<Integer> transactionList, Integer sum) {
        var start = Instant.now();
        var hasMatch = hasMatch(transactionList, sum);
        var end1 =  Instant.now();
        var hasMatchDuration = Duration.between(start, end1).toSeconds();
        var matches = findSubsets(transactionList, sum);
        return new ResultModel("dynamic", transactionList.size(), sum, hasMatch, hasMatchDuration, matches.size(), Duration.between(end1, Instant.now()).toSeconds());
    }

}
