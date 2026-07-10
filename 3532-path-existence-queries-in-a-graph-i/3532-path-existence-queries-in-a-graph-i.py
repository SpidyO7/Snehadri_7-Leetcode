class Solution:
    def pathExistenceQueries(self, n, nums, maxDiff, queries):
        component = [0] * n
        group = 0

        for i in range(1, n):
            gap = nums[i] - nums[i - 1]
            if gap > maxDiff:
                group += 1
            component[i] = group

        result = []

        for u, v in queries:
            if component[u] == component[v]:
                result.append(True)
            else:
                result.append(False)

        return result