import java.util.*;

class Solution {
    String s;
    int index;

    public List<String> braceExpansionII(String expression) {
        s = expression;
        index = 0;

        Set<String> set = parseExpression();

        List<String> ans = new ArrayList<>(set);
        Collections.sort(ans);

        return ans;
    }

    Set<String> parseExpression() {
        Set<String> result = parseTerm();

        while (index < s.length() && s.charAt(index) == ',') {
            index++;
            result.addAll(parseTerm());
        }

        return result;
    }

    Set<String> parseTerm() {
        Set<String> result = new HashSet<>();
        result.add("");

        while (index < s.length()
                && s.charAt(index) != '}'
                && s.charAt(index) != ',') {

            Set<String> next;

            if (s.charAt(index) == '{') {
                index++;
                next = parseExpression();
                index++;
            } else {
                next = new HashSet<>();
                next.add(String.valueOf(s.charAt(index)));
                index++;
            }

            result = multiply(result, next);
        }

        return result;
    }

    Set<String> multiply(Set<String> a, Set<String> b) {
        Set<String> result = new HashSet<>();

        for (String x : a) {
            for (String y : b) {
                result.add(x + y);
            }
        }

        return result;
    }
}