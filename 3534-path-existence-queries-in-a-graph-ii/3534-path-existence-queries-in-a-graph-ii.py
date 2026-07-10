class Solution:
    def pathExistenceQueries(self, n, nums, maxDiff, queries):
        order = sorted(range(n), key=lambda i: nums[i])
        val = [nums[i] for i in order]
        rank = [0] * n
        for pos, i in enumerate(order):
            rank[i] = pos

        # component id: break when gap between consecutive sorted values > maxDiff
        comp = [0] * n
        for pos in range(1, n):
            comp[pos] = comp[pos - 1] + (val[pos] - val[pos - 1] > maxDiff)

        # R[i] = farthest index reachable in one hop from sorted position i
        R = [0] * n
        j = 0
        for i in range(n):
            j = max(j, i)
            while j + 1 < n and val[j + 1] - val[i] <= maxDiff:
                j += 1
            R[i] = j

        # binary lifting table
        LOG = max(1, (n - 1).bit_length()) + 1
        jump = [R[:]]
        for k in range(1, LOG):
            prev = jump[-1]
            jump.append([prev[prev[i]] for i in range(n)])

        def hops(p, q):  # min jumps from p to reach q, p <= q
            cur, steps = p, 0
            for k in range(LOG - 1, -1, -1):
                if jump[k][cur] < q:
                    cur = jump[k][cur]
                    steps += 1 << k
            return steps + 1

        ans = []
        for u, v in queries:
            if u == v:
                ans.append(0)
                continue
            pu, pv = rank[u], rank[v]
            if comp[pu] != comp[pv]:
                ans.append(-1)
            else:
                p, q = (pu, pv) if pu < pv else (pv, pu)
                ans.append(hops(p, q))
        return ans