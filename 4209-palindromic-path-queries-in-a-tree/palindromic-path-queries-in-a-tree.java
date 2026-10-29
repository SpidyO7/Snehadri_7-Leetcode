import java.util.*;

class Solution {
    public List<Boolean> palindromePath(int n, int[][] edges, String s, String[] queries) {
        List<ArrayList<Integer>> graph = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        for (int[] edge : edges) {
            graph.get(edge[0]).add(edge[1]);
            graph.get(edge[1]).add(edge[0]);
        }

        int[] parent = new int[n];
        int[] depth = new int[n];
        int[] tin = new int[n];
        int[] tout = new int[n];
        int[] next = new int[n];
        int[] stack = new int[n];

        int top = 0;
        int timer = 0;
        stack[0] = 0;
        parent[0] = 0;
        tin[0] = ++timer;

        while (top >= 0) {
            int u = stack[top];

            if (next[u] < graph.get(u).size()) {
                int v = graph.get(u).get(next[u]++);

                if (v == parent[u]) {
                    continue;
                }

                parent[v] = u;
                depth[v] = depth[u] + 1;
                tin[v] = ++timer;
                stack[++top] = v;
            } else {
                tout[u] = ++timer;
                top--;
            }
        }

        int log = 1;
        while ((1 << log) <= n) {
            log++;
        }

        int[][] up = new int[log][n];

        for (int i = 0; i < n; i++) {
            up[0][i] = parent[i];
        }

        for (int k = 1; k < log; k++) {
            for (int i = 0; i < n; i++) {
                up[k][i] = up[k - 1][up[k - 1][i]];
            }
        }

        char[] chars = s.toCharArray();
        Fenwick fenwick = new Fenwick(2 * n + 2);

        for (int i = 0; i < n; i++) {
            int bit = 1 << (chars[i] - 'a');
            fenwick.update(tin[i], bit);
            fenwick.update(tout[i], bit);
        }

        List<Boolean> ans = new ArrayList<>();

        for (String query : queries) {
            String[] parts = query.trim().split("\\s+");

            if (parts[0].equals("update")) {
                int u = Integer.parseInt(parts[1]);
                char c = parts[2].charAt(0);

                int oldBit = 1 << (chars[u] - 'a');
                int newBit = 1 << (c - 'a');
                int change = oldBit ^ newBit;

                fenwick.update(tin[u], change);
                fenwick.update(tout[u], change);
                chars[u] = c;
            } else {
                int u = Integer.parseInt(parts[1]);
                int v = Integer.parseInt(parts[2]);

                int w = lca(u, v, depth, up);

                int mask = fenwick.query(tin[u])
                        ^ fenwick.query(tin[v])
                        ^ (1 << (chars[w] - 'a'));

                ans.add((mask & (mask - 1)) == 0);
            }
        }

        return ans;
    }

    private int lca(int u, int v, int[] depth, int[][] up) {
        if (depth[u] < depth[v]) {
            int temp = u;
            u = v;
            v = temp;
        }

        int diff = depth[u] - depth[v];

        for (int k = 0; k < up.length; k++) {
            if ((diff & (1 << k)) != 0) {
                u = up[k][u];
            }
        }

        if (u == v) {
            return u;
        }

        for (int k = up.length - 1; k >= 0; k--) {
            if (up[k][u] != up[k][v]) {
                u = up[k][u];
                v = up[k][v];
            }
        }

        return up[0][u];
    }

    static class Fenwick {
        int[] tree;

        Fenwick(int n) {
            tree = new int[n + 1];
        }

        void update(int i, int value) {
            while (i < tree.length) {
                tree[i] ^= value;
                i += i & -i;
            }
        }

        int query(int i) {
            int result = 0;

            while (i > 0) {
                result ^= tree[i];
                i -= i & -i;
            }

            return result;
        }
    }
}