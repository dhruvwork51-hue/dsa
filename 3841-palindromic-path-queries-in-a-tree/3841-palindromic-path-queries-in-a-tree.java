import java.util.*;

class Solution {
    public List<Boolean> palindromePath(int n, int[][] edges, String s, String[] queries) {
        List<Integer>[] graph = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int[] e : edges) {
            graph[e[0]].add(e[1]);
            graph[e[1]].add(e[0]);
        }

        int LOG = 1;
        while ((1 << LOG) <= n) LOG++;

        int[][] up = new int[LOG][n];
        int[] parent = new int[n];
        int[] depth = new int[n];
        int[] tin = new int[n];
        int[] tout = new int[n];
        int[] order = new int[n];
        int[] size = new int[n];
        int[] iter = new int[n];

        Arrays.fill(parent, -1);

        int[] stack = new int[n];
        int top = 0;
        int timer = 0;

        stack[top++] = 0;
        parent[0] = 0;

        while (top > 0) {
            int u = stack[top - 1];

            if (iter[u] == 0) {
                tin[u] = timer;
                order[timer++] = u;
            }

            if (iter[u] < graph[u].size()) {
                int v = graph[u].get(iter[u]++);

                if (v == parent[u]) continue;

                parent[v] = u;
                depth[v] = depth[u] + 1;
                stack[top++] = v;
            } else {
                tout[u] = timer - 1;
                top--;
            }
        }

        for (int i = 0; i < n; i++) {
            up[0][i] = parent[i];
        }

        for (int j = 1; j < LOG; j++) {
            for (int i = 0; i < n; i++) {
                up[j][i] = up[j - 1][up[j - 1][i]];
            }
        }

        char[] chars = s.toCharArray();
        int[] prefix = new int[n];

        for (int u : order) {
            prefix[u] = (1 << (chars[u] - 'a'));

            if (u != 0) {
                prefix[u] ^= prefix[parent[u]];
            }
        }

        Fenwick bit = new Fenwick(n);

        for (int u = 0; u < n; u++) {
            bit.rangeXor(tin[u], tin[u], prefix[u]);
        }

        List<Boolean> ans = new ArrayList<>();

        for (String q : queries) {
            String[] p = q.split(" ");

            if (p[0].equals("update")) {
                int u = Integer.parseInt(p[1]);
                char c = p[2].charAt(0);

                int oldMask = 1 << (chars[u] - 'a');
                int newMask = 1 << (c - 'a');
                int delta = oldMask ^ newMask;

                bit.rangeXor(tin[u], tout[u], delta);
                chars[u] = c;
            } else {
                int u = Integer.parseInt(p[1]);
                int v = Integer.parseInt(p[2]);
                int l = lca(u, v, depth, up, LOG);

                int mask = bit.query(tin[u])
                         ^ bit.query(tin[v])
                         ^ (1 << (chars[l] - 'a'));

                ans.add((mask & (mask - 1)) == 0);
            }
        }

        return ans;
    }

    private int lca(int u, int v, int[] depth, int[][] up, int LOG) {
        if (depth[u] < depth[v]) {
            int temp = u;
            u = v;
            v = temp;
        }

        int diff = depth[u] - depth[v];

        for (int j = 0; j < LOG; j++) {
            if ((diff & (1 << j)) != 0) {
                u = up[j][u];
            }
        }

        if (u == v) return u;

        for (int j = LOG - 1; j >= 0; j--) {
            if (up[j][u] != up[j][v]) {
                u = up[j][u];
                v = up[j][v];
            }
        }

        return up[0][u];
    }

    static class Fenwick {
        int[] tree;
        int n;

        Fenwick(int n) {
            this.n = n;
            tree = new int[n + 2];
        }

        void update(int i, int val) {
            for (i++; i <= n; i += i & -i) {
                tree[i] ^= val;
            }
        }

        void rangeXor(int l, int r, int val) {
            update(l, val);
            if (r + 1 < n) {
                update(r + 1, val);
            }
        }

        int query(int i) {
            int ans = 0;

            for (i++; i > 0; i -= i & -i) {
                ans ^= tree[i];
            }

            return ans;
        }
    }
}