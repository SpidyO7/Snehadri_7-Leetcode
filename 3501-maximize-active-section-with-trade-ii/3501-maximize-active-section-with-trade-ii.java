import java.util.*;

class Solution {
    public List<Integer> maxActiveSectionsAfterTrade(String s, int[][] queries) {
        int n = s.length();
        int totalOnes = 0;
        for (int i = 0; i < n; i++) if (s.charAt(i) == '1') totalOnes++;

        List<int[]> runs = new ArrayList<>(); // {isOne, start, end}
        int i = 0;
        while (i < n) {
            int j = i;
            char c = s.charAt(i);
            while (j < n && s.charAt(j) == c) j++;
            runs.add(new int[]{c == '1' ? 1 : 0, i, j - 1});
            i = j;
        }

        List<int[]> triples = new ArrayList<>(); // {a,b,z1s,z2e,val}
        for (int idx = 0; idx < runs.size(); idx++) {
            int[] run = runs.get(idx);
            if (run[0] == 1 && run[1] > 0 && run[2] < n - 1) {
                int a = run[1], b = run[2];
                int z1s = runs.get(idx - 1)[1];
                int z2e = runs.get(idx + 1)[2];
                int val = (a - z1s) + (z2e - b);
                triples.add(new int[]{a, b, z1s, z2e, val});
            }
        }

        int m = triples.size();
        int[] A = new int[m], B = new int[m], Z1S = new int[m], Z2E = new int[m], VAL = new int[m];
        for (int k = 0; k < m; k++) {
            int[] t = triples.get(k);
            A[k] = t[0]; B[k] = t[1]; Z1S[k] = t[2]; Z2E[k] = t[3]; VAL[k] = t[4];
        }

        int LOG = 1;
        while ((1 << LOG) <= Math.max(m, 1)) LOG++;
        int[][] sparse = new int[LOG][Math.max(m, 1)];
        if (m > 0) {
            sparse[0] = VAL.clone();
            for (int k = 1; k < LOG; k++) {
                int half = 1 << (k - 1);
                for (int idx2 = 0; idx2 + (1 << k) <= m; idx2++)
                    sparse[k][idx2] = Math.max(sparse[k - 1][idx2], sparse[k - 1][idx2 + half]);
            }
        }
        int[] log2 = new int[m + 1];
        for (int k = 2; k <= m; k++) log2[k] = log2[k / 2] + 1;

        List<Integer> answer = new ArrayList<>();
        for (int[] qr : queries) {
            int l = qr[0], r = qr[1];
            int maxGain = 0;

            if (m > 0) {
                int lo = upperBound(A, l);
                int hi = lowerBound(B, r) - 1;

                if (lo <= hi) {
                    if (lo == hi) {
                        int val = (A[lo] - Math.max(l, Z1S[lo])) + (Math.min(r, Z2E[lo]) - B[lo]);
                        maxGain = Math.max(maxGain, val);
                    } else {
                        int valLo = (A[lo] - Math.max(l, Z1S[lo])) + (Z2E[lo] - B[lo]);
                        int valHi = (A[hi] - Z1S[hi]) + (Math.min(r, Z2E[hi]) - B[hi]);
                        maxGain = Math.max(maxGain, Math.max(valLo, valHi));
                        int gl = lo + 1, gh = hi - 1;
                        if (gl <= gh) {
                            int len = gh - gl + 1;
                            int kk = log2[len];
                            int genericMax = Math.max(sparse[kk][gl], sparse[kk][gh - (1 << kk) + 1]);
                            maxGain = Math.max(maxGain, genericMax);
                        }
                    }
                }
            }
            answer.add(totalOnes + maxGain);
        }
        return answer;
    }

    private int upperBound(int[] arr, int key) {
        int lo = 0, hi = arr.length;
        while (lo < hi) {
            int mid = (lo + hi) >>> 1;
            if (arr[mid] <= key) lo = mid + 1; else hi = mid;
        }
        return lo;
    }

    private int lowerBound(int[] arr, int key) {
        int lo = 0, hi = arr.length;
        while (lo < hi) {
            int mid = (lo + hi) >>> 1;
            if (arr[mid] < key) lo = mid + 1; else hi = mid;
        }
        return lo;
    }
}
