class Solution {
    List<String> result = new ArrayList<>();

    public List<String> generateParenthesis(int n) {
        backtrack("", 0, 0, n);
        return result;
    }

    private void backtrack(String current, int open, int close, int n) {

        // Base Case
        if (current.length() == 2 * n) {
            result.add(current);
            return;
        }

        // Add '(' if possible
        if (open < n) {
            backtrack(current + "(", open + 1, close, n);
        }

        // Add ')' if it won't make the string invalid
        if (close < open) {
            backtrack(current + ")", open, close + 1, n);
        }
    }
}