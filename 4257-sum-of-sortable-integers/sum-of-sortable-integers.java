import java.util.Arrays;

class Solution {
    public int sortableIntegers(int[] nums) {
        int n = nums.length;
        int[] sorted = nums.clone();
        Arrays.sort(sorted);

        int ans = 0;

        for (int k = 1; k <= n; k++) {
            if (n % k != 0) {
                continue;
            }

            boolean ok = true;

            for (int i = 0; i < n; i += k) {
                int start = 0;
                int count = 0;

                for (int j = 0; j < k - 1; j++) {
                    if (nums[i + j] > nums[i + j + 1]) {
                        count++;
                        start = j + 1;
                    }
                }

                if (count > 1) {
                    ok = false;
                    break;
                }

                for (int j = 0; j < k; j++) {
                    if (nums[i + (start + j) % k] != sorted[i + j]) {
                        ok = false;
                        break;
                    }
                }

                if (!ok) {
                    break;
                }
            }

            if (ok) {
                ans += k;
            }
        }

        return ans;
    }
}