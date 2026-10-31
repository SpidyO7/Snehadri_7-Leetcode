
class Solution {
    public int uniquePaths(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int mod = 1000000007;

        int[][] right = new int[m][n];
        int[][] down = new int[m][n];

        right[0][0] = 1;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                if (grid[i][j] == 1) {

                    if (i + 1 < m) {
                        down[i + 1][j] = (int)(
                            (down[i + 1][j] + (long) right[i][j]) % mod
                        );
                    }

                    if (j + 1 < n) {
                        right[i][j + 1] = (int)(
                            (right[i][j + 1] + (long) down[i][j]) % mod
                        );
                    }

                } else {

                    long ways = ((long) right[i][j] + down[i][j]) % mod;

                    if (j + 1 < n) {
                        right[i][j + 1] = (int)(
                            (right[i][j + 1] + ways) % mod
                        );
                    }

                    if (i + 1 < m) {
                        down[i + 1][j] = (int)(
                            (down[i + 1][j] + ways) % mod
                        );
                    }
                }
            }
        }

        return (int)(((long) right[m - 1][n - 1]
                    + down[m - 1][n - 1]) % mod);
    }
}
