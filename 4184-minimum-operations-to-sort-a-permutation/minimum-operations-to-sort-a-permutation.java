class Solution {
    public int minOperations(int[] nums) {
        int n = nums.length;
        int zero = 0;

        for (int i = 0; i < n; i++) {
            if (nums[i] == 0) {
                zero = i;
                break;
            }
        }

        boolean inc = true;
        boolean dec = true;

        for (int i = 0; i < n; i++) {
            if (nums[(zero + i) % n] != i) {
                inc = false;
            }

            if (nums[(zero - i + n) % n] != i) {
                dec = false;
            }
        }

        int ans = Integer.MAX_VALUE;

        if (inc) {
            ans = Math.min(zero, nums[0] + 2);
        }

        if (dec) {
            ans = Math.min(ans, Math.min(zero + 2, n - zero));
        }

        return ans == Integer.MAX_VALUE ? -1 : ans;
    }
}