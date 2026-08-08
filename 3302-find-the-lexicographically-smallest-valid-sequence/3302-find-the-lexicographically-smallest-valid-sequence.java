import java.util.*;

class Solution {
    public int[] validSequence(String word1, String word2) {
        int m = word2.length();
        int[] last = new int[m];
        Arrays.fill(last, -1);

        int i = word1.length() - 1;
        int j = m - 1;

        while (i >= 0 && j >= 0) {
            if (word1.charAt(i) == word2.charAt(j)) {
                last[j] = i;
                j--;
            }
            i--;
        }

        int[] ans = new int[m];
        boolean used = false;
        j = 0;

        for (i = 0; i < word1.length() && j < m; i++) {
            if (word1.charAt(i) == word2.charAt(j)) {
                ans[j++] = i;
            } 
            else if (!used && (j == m - 1 || i < last[j + 1])) {
                ans[j++] = i;
                used = true;
            }
        }

        return j == m ? ans : new int[0];
    }
}