class Solution:
    def gcdValues(self, nums, queries):
        max_num = max(nums)

        freq = [0] * (max_num + 1)
        for num in nums:
            freq[num] += 1

        count = [0] * (max_num + 1)

        for g in range(1, max_num + 1):
            total = 0
            for multiple in range(g, max_num + 1, g):
                total += freq[multiple]

            count[g] = total * (total - 1) // 2

        for g in range(max_num, 0, -1):
            multiple = g * 2
            while multiple <= max_num:
                count[g] -= count[multiple]
                multiple += g

        prefix = []
        running = 0

        for g in range(1, max_num + 1):
            running += count[g]
            prefix.append(running)

        ans = []

        for q in queries:
            left = 0
            right = max_num - 1

            while left < right:
                mid = (left + right) // 2

                if prefix[mid] > q:
                    right = mid
                else:
                    left = mid + 1

            ans.append(left + 1)

        return ans