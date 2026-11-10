class Solution {
    public int climbStairs(int n, int[] costs) {
        long[] dp = new long[n + 1];

        for (int i = 1; i <= n; i++) {
            dp[i] = Long.MAX_VALUE;

            for (int j = 1; j <= 3; j++) {
                if (i - j >= 0) {
                    long cost = dp[i - j] + costs[i - 1] + j * j;
                    dp[i] = Math.min(dp[i], cost);
                }
            }
        }

        return (int) dp[n];
    }
}