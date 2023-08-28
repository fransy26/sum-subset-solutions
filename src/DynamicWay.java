import java.util.List;

public class DynamicWay {

    // Returns true if there exists a subsequence of array `A` with the given sum
    public static boolean subsetSum(List<Integer> array, Integer sum)
    {
        int n = array.size();

        // `T[i][j]` stores true if subset with sum `j` can be attained
        // using items up to first `i` items
        boolean[][] T = new boolean[n + 1][sum + 1];

        // if the sum is zero
        for (int i = 0; i <= n; i++) {
            T[i][0] = true;
        }

        // do for i'th item
        for (int i = 1; i <= n; i++)
        {
            // consider all sum from 1 to sum
            for (int j = 1; j <= sum; j++)
            {
                // don't include the i'th element if `j-A[i-1]` is negative
                if (array.get(i - 1 )> j) {
                    T[i][j] = T[i - 1][j];
                }
                else {
                    // find the subset with sum `j` by excluding or including
                    // the i'th item
                    T[i][j] = T[i - 1][j] || T[i - 1][j -array.get(i - 1)];
                }
            }
        }

        // return maximum value
        return T[n][sum];
    }

        public static void main(String[] args)
        {
            // Input: a set of items and a sum
            List<Integer> A = List.of(3, 4, 5, 2);
            int k = 9;

            if (subsetSum(A, k)) {
                System.out.println("Subsequence with the given sum exists");
            }
            else {
                System.out.println("Subsequence with the given sum does not exist");
            }
        }
}
