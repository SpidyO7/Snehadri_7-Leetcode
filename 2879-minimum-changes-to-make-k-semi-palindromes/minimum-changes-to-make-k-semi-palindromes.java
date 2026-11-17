
class Solution {
    public int minimumChanges(String s, int k) {
        int n = s.length();
        int[][] cost = new int[n][n];

        for (int len = 2; len <= n; len++) {
            for (int i = 0; i + len <= n; i++) {
                int ans = Integer.MAX_VALUE;

                for (int d = 1; d < len; d++) {
                    if (len % d != 0) {
                        continue;
                    }

                    int groups = len / d;
                    int changes = 0;

                    for (int j = 0; j < d; j++) {
                        int left = 0;
                        int right = groups - 1;

                        while (left < right) {
                            if (s.charAt(i + j + left * d) !=
                                s.charAt(i + j + right * d)) {
                                changes++;
                            }

                            left++;
                            right--;
                        }
                    }

                    ans = Math.min(ans, changes);
                }

                cost[i][i + len - 1] = ans;
            }
        }

        int[][] dp = new int[n + 1][k + 1];

        for (int i = 0; i <= n; i++) {
            Arrays.fill(dp[i], 1000000);
        }

        dp[0][0] = 0;

        for (int parts = 1; parts <= k; parts++) {
            for (int end = parts * 2; end <= n; end++) {
                for (int start = (parts - 1) * 2; start <= end - 2; start++) {
                    dp[end][parts] = Math.min(
                        dp[end][parts],
                        dp[start][parts - 1] + cost[start][end - 1]
                    );
                }
            }
        }

        return dp[n][k];
    }
}
