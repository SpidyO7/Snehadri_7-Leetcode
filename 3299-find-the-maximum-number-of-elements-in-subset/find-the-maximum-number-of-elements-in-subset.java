import java.util.*;

class Solution {
    public int maximumLength(int[] nums) {
        Map<Long, Integer> map = new HashMap<>();

        for (int num : nums) {
            long x = num;
            map.put(x, map.getOrDefault(x, 0) + 1);
        }

        int ans = 1;

        int ones = map.getOrDefault(1L, 0);
        ans = Math.max(ans, ones % 2 == 0 ? ones - 1 : ones);

        for (long x : map.keySet()) {
            if (x == 1) {
                continue;
            }

            long curr = x;
            int pairs = 0;

            while (map.getOrDefault(curr, 0) >= 2) {
                pairs++;

                if (curr > 1000000000L / curr) {
                    curr = -1;
                    break;
                }

                curr = curr * curr;
            }

            int len = pairs * 2;

            if (curr != -1 && map.getOrDefault(curr, 0) >= 1) {
                len++;
            } else {
                len--;
            }

            ans = Math.max(ans, len);
        }

        return ans;
    }
}