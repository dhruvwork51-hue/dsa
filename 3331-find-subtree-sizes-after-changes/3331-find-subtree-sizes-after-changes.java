class Solution {
    public int[] findSubtreeSizes(int[] parent, String s) {
        int n = parent.length;
        List<List<Integer>> adj = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }

        for (int i = 1; i < n; i++) {
            adj.get(parent[i]).add(i);
        }

        int[] newParent = parent.clone();
        int[] last = new int[26];
        Arrays.fill(last, -1);

        dfs(0, adj, s, last, newParent);

        List<List<Integer>> tree = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            tree.add(new ArrayList<>());
        }

        for (int i = 1; i < n; i++) {
            tree.get(newParent[i]).add(i);
        }

        int[] ans = new int[n];
        dfsSize(0, tree, ans);

        return ans;
    }

    private void dfs(int node, List<List<Integer>> adj, String s, int[] last, int[] newParent) {
        int c = s.charAt(node) - 'a';
        int old = last[c];

        if (node != 0 && old != -1) {
            newParent[node] = old;
        }

        last[c] = node;

        for (int child : adj.get(node)) {
            dfs(child, adj, s, last, newParent);
        }

        last[c] = old;
    }

    private int dfsSize(int node, List<List<Integer>> tree, int[] ans) {
        int size = 1;

        for (int child : tree.get(node)) {
            size += dfsSize(child, tree, ans);
        }

        ans[node] = size;
        return size;
    }
}