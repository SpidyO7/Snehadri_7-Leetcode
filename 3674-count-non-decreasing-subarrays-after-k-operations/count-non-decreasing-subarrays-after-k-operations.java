
class Solution {
    public long countNonDecreasingSubarrays(int[] nums, int k) {
        int n = nums.length;
        int log = 17;

        int[] next = new int[n];
        int[] stack = new int[n];
        int top = -1;

        for (int i = n - 1; i >= 0; i--) {
            while (top >= 0 && nums[stack[top]] <= nums[i]) {
                top--;
            }

            next[i] = top == -1 ? n : stack[top];
            stack[++top] = i;
        }

        int[][] up = new int[log][n];
        long[][] sum = new long[log][n];

        for (int i = 0; i < n; i++) {
            up[0][i] = next[i] == n ? -1 : next[i];
            sum[0][i] = (long) nums[i] * (next[i] - i);
        }

        for (int j = 1; j < log; j++) {
            for (int i = 0; i < n; i++) {
                int mid = up[j - 1][i];

                if (mid != -1) {
                    up[j][i] = up[j - 1][mid];
                    sum[j][i] = sum[j - 1][i] + sum[j - 1][mid];
                } else {
                    up[j][i] = -1;
                    sum[j][i] = sum[j - 1][i];
                }
            }
        }

        long[] prefix = new long[n + 1];

        for (int i = 0; i < n; i++) {
            prefix[i + 1] = prefix[i] + nums[i];
        }

        long ans = 0;

        for (int l = 0; l < n; l++) {
            int low = l;
            int high = n - 1;
            int best = l - 1;

            while (low <= high) {
                int mid = low + (high - low) / 2;

                int cur = l;
                long maxSum = 0;

                for (int j = log - 1; j >= 0; j--) {
                    int v = up[j][cur];

                    if (v != -1 && v <= mid) {
                        maxSum += sum[j][cur];
                        cur = v;
                    }
                }

                maxSum += (long) nums[cur] * (mid - cur + 1);

                long cost = maxSum - (prefix[mid + 1] - prefix[l]);

                if (cost <= k) {
                    best = mid;
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }

            ans += best - l + 1;
        }

        return ans;
    }
}
