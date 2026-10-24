class Solution {
    public int countDaysTogether(String arriveAlice, String leaveAlice, String arriveBob, String leaveBob) {
        int a1 = days(arriveAlice);
        int a2 = days(leaveAlice);
        int b1 = days(arriveBob);
        int b2 = days(leaveBob);

        int start = Math.max(a1, b1);
        int end = Math.min(a2, b2);

        return Math.max(0, end - start + 1);
    }

    public int days(String date) {
        int[] month = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        int m = Integer.parseInt(date.substring(0, 2));
        int d = Integer.parseInt(date.substring(3, 5));

        for (int i = 0; i < m - 1; i++) {
            d += month[i];
        }

        return d;
    }
}