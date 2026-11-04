import java.util.*;

class Solution {
    TreeMap<Integer, Integer> low = new TreeMap<>();
    TreeMap<Integer, Integer> high = new TreeMap<>();

    long sumLow = 0, sumHigh = 0;
    int sizeLow = 0, sizeHigh = 0;

    void add(TreeMap<Integer, Integer> map, int val) {
        map.put(val, map.getOrDefault(val, 0) + 1);
    }

    void remove(TreeMap<Integer, Integer> map, int val) {
        int count = map.get(val);
        if (count == 1) map.remove(val);
        else map.put(val, count - 1);
    }

    void balance() {
        while (sizeLow > sizeHigh + 1) {
            int val = low.lastKey();
            remove(low, val);
            sumLow -= val;
            sizeLow--;

            add(high, val);
            sumHigh += val;
            sizeHigh++;
        }

        while (sizeLow < sizeHigh) {
            int val = high.firstKey();
            remove(high, val);
            sumHigh -= val;
            sizeHigh--;

            add(low, val);
            sumLow += val;
            sizeLow++;
        }
    }

    void insert(int val) {
        if (low.isEmpty() || val <= low.lastKey()) {
            add(low, val);
            sumLow += val;
            sizeLow++;
        } else {
            add(high, val);
            sumHigh += val;
            sizeHigh++;
        }
        balance();
    }

    void erase(int val) {
        if (low.containsKey(val)) {
            remove(low, val);
            sumLow -= val;
            sizeLow--;
        } else {
            remove(high, val);
            sumHigh -= val;
            sizeHigh--;
        }
        balance();
    }

    long getCost() {
        long median = low.lastKey();
        return median * sizeLow - sumLow
             + sumHigh - median * sizeHigh;
    }

    public long minOperations(int[] nums, int x, int k) {
        int n = nums.length;
        long[] cost = new long[n - x + 1];

        for (int i = 0; i < n; i++) {
            insert(nums[i]);

            if (i >= x) {
                erase(nums[i - x]);
            }

            if (i >= x - 1) {
                cost[i - x + 1] = getCost();
            }
        }

        long INF = Long.MAX_VALUE / 2;
        long[][] dp = new long[k + 1][n + 1];

        for (int i = 0; i <= k; i++) {
            Arrays.fill(dp[i], INF);
        }

        dp[0][0] = 0;

        for (int j = 0; j < k; j++) {
            for (int i = 0; i <= n; i++) {
                if (dp[j][i] == INF) continue;

                if (i < n) {
                    dp[j][i + 1] = Math.min(dp[j][i + 1], dp[j][i]);
                }

                if (i + x <= n) {
                    dp[j + 1][i + x] = Math.min(
                        dp[j + 1][i + x],
                        dp[j][i] + cost[i]
                    );
                }
            }
        }

        long ans = INF;

        for (int i = 0; i <= n; i++) {
            ans = Math.min(ans, dp[k][i]);
        }

        return ans;
    }
}