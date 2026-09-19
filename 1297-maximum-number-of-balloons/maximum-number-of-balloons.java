class Solution {
    public int maxNumberOfBalloons(String text) {
        int[] count = new int[26];

        for (char c : text.toCharArray()) {
            count[c - 'a']++;
        }

        int ans = count['b' - 'a'];
        ans = Math.min(ans, count['a' - 'a']);
        ans = Math.min(ans, count['l' - 'a'] / 2);
        ans = Math.min(ans, count['o' - 'a'] / 2);
        ans = Math.min(ans, count['n' - 'a']);

        return ans;
    }
}