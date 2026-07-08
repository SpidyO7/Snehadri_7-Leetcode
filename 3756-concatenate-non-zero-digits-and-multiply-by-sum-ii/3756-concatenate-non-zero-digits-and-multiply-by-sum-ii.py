from bisect import bisect_left, bisect_right

class Solution:
    def sumAndMultiply(self, s, queries):
        MOD = 10**9 + 7

        pos = []
        digits = []

        for i, ch in enumerate(s):
            if ch != '0':
                pos.append(i)
                digits.append(int(ch))

        n = len(digits)

        pow10 = [1] * (n + 1)
        for i in range(1, n + 1):
            pow10[i] = (pow10[i - 1] * 10) % MOD

        prefixNum = [0] * (n + 1)
        prefixSum = [0] * (n + 1)

        for i in range(n):
            prefixNum[i + 1] = (prefixNum[i] * 10 + digits[i]) % MOD
            prefixSum[i + 1] = prefixSum[i] + digits[i]

        ans = []

        for l, r in queries:
            left = bisect_left(pos, l)
            right = bisect_right(pos, r)

            if left == right:
                ans.append(0)
                continue

            digitSum = prefixSum[right] - prefixSum[left]

            number = prefixNum[right] - prefixNum[left] * pow10[right - left]
            number %= MOD

            ans.append((number * digitSum) % MOD)

        return ans