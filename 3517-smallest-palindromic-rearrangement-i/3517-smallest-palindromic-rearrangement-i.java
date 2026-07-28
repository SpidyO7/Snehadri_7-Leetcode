class Solution {
    public String smallestPalindrome(String s) {
        int[] cnt = new int[26];

        for (char c : s.toCharArray()) {
            cnt[c - 'a']++;
        }

        StringBuilder left = new StringBuilder();
        String mid = "";

        for (int i = 0; i < 26; i++) {
            while (cnt[i] >= 2) {
                left.append((char) ('a' + i));
                cnt[i] -= 2;
            }
            if (cnt[i] == 1) {
                mid = String.valueOf((char) ('a' + i));
            }
        }

        String right = new StringBuilder(left).reverse().toString();
        return left.toString() + mid + right;
    }
}