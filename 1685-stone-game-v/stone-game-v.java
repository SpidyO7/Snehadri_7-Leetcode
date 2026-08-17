class Solution {
    public int stoneGameV(int[] stoneValue) {
        int n = stoneValue.length;
        // prefix sums: prefix[i] = sum of stoneValue[0..i-1]
        int[] prefix = new int[n + 1];
        for (int i = 0; i < n; i++) {
            prefix[i + 1] = prefix[i] + stoneValue[i];
        }

        // dp[i][j] = max score Alice can get from stoneValue[i..j]
        int[][] dp = new int[n][n];

        // len = length of interval
        for (int len = 2; len <= n; len++) {
            for (int i = 0; i + len - 1 < n; i++) {
                int j = i + len - 1;
                int best = 0;

                // try all split points k between i and j
                for (int k = i; k < j; k++) {
                    int leftSum = prefix[k + 1] - prefix[i];       // sum(i..k)
                    int rightSum = prefix[j + 1] - prefix[k + 1];  // sum(k+1..j)

                    if (leftSum < rightSum) {
                        // Bob throws away right row, game continues on left
                        best = Math.max(best, leftSum + dp[i][k]);
                    } else if (rightSum < leftSum) {
                        // Bob throws away left row, game continues on right
                        best = Math.max(best, rightSum + dp[k + 1][j]);
                    } else {
                        // equal sums: Alice chooses which row to keep
                        best = Math.max(best, leftSum + Math.max(dp[i][k], dp[k + 1][j]));
                    }
                }

                dp[i][j] = best;
            }
        }

        return dp[0][n - 1];
    }
}