def gcd(a, b):
    while b:
        a, b = b, a % b
    return a


class Solution:
    def subsequencePairCount(self, nums):
        MOD = 10**9 + 7
        LIMIT = 200

        dp = [[0] * (LIMIT + 1) for _ in range(LIMIT + 1)]
        dp[0][0] = 1

        for num in nums:
            new_dp = [row[:] for row in dp]

            for g1 in range(LIMIT + 1):
                for g2 in range(LIMIT + 1):
                    if dp[g1][g2] == 0:
                        continue

                    ng1 = num if g1 == 0 else gcd(g1, num)
                    ng2 = num if g2 == 0 else gcd(g2, num)

                    new_dp[ng1][g2] = (new_dp[ng1][g2] + dp[g1][g2]) % MOD
                    new_dp[g1][ng2] = (new_dp[g1][ng2] + dp[g1][g2]) % MOD

            dp = new_dp

        ans = 0

        for g in range(1, LIMIT + 1):
            ans = (ans + dp[g][g]) % MOD

        return ans

class Solution:
    def subsequencePairCount(self, nums):
        MOD = 10**9 + 7
        LIMIT = 200

        dp = [[0] * (LIMIT + 1) for _ in range(LIMIT + 1)]
        dp[0][0] = 1

        for num in nums:
            new_dp = [row[:] for row in dp]

            for g1 in range(LIMIT + 1):
                for g2 in range(LIMIT + 1):
                    if dp[g1][g2] == 0:
                        continue

                    ng1 = num if g1 == 0 else gcd(g1, num)
                    ng2 = num if g2 == 0 else gcd(g2, num)

                    new_dp[ng1][g2] = (new_dp[ng1][g2] + dp[g1][g2]) % MOD
                    new_dp[g1][ng2] = (new_dp[g1][ng2] + dp[g1][g2]) % MOD

            dp = new_dp

        ans = 0

        for g in range(1, LIMIT + 1):
            ans = (ans + dp[g][g]) % MOD

        return ans