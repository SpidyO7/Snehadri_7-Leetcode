from collections import defaultdict

class Solution(object):
    def minScore(self, n, roads):
        graph = defaultdict(list)

        for u, v, w in roads:
            graph[u].append((v, w))
            graph[v].append((u, w))

        visited = set()
        ans = [float("inf")]   # use a list so it can be modified

        def dfs(node):
            visited.add(node)

            for nei, wt in graph[node]:
                if wt < ans[0]:
                    ans[0] = wt
                if nei not in visited:
                    dfs(nei)

        dfs(1)
        return ans[0]