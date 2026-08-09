class Solution {
    int[][] dp;
    int[] sum;
    int n;

    public int stoneGameII(int[] piles) {
        n = piles.length;
        dp = new int[n][n + 1];
        sum = new int[n + 1];

        for (int i = n - 1; i >= 0; i--)
            sum[i] = sum[i + 1] + piles[i];

        return dfs(0, 1);
    }

    int dfs(int i, int m) {
        if (i >= n) return 0;
        if (dp[i][m] != 0) return dp[i][m];

        int best = 0;

        for (int x = 1; x <= 2 * m && i + x <= n; x++) {
            best = Math.max(best, sum[i] - dfs(i + x, Math.max(m, x)));
        }

        return dp[i][m] = best;
    }
}
