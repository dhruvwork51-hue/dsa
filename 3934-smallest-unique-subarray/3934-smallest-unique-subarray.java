class Solution {
    public int smallestUniqueSubarray(int[] nums) {
        int n = nums.length;
        int[] sa = new int[n];
        int[] rank = new int[n];
        int[] temp = new int[n];

        for (int i = 0; i < n; i++) {
            sa[i] = i;
            rank[i] = nums[i];
        }

        for (int k = 1; k < n; k <<= 1) {
            final int len = k;
            final int[] r = rank;

            Integer[] idx = new Integer[n];
            for (int i = 0; i < n; i++) idx[i] = sa[i];

            java.util.Arrays.sort(idx, (a, b) -> {
                if (r[a] != r[b]) return Integer.compare(r[a], r[b]);
                int ra = a + len < n ? r[a + len] : -1;
                int rb = b + len < n ? r[b + len] : -1;
                return Integer.compare(ra, rb);
            });

            for (int i = 0; i < n; i++) sa[i] = idx[i];

            temp[sa[0]] = 0;
            for (int i = 1; i < n; i++) {
                int a = sa[i - 1], b = sa[i];

                int ra1 = rank[a], rb1 = rank[b];
                int ra2 = a + k < n ? rank[a + k] : -1;
                int rb2 = b + k < n ? rank[b + k] : -1;

                temp[b] = temp[a] + ((ra1 != rb1 || ra2 != rb2) ? 1 : 0);
            }

            System.arraycopy(temp, 0, rank, 0, n);
            if (rank[sa[n - 1]] == n - 1) break;
        }

        int[] pos = new int[n];
        for (int i = 0; i < n; i++) pos[sa[i]] = i;

        int[] lcp = new int[n];
        int h = 0;

        for (int i = 0; i < n; i++) {
            int r = pos[i];
            if (r == 0) continue;

            int j = sa[r - 1];

            while (i + h < n && j + h < n && nums[i + h] == nums[j + h]) {
                h++;
            }

            lcp[r] = h;
            if (h > 0) h--;
        }

        int ans = n;

        for (int i = 0; i < n; i++) {
            int r = pos[i];
            int maxLcp = 0;

            if (r > 0) maxLcp = Math.max(maxLcp, lcp[r]);
            if (r < n - 1) maxLcp = Math.max(maxLcp, lcp[r + 1]);

            int len = maxLcp + 1;

            if (i + len <= n) {
                ans = Math.min(ans, len);
            }
        }

        return ans;
    }
}