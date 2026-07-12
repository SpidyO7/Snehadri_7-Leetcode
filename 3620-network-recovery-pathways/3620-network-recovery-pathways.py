from collections import deque

class Solution:
    def findMaxPathScore(self, edges, online, k):
        n = len(online)

        graph = [[] for _ in range(n)]
        indegree = [0] * n
        costs = []

        for u, v, w in edges:
            graph[u].append((v, w))
            indegree[v] += 1
            costs.append(w)

        q = deque()
        for i in range(n):
            if indegree[i] == 0:
                q.append(i)

        order = []
        while q:
            node = q.popleft()
            order.append(node)

            for nxt, w in graph[node]:
                indegree[nxt] -= 1
                if indegree[nxt] == 0:
                    q.append(nxt)

        def check(limit):
            INF = 10**18
            dist = [INF] * n
            dist[0] = 0

            for node in order:
                if dist[node] == INF:
                    continue

                if node != 0 and node != n - 1 and not online[node]:
                    continue

                for nxt, w in graph[node]:
                    if w < limit:
                        continue

                    if nxt != n - 1 and not online[nxt]:
                        continue

                    if dist[node] + w < dist[nxt]:
                        dist[nxt] = dist[node] + w

            return dist[n - 1] <= k

        if not costs:
            return -1

        left = 0
        right = max(costs)
        ans = -1

        while left <= right:
            mid = (left + right) // 2

            if check(mid):
                ans = mid
                left = mid + 1
            else:
                right = mid - 1

        return ans