class Solution {
    public boolean uniformArray(int[] nums1) {
        boolean odd = false;
        boolean even = false;

        for (int x : nums1) {
            if (x % 2 == 0)
                even = true;
            else
                odd = true;
        }

        if (!odd || !even)
            return true;

        int oddValue = 0;

        for (int x : nums1) {
            if (x % 2 != 0) {
                oddValue = x;
                break;
            }
        }

        for (int x : nums1) {
            if (x % 2 == 0) {
                boolean possible = false;

                for (int y : nums1) {
                    if (x - y != 0 && (x - y) % 2 != 0) {
                        possible = true;
                        break;
                    }
                }

                if (!possible)
                    return false;
            }
        }

        return true;
    }
}