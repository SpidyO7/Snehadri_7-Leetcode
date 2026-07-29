class Solution {
    long LIMIT = 1000001;

    public String smallestPalindrome(String s, int k) {
        int[] cnt = new int[26];
        for (char c : s.toCharArray()) cnt[c - 'a']++;

        String mid = "";
        int[] half = new int[26];
        int len = 0;

        for (int i = 0; i < 26; i++) {
            if ((cnt[i] & 1) == 1) mid = "" + (char) ('a' + i);
            half[i] = cnt[i] / 2;
            len += half[i];
        }

        if (countWays(half, len) < k) return "";

        StringBuilder left = new StringBuilder();

        for (int pos = 0; pos < len; pos++) {
            for (int c = 0; c < 26; c++) {
                if (half[c] == 0) continue;

                half[c]--;
                long ways = countWays(half, len - pos - 1);

                if (ways >= k) {
                    left.append((char) ('a' + c));
                    break;
                } else {
                    k -= ways;
                    half[c]++;
                }
            }
        }

        StringBuilder ans = new StringBuilder(left);
        ans.append(mid);
        ans.append(new StringBuilder(left).reverse());

        return ans.toString();
    }

    private long countWays(int[] half, int total) {
        long res = 1;
        int rem = total;

        for (int x : half) {
            if (x == 0) continue;
            res *= comb(rem, x);
            if (res > LIMIT) return LIMIT;
            rem -= x;
        }

        return Math.min(res, LIMIT);
    }

    private long comb(int n, int r) {
        if (r > n - r) r = n - r;
        long ans = 1;

        for (int i = 1; i <= r; i++) {
            ans = ans * (n - r + i) / i;
            if (ans > LIMIT) return LIMIT;
        }

        return ans;
    }
}