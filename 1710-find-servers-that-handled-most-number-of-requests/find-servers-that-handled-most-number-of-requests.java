import java.util.*;

class Solution {
    public List<Integer> busiestServers(int k, int[] arrival, int[] load) {
        TreeSet<Integer> free = new TreeSet<>();

        PriorityQueue<long[]> busy = new PriorityQueue<>(
            (a, b) -> Long.compare(a[0], b[0])
        );

        int[] count = new int[k];

        for (int i = 0; i < k; i++) {
            free.add(i);
        }

        for (int i = 0; i < arrival.length; i++) {

            while (!busy.isEmpty() && busy.peek()[0] <= arrival[i]) {
                int server = (int) busy.poll()[1];
                free.add(server);
            }

            if (free.isEmpty()) {
                continue;
            }

            int start = i % k;
            Integer server = free.ceiling(start);

            if (server == null) {
                server = free.first();
            }

            free.remove(server);

            count[server]++;

            busy.offer(new long[]{
                (long) arrival[i] + load[i], server
            });
        }

        int max = 0;

        for (int i = 0; i < k; i++) {
            max = Math.max(max, count[i]);
        }

        List<Integer> ans = new ArrayList<>();

        for (int i = 0; i < k; i++) {
            if (count[i] == max) {
                ans.add(i);
            }
        }

        return ans;
    }
}