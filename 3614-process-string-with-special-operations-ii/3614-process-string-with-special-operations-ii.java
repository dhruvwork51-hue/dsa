
class Solution {
    public char processStr(String s, long k) {
        int n = s.length();
        long[] len = new long[n + 1];
        long limit = (long) 1e18;

        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);

            if (c == '*') {
                len[i + 1] = Math.max(0, len[i] - 1);
            } else if (c == '#') {
                len[i + 1] = Math.min(limit, len[i] * 2);
            } else {
                len[i + 1] = len[i] + (c == '%' ? 0 : 1);
            }
        }

        if (k >= len[n]) return '.';

        for (int i = n - 1; i >= 0; i--) {
            char c = s.charAt(i);

            if (c == '*') {
                len[i + 1] = len[i];
            } else if (c == '#') {
                if (k >= len[i]) {
                    k -= len[i];
                }
            } else if (c == '%') {
                k = len[i + 1] - 1 - k;
            } else {
                if (k == len[i]) return c;
            }
        }

        return '.';
    }
}
