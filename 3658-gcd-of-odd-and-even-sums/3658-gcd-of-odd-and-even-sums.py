class Solution:
    def gcdOfOddEvenSums(self, n):
        def gcd(a, b):
            while b:
                a, b = b, a % b
            return a

        sumOdd = n * n
        sumEven = n * (n + 1)

        return gcd(sumOdd, sumEven)