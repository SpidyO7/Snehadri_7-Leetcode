class Solution {
    public int minSumOfLengths(int[] arr, int target) {
        int n = arr.length;
        int[] dp = new int[n];
        int left = 0;
        int sum = 0;
        int best = Integer.MAX_VALUE;
        int ans = Integer.MAX_VALUE;

        for (int right = 0; right < n; right++) {
            sum += arr[right];

            while (sum > target) {
                sum -= arr[left];
                left++;
            }

            if (sum == target) {
                int len = right - left + 1;

                if (left > 0 && dp[left - 1] != 0) {
                    ans = Math.min(ans, len + dp[left - 1]);
                }

                best = Math.min(best, len);
            }

            if (best != Integer.MAX_VALUE) {
                dp[right] = best;
            }
        }

        return ans == Integer.MAX_VALUE ? -1 : ans;
    }
}