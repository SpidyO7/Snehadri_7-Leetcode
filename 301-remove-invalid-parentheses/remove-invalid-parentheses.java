import java.util.*;

class Solution {
    public List<String> removeInvalidParentheses(String s) {
        int left = 0;
        int right = 0;

        for (char c : s.toCharArray()) {
            if (c == '(') {
                left++;
            } else if (c == ')') {
                if (left > 0)
                    left--;
                else
                    right++;
            }
        }

        Set<String> set = new HashSet<>();
        solve(s, 0, left, right, 0, "", set);

        return new ArrayList<>(set);
    }

    void solve(String s, int index, int left, int right, int open,
               String cur, Set<String> set) {

        if (index == s.length()) {
            if (left == 0 && right == 0 && open == 0)
                set.add(cur);
            return;
        }

        char c = s.charAt(index);

        if (c == '(' && left > 0) {
            solve(s, index + 1, left - 1, right, open, cur, set);
        }

        if (c == ')' && right > 0) {
            solve(s, index + 1, left, right - 1, open, cur, set);
        }

        if (c == '(') {
            solve(s, index + 1, left, right, open + 1, cur + c, set);
        } else if (c == ')') {
            if (open > 0)
                solve(s, index + 1, left, right, open - 1, cur + c, set);
        } else {
            solve(s, index + 1, left, right, open, cur + c, set);
        }
    }
}