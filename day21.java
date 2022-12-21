import java.io.*;
import java.util.HashMap;
import java.util.StringTokenizer;

public class day21 {

    static HashMap<String, Operation> opMonkey = new HashMap<>();
    static HashMap<String, Long> yellMonkey = new HashMap<>();

    public static void main(String[] args) throws IOException {
        BufferedReader r = new BufferedReader(new FileReader("input.txt"));
        PrintWriter pw = new PrintWriter(System.out);

        String s;
        while ((s = r.readLine()) != null) {
            StringTokenizer st = new StringTokenizer(s);
            
            if (st.countTokens() == 2) { //yells a number
                String name = st.nextToken(); 
                name = name.substring(0, name.length() - 1);
                yellMonkey.put(name, Long.parseLong(st.nextToken()));
            } else { //does an operation
                String name = st.nextToken(); 
                name = name.substring(0, name.length() - 1);
                Operation t = new Operation(st.nextToken(), st.nextToken().charAt(0), st.nextToken());
                opMonkey.put(name, t);
            }
        }

        /*--------PART 1--------*/

        pw.println(dfs("root"));

        /*--------PART 2--------*/

        String hasHuman;
        String other;

        if (containsHuman(opMonkey.get("root").m1)) {
            hasHuman = opMonkey.get("root").m1;
            other = opMonkey.get("root").m2;
        } else {
            hasHuman = opMonkey.get("root").m2;
            other = opMonkey.get("root").m1;
        }
        pw.println(reverseDfs(hasHuman, dfs(other)));

        pw.close();
        r.close();
    }

    record Operation(String m1, char op, String m2) {}

    static long reverseDfs(String m, long val) { //monkey m must yell val
        if (m.equals("humn")) {
            return val;
        }

        String m1 = opMonkey.get(m).m1;
        char op = opMonkey.get(m).op;
        String m2 = opMonkey.get(m).m2;
        if (containsHuman(m1)) {
            if (op == '+') {
                return reverseDfs(m1, val - dfs(m2));
            } else if (op == '-') {
                return reverseDfs(m1, val + dfs(m2));
            } else if (op == '*') {
                return reverseDfs(m1, val / dfs(m2));
            } else {
                return reverseDfs(m1, val * dfs(m2));
            }
        } else {
            if (op == '+') {
                return reverseDfs(m2, val - dfs(m1));
            } else if (op == '-') {
                return reverseDfs(m2, dfs(m1) - val);
            } else if (op == '*') {
                return reverseDfs(m2, val / dfs(m1));
            } else {
                return reverseDfs(m2, dfs(m1) / val);
            }
        }
    }

    static boolean containsHuman(String m) {
        if (m.equals("humn")) {return true; }
        if (yellMonkey.containsKey(m)) {return false; }
        return containsHuman(opMonkey.get(m).m1) || containsHuman(opMonkey.get(m).m2);
    }

    static long dfs(String m) {
        if (yellMonkey.containsKey(m)) {
            return yellMonkey.get(m);
        }
        Operation t = opMonkey.get(m);
        if (t.op == '+') {
            return dfs(t.m1) + dfs(t.m2);
        } else if (t.op == '-') {
            return dfs(t.m1) - dfs(t.m2);
        } else if (t.op == '*') {
            return dfs(t.m1) * dfs(t.m2);
        } else {
            return dfs(t.m1) / dfs(t.m2);
        }
    }
}
