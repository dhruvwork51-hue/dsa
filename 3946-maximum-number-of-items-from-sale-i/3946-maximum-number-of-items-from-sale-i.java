class Solution {
    public int maximumSaleItems(int[][] items, int budget) {
        int n = items.length;
        int minPrice = Integer.MAX_VALUE;
        int[] reward = new int[n];

        for (int[] item : items) {
            minPrice = Math.min(minPrice, item[1]);
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != j && items[j][0] % items[i][0] == 0) {
                    reward[i]++;
                }
            }
        }

        int[] dp = new int[budget + 1];
        java.util.Arrays.fill(dp, -1);
        dp[0] = 0;

        for (int i = 0; i < n; i++) {
            int price = items[i][1];
            int value = reward[i] + 1;

            for (int b = budget; b >= price; b--) {
                if (dp[b - price] != -1) {
                    dp[b] = Math.max(dp[b], dp[b - price] + value);
                }
            }
        }

        int ans = 0;

        for (int b = 0; b <= budget; b++) {
            if (dp[b] != -1) {
                ans = Math.max(ans, dp[b] + (budget - b) / minPrice);
            }
        }

        return ans;
    }
}