import java.io.*;
import java.util.ArrayList;
import java.util.Stack;
import java.util.StringTokenizer;

public class day5 {

    public static void main(String[] args) throws IOException {
        BufferedReader r = new BufferedReader(new FileReader("input.txt"));
        PrintWriter pw = new PrintWriter(System.out);

        ArrayList<ArrayList<Character>> temp = new ArrayList<>();

        String s;
        while ((s = r.readLine()).length() != 0) {
            ArrayList<Character> row = new ArrayList<>();
            for (int i = 1; i < s.length(); i += 4) {
                row.add(s.charAt(i));
            }
            temp.add(row);
        }
        temp.remove(temp.size() - 1);

        ArrayList<Stack<Character>> stacks = new ArrayList<>();
        stacks = toStacks(temp);

        ArrayList<ArrayList<Integer>> queries = new ArrayList<>();
        while ((s = r.readLine()) != null) {
            ArrayList<Integer> arr = new ArrayList<>();
            StringTokenizer st = new StringTokenizer(s);
            for (int i = 0; i < 3; i++) {
                st.nextToken(); //"move", "from", "to"
                arr.add(Integer.parseInt(st.nextToken()));
            }
            queries.add(arr);
        }

        /*--------PART 1--------*/

        for (ArrayList<Integer> query : queries) {
            for (int i = 0; i < query.get(0); i++) {
                stacks.get(query.get(2)).add(stacks.get(query.get(1)).pop());
            }
        }

        pw.print("Part 1: ");
        for (int i = 1; i < stacks.size(); i++) {
            pw.print(stacks.get(i).peek());
        }
        pw.println();

        /*--------PART 2--------*/

        stacks = toStacks(temp);
        for (ArrayList<Integer> query : queries) {
            Stack<Character> reversal = new Stack<>();
            for (int i = 0; i < query.get(0); i++) {
                reversal.add(stacks.get(query.get(1)).pop());
            }
            for (int i = 0; i < query.get(0); i++) {
                stacks.get(query.get(2)).add(reversal.pop());
            }
        }

        pw.print("Part 2: ");
        for (int i = 1; i < stacks.size(); i++) {
            pw.print(stacks.get(i).peek());
        }
        pw.println();

        pw.close();
        r.close();
    }

    static ArrayList<Stack<Character>> toStacks(ArrayList<ArrayList<Character>> temp) {
        ArrayList<Stack<Character>> stacks = new ArrayList<>();
        for (int i = 0; i <= temp.get(temp.size() - 1).size(); i++) {
            stacks.add(new Stack<>());
        }

        for (int i = temp.size() - 1; i >= 0; i--) {
            for (int j = 0; j < temp.get(i).size(); j++) {
                if (temp.get(i).get(j) != ' ') {
                    stacks.get(j + 1).add(temp.get(i).get(j));
                }
            }
        }
        return stacks;
    }
}
