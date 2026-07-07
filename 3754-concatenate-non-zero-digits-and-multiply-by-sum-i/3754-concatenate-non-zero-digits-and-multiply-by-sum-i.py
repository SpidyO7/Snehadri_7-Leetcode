class Solution:
    def sumAndMultiply(self, n):
        newNum = 0
        s = 0

        for ch in str(n):
            if ch != "0":
                d = int(ch)
                newNum = newNum * 10 + d
                s += d

        return newNum * s