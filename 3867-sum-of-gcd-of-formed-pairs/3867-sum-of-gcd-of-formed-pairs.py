class Solution:
    def gcdSum(self, nums):

        def findGcd(a, b):
            while b != 0:
                temp = a % b
                a = b
                b = temp
            return a

        arr = []
        maximum = nums[0]

        for num in nums:
            if num > maximum:
                maximum = num

            value = findGcd(num, maximum)
            arr.append(value)

        arr.sort()

        i = 0
        j = len(arr) - 1
        total = 0

        while i < j:
            total += findGcd(arr[i], arr[j])
            i += 1
            j -= 1

        return total