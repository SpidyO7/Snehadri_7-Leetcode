class Solution {
    public String lexGreaterPermutation(String s, String target) {
        int[] count = new int[26];

        for (char c : s.toCharArray()) {
            count[c - 'a']++;
        }

        String best = "";

        for (int i = 0; i < s.length(); i++) {
            int t = target.charAt(i) - 'a';

            for (int c = t + 1; c < 26; c++) {
                if (count[c] > 0) {
                    StringBuilder candidate = new StringBuilder();

                    candidate.append(target.substring(0, i));
                    candidate.append((char) ('a' + c));

                    int[] temp = count.clone();
                    temp[c]--;

                    for (int j = 0; j < 26; j++) {
                        while (temp[j] > 0) {
                            candidate.append((char) ('a' + j));
                            temp[j]--;
                        }
                    }

                    String cur = candidate.toString();

                    if (best.isEmpty() || cur.compareTo(best) < 0) {
                        best = cur;
                    }

                    break;
                }
            }

            if (count[t] == 0) {
                break;
            }

            count[t]--;
        }

        return best;
    }
}