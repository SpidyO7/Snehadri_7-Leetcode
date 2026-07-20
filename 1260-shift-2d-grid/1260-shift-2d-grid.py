class Solution:
    def shiftGrid(self, grid, k):
        rows = len(grid)
        cols = len(grid[0])

        arr = []

        for i in range(rows):
            for j in range(cols):
                arr.append(grid[i][j])

        size = len(arr)
        k = k % size

        arr = arr[size - k:] + arr[:size - k]

        ans = []
        index = 0

        for i in range(rows):
            row = []

            for j in range(cols):
                row.append(arr[index])
                index += 1

            ans.append(row)

        return ans