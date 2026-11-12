
class Solution {
    int[] len, pref, suff, best;
    char[] left, right;

    void build(int node, int start, int end, String s) {
        if (start == end) {
            len[node] = pref[node] = suff[node] = best[node] = 1;
            left[node] = right[node] = s.charAt(start);
            return;
        }

        int mid = (start + end) / 2;

        build(node * 2, start, mid, s);
        build(node * 2 + 1, mid + 1, end, s);

        merge(node);
    }

    void merge(int node) {
        int a = node * 2;
        int b = node * 2 + 1;

        len[node] = len[a] + len[b];
        left[node] = left[a];
        right[node] = right[b];

        pref[node] = pref[a];
        suff[node] = suff[b];
        best[node] = Math.max(best[a], best[b]);

        if (right[a] == left[b]) {
            best[node] = Math.max(best[node], suff[a] + pref[b]);

            if (pref[a] == len[a]) {
                pref[node] = len[a] + pref[b];
            }

            if (suff[b] == len[b]) {
                suff[node] = len[b] + suff[a];
            }
        }
    }

    void update(int node, int start, int end, int index, char c) {
        if (start == end) {
            left[node] = right[node] = c;
            return;
        }

        int mid = (start + end) / 2;

        if (index <= mid) {
            update(node * 2, start, mid, index, c);
        } else {
            update(node * 2 + 1, mid + 1, end, index, c);
        }

        merge(node);
    }

    public int[] longestRepeating(String s, String queryCharacters, int[] queryIndices) {
        int n = s.length();
        int k = queryIndices.length;

        len = new int[4 * n];
        pref = new int[4 * n];
        suff = new int[4 * n];
        best = new int[4 * n];
        left = new char[4 * n];
        right = new char[4 * n];

        build(1, 0, n - 1, s);

        int[] ans = new int[k];

        for (int i = 0; i < k; i++) {
            update(1, 0, n - 1, queryIndices[i], queryCharacters.charAt(i));
            ans[i] = best[1];
        }

        return ans;
    }
}
