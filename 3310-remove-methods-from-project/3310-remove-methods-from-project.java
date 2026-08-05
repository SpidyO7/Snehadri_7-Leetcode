import java.util.*;

class Solution {
    List<Integer>[] g;
    boolean[] vis;

    void dfs(int u) {
        vis[u] = true;
        for (int v : g[u])
            if (!vis[v]) dfs(v);
    }

    public List<Integer> remainingMethods(int n, int k, int[][] invocations) {
        g = new ArrayList[n];
        for (int i = 0; i < n; i++) g[i] = new ArrayList<>();

        for (int[] e : invocations)
            g[e[0]].add(e[1]);

        vis = new boolean[n];
        dfs(k);

        for (int[] e : invocations)
            if (!vis[e[0]] && vis[e[1]]) {
                List<Integer> ans = new ArrayList<>();
                for (int i = 0; i < n; i++) ans.add(i);
                return ans;
            }

        List<Integer> ans = new ArrayList<>();
        for (int i = 0; i < n; i++)
            if (!vis[i]) ans.add(i);

        return ans;
    }
}