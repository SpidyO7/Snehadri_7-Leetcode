import java.util.*;

class Solution {
    static class Interval {
        int l, r, w, index;

        Interval(int l, int r, int w, int index) {
            this.l = l;
            this.r = r;
            this.w = w;
            this.index = index;
        }
    }

    static class State {
        long score;
        int[] ids;

        State(long score, int[] ids) {
            this.score = score;
            this.ids = ids;
        }
    }

    public int[] maximumWeight(List<List<Integer>> intervals) {
        int n = intervals.size();

        Interval[] arr = new Interval[n];

        for (int i = 0; i < n; i++) {
            arr[i] = new Interval(
                intervals.get(i).get(0),
                intervals.get(i).get(1),
                intervals.get(i).get(2),
                i
            );
        }

        Arrays.sort(arr, (a, b) -> {
            if (a.r != b.r) {
                return Integer.compare(a.r, b.r);
            }
            return Integer.compare(a.index, b.index);
        });

        int[] ends = new int[n];

        for (int i = 0; i < n; i++) {
            ends[i] = arr[i].r;
        }

        int[] prev = new int[n];

        for (int i = 0; i < n; i++) {
            int left = 0;
            int right = i - 1;
            int pos = -1;

            while (left <= right) {
                int mid = left + (right - left) / 2;

                if (ends[mid] < arr[i].l) {
                    pos = mid;
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }

            prev[i] = pos + 1;
        }

        State[][] dp = new State[n + 1][5];

        for (int i = 0; i <= n; i++) {
            for (int k = 0; k <= 4; k++) {
                dp[i][k] = new State(0, new int[0]);
            }
        }

        for (int i = 1; i <= n; i++) {
            Interval cur = arr[i - 1];

            for (int k = 1; k <= 4; k++) {
                State skip = dp[i - 1][k];
                State takeBase = dp[prev[i - 1]][k - 1];

                int[] ids = new int[takeBase.ids.length + 1];

                int p = 0;
                boolean added = false;

                for (int id : takeBase.ids) {
                    if (!added && cur.index < id) {
                        ids[p++] = cur.index;
                        added = true;
                    }
                    ids[p++] = id;
                }

                if (!added) {
                    ids[p] = cur.index;
                }

                State take = new State(
                    takeBase.score + cur.w,
                    ids
                );

                if (take.score > skip.score) {
                    dp[i][k] = take;
                } else if (take.score < skip.score) {
                    dp[i][k] = skip;
                } else {
                    if (compare(take.ids, skip.ids) < 0) {
                        dp[i][k] = take;
                    } else {
                        dp[i][k] = skip;
                    }
                }
            }
        }

        return dp[n][4].ids;
    }

    private int compare(int[] a, int[] b) {
        int len = Math.min(a.length, b.length);

        for (int i = 0; i < len; i++) {
            if (a[i] != b[i]) {
                return Integer.compare(a[i], b[i]);
            }
        }

        return Integer.compare(a.length, b.length);
    }
}