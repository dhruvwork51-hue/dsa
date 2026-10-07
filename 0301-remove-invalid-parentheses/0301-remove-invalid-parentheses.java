class Solution {
    Set<String> ans = new HashSet<>();
    String s;
    int n;

    public List<String> removeInvalidParentheses(String s) {
        this.s = s;
        this.n = s.length();

        int left = 0;
        int right = 0;

        for (char c : s.toCharArray()) {
            if (c == '(') {
                left++;
            } else if (c == ')') {
                if (left > 0) {
                    left--;
                } else {
                    right++;
                }
            }
        }

        dfs(0, left, right, 0, 0, new StringBuilder());

        return new ArrayList<>(ans);
    }

    private void dfs(int i, int leftRemove, int rightRemove,
                     int open, int close, StringBuilder cur) {

        if (open < close) return;

        if (i == n) {
            if (leftRemove == 0 && rightRemove == 0 && open == close) {
                ans.add(cur.toString());
            }
            return;
        }

        char c = s.charAt(i);

        if (c == '(' && leftRemove > 0) {
            dfs(i + 1, leftRemove - 1, rightRemove, open, close, cur);
        }

        if (c == ')' && rightRemove > 0) {
            dfs(i + 1, leftRemove, rightRemove - 1, open, close, cur);
        }

        cur.append(c);

        if (c == '(') {
            dfs(i + 1, leftRemove, rightRemove, open + 1, close, cur);
        } else if (c == ')') {
            if (open > close) {
                dfs(i + 1, leftRemove, rightRemove, open, close + 1, cur);
            }
        } else {
            dfs(i + 1, leftRemove, rightRemove, open, close, cur);
        }

        cur.deleteCharAt(cur.length() - 1);
    }
}