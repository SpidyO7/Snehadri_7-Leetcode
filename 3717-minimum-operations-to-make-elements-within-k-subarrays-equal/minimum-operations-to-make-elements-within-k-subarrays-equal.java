import java.util.*;

class Solution {
    class Fenwick {
        int n;
        long[] tree;

        Fenwick(int n) {
            this.n = n;
            tree = new long[n + 1];
        }

        void add(int i, long val) {
            while (i <= n) {
                tree[i] += val;
                i += i & -i;
            }
        }

        long sum(int i) {
            long ans = 0;
            while (i > 0) {
                ans += tree[i];
                i -= i & -i;
            }
            return ans;
        }

        int find(int k) {
            int pos = 0;
            int bit = Integer.highestOneBit(n);

            while (bit > 0) {
                int next = pos + bit;

                if (next <= n && tree[next] < k) {
                    pos = next;
                    k -= (int) tree[next];
                }

                bit >>= 1;
            }

            return pos + 1;
        }
    }

    public long minOperations(int[] nums, int x, int k) {
        int n = nums.length;

        int[] sorted = nums.clone();
        Arrays.sort(sorted);

        int m = 0;

        for (int v : sorted) {
            if (m == 0 || sorted[m - 1] != v) {
                sorted[m++] = v;
            }
        }

        Fenwick count = new Fenwick(m);
        Fenwick sum = new Fenwick(m);

        long[] cost = new long[n - x + 1];
        long total = 0;

        for (int i = 0; i < x; i++) {
            int p = Arrays.binarySearch(sorted, 0, m, nums[i]) + 1;
            count.add(p, 1);
            sum.add(p, nums[i]);
            total += nums[i];
        }

        for (int i = 0; i <= n - x; i++) {
            int medianPos = count.find((x + 1) / 2);
            long median = sorted[medianPos - 1];

            long leftCount = count.sum(medianPos);
            long leftSum = sum.sum(medianPos);

            cost[i] = median * leftCount - leftSum
                    + (total - leftSum)
                    - median * (x - leftCount);

            if (i < n - x) {
                int oldPos = Arrays.binarySearch(sorted, 0, m, nums[i]) + 1;
                int newPos = Arrays.binarySearch(sorted, 0, m, nums[i + x]) + 1;

                count.add(oldPos, -1);
                sum.add(oldPos, -nums[i]);

                count.add(newPos, 1);
                sum.add(newPos, nums[i + x]);

                total += nums[i + x] - nums[i];
            }
        }

        long INF = Long.MAX_VALUE / 4;

        long[] prev = new long[n + 1];

        for (int j = 1; j <= k; j++) {
            long[] curr = new long[n + 1];
            Arrays.fill(curr, INF);

            for (int i = 1; i <= n; i++) {
                curr[i] = curr[i - 1];

                if (i >= x && prev[i - x] != INF) {
                    curr[i] = Math.min(curr[i],
                            prev[i - x] + cost[i - x]);
                }
            }

            prev = curr;
        }

        return prev[n];
    }
}