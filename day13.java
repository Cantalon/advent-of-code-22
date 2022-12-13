import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class day13 {

    public static void main(String[] args) throws IOException {
        BufferedReader r = new BufferedReader(new FileReader("input.txt"));
        PrintWriter pw = new PrintWriter(System.out);

        ArrayList<Shit> part2 = new ArrayList<>();

        /*--------PART 1--------*/

        int ans = 0;
        int index = 0;
        String s;
        while ((s = r.readLine()) != null) {
            Shit shit1 = new Shit(s);
            Shit shit2 = new Shit(r.readLine());
            part2.add(shit1); part2.add(shit2);

            index++;
            if (shit1.compareTo(shit2) > 0) {
                ans += index;
            }
            r.readLine();
        }
        pw.println("Part 1: " + ans);

        /*--------PART 2--------*/

        ans = 1;
        part2.add(new Shit("[[2]]"));
        part2.add(new Shit("[[6]]"));
        Collections.sort(part2);
        for (int i = part2.size() - 1; i >= 0; i--) {
            if (part2.get(i).s.equals("[[2]]") || part2.get(i).s.equals("[[6]]")) {
                ans *= part2.size() - i;
            }
        }
        pw.println("Part 2: " + ans);

        pw.close();
        r.close();

    }

    public static class Shit implements Comparable<Shit> {
        ArrayList<Shit> subshits = new ArrayList<>();
        boolean isInt;
        int value;
        String s;

        public Shit(String s) {
            this.s = s;

            if (s.equals("[]")) { // an empty arr
                value = -1;
                isInt = true;
            } else if (!s.startsWith("[")) { // an integer
                value = Integer.parseInt(s);
                isInt = true;
            } else { //a nonempty arr
                isInt = false;
                s = s.substring(1, s.length() - 1);

                int brackets = 0;
                String temp = "";
                for (int i = 0; i < s.length(); i++) {
                    if (s.charAt(i) == ',' && brackets == 0) {
                        subshits.add(new Shit(temp));
                        temp = "";
                    } else {
                        if (s.charAt(i) == '[') {
                            brackets++;
                        } else if (s.charAt(i) == ']') {
                            brackets--;
                        }
                        temp += s.charAt(i);
                    }
                }
                if (!temp.equals("")) { //clearing remaining data
                    subshits.add(new Shit(temp));
                }
            }
        }

        public int compareTo(Shit o) {
            if (isInt && o.isInt) { //comparing integers
                return Integer.compare(o.value, value);
            }
            if (!isInt && !o.isInt) { //comparing lists
                for (int i = 0; i < Math.min(subshits.size(), o.subshits.size()); i++) {
                    int val = subshits.get(i).compareTo(o.subshits.get(i));
                    if (val != 0) {
                        return val;
                    }
                }
                return Integer.compare(o.subshits.size(), subshits.size());
            } //convert both of them to lists, then recompare
            Shit l1 = this;
            if (isInt) {
                l1 = new Shit("[" + value + "]");
            }
            Shit l2 = o;
            if (o.isInt) {
                l2 = new Shit("[" + o.value + "]");
            }
            return l1.compareTo(l2);
        }
    }
}
