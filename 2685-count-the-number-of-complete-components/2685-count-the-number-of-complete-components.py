from collections import deque

class Solution:
    def countCompleteComponents(self, n, edges):
        graph = [[] for _ in range(n)]

        for u, v in edges:
            graph[u].append(v)
            graph[v].append(u)

        visited = [False] * n
        answer = 0

        for i in range(n):
            if visited[i]:
                continue

            queue = deque([i])
            visited[i] = True
            nodes = []
            edge_count = 0

            while queue:
                node = queue.popleft()
                nodes.append(node)
                edge_count += len(graph[node])

                for nei in graph[node]:
                    if not visited[nei]:
                        visited[nei] = True
                        queue.append(nei)

            size = len(nodes)
            edge_count //= 2

            if edge_count == size * (size - 1) // 2:
                answer += 1

        return answer
        