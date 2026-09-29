class Solution {
    public boolean hasValidPath(char[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        if (grid[0][0] == ')' || grid[m - 1][n - 1] == '(') {
            return false;
        }

        boolean[][][] dp = new boolean[m][n][m + n];

        dp[0][0][1] = true;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < m + n; k++) {
                    if (!dp[i][j][k]) {
                        continue;
                    }

                    if (i + 1 < m) {
                        int next = grid[i + 1][j] == '(' ? k + 1 : k - 1;

                        if (next >= 0) {
                            dp[i + 1][j][next] = true;
                        }
                    }

                    if (j + 1 < n) {
                        int next = grid[i][j + 1] == '(' ? k + 1 : k - 1;

                        if (next >= 0) {
                            dp[i][j + 1][next] = true;
                        }
                    }
                }
            }
        }

        return dp[m - 1][n - 1][0];
    }
}