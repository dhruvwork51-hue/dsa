class Solution {
    static final long MOD = 1_000_000_007;
    long[] sum;
    long[] sq;
    long[] lazy;
    int n;

    public int sumCounts(int[] nums) {
        n = nums.length;
        sum = new long[4 * n];
        sq = new long[4 * n];
        lazy = new long[4 * n];

        int[] last = new int[100001];
        Arrays.fill(last, -1);

        long ans = 0;

        for (int i = 0; i < n; i++) {
            int left = last[nums[i]] + 1;
            update(1, 0, n - 1, left, i);
            last[nums[i]] = i;
            ans = (ans + sq[1]) % MOD;
        }

        return (int) ans;
    }

    void apply(int node, int l, int r, long v) {
        sq[node] = (sq[node] + 2 * v * sum[node] + v * v * (r - l + 1)) % MOD;
        sum[node] = (sum[node] + v * (r - l + 1)) % MOD;
        lazy[node] = (lazy[node] + v) % MOD;
    }

    void push(int node, int l, int r) {
        if (lazy[node] == 0 || l == r) return;

        int mid = (l + r) / 2;

        apply(node * 2, l, mid, lazy[node]);
        apply(node * 2 + 1, mid + 1, r, lazy[node]);

        lazy[node] = 0;
    }

    void update(int node, int l, int r, int ql, int qr) {
        if (ql > r || qr < l) return;

        if (ql <= l && r <= qr) {
            apply(node, l, r, 1);
            return;
        }

        push(node, l, r);

        int mid = (l + r) / 2;

        update(node * 2, l, mid, ql, qr);
        update(node * 2 + 1, mid + 1, r, ql, qr);

        sum[node] = (sum[node * 2] + sum[node * 2 + 1]) % MOD;
        sq[node] = (sq[node * 2] + sq[node * 2 + 1]) % MOD;
    }
}