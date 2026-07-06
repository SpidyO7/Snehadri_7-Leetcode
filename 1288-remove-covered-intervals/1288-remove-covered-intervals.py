class Solution:
    def removeCoveredIntervals(self, intervals):
        intervals.sort(key=lambda x: (x[0], -x[1]))

        ans = 0
        end = -1

        for l, r in intervals:
            if r > end:
                ans += 1
                end = r

        return ans