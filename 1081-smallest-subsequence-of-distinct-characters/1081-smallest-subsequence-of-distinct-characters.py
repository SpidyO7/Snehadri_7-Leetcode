class Solution:
    def smallestSubsequence(self, s):
        last = {}

        for i in range(len(s)):
            last[s[i]] = i

        stack = []
        used = set()

        for i in range(len(s)):
            ch = s[i]

            if ch in used:
                continue

            while stack and stack[-1] > ch and last[stack[-1]] > i:
                used.remove(stack.pop())

            stack.append(ch)
            used.add(ch)

        return "".join(stack)