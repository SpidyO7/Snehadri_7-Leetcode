class Solution:
    def arrayRankTransform(self, arr):
        rank = {}
        current = 1

        for num in sorted(set(arr)):
            rank[num] = current
            current += 1

        ans = []

        for num in arr:
            ans.append(rank[num])

        return ans