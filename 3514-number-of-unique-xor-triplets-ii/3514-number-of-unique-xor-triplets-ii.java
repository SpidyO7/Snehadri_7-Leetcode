class Solution {
    public int uniqueXorTriplets(int[] nums) {
        final int MAX = 2048;

        boolean[][] dp = new boolean[4][MAX];
        dp[0][0] = true;

        boolean[] seen = new boolean[MAX];

        for (int v : nums) {
            seen[v] = true;
            for (int cnt = 2; cnt >= 0; cnt--) {
                for (int x = 0; x < MAX; x++) {
                    if (dp[cnt][x]) {
                        dp[cnt + 1][x ^ v] = true;
                    }
                }
            }
        }

        int ans = 0;
        for (int x = 0; x < MAX; x++) {
            if (seen[x] || dp[3][x]) {
                ans++;
            }
        }

        return ans;
    }
}