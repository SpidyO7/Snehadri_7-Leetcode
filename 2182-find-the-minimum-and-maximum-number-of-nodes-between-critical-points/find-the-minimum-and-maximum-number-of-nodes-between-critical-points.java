class Solution {
    public int[] nodesBetweenCriticalPoints(ListNode head) {
        int min = Integer.MAX_VALUE;
        int first = -1;
        int last = -1;
        int pos = 1;

        ListNode prev = head;
        ListNode curr = head.next;

        while (curr.next != null) {
            if ((curr.val > prev.val && curr.val > curr.next.val) ||
                (curr.val < prev.val && curr.val < curr.next.val)) {

                if (first == -1) {
                    first = pos;
                } else {
                    min = Math.min(min, pos - last);
                }

                last = pos;
            }

            prev = curr;
            curr = curr.next;
            pos++;
        }

        if (first == last) {
            return new int[]{-1, -1};
        }

        return new int[]{min, last - first};
    }
}