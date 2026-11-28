import java.util.*;

class Solution {
    public int maximumSaleItems(int[][] items, int budget) {
        int n = items.length;
        int[] freq = new int[n + 1];
        int minPrice = Integer.MAX_VALUE;

        for (int[] item : items) {
            freq[item[0]]++;
            minPrice = Math.min(minPrice, item[1]);
        }

        int[] count = new int[n + 1];

        for (int f = 1; f <= n; f++) {
            for (int j = f; j <= n; j += f) {
                count[f] += freq[j];
            }
            if (count[f] > 0) {
                count[f]--;
            }
        }

        ArrayList<int[]> deals = new ArrayList<>();

        for (int[] item : items) {
            int factor = item[0];
            int price = item[1];

            if (count[factor] > 0 && price < 2L * minPrice) {
                deals.add(new int[]{price, count[factor]});
            }
        }

        deals.sort((a, b) -> Integer.compare(a[0], b[0]));

        long ans = 0;
        long remaining = budget;

        for (int[] deal : deals) {
            long price = deal[0];
            long available = deal[1];

            long buy = Math.min(available, remaining / price);

            ans += buy * 2;
            remaining -= buy * price;
        }

        ans += remaining / minPrice;

        return (int) ans;
    }
}