import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;

public class day3 {

    public static void main(String[] args) throws IOException {
        BufferedReader r = new BufferedReader(new FileReader("input.txt"));
        PrintWriter pw = new PrintWriter(System.out);

        ArrayList<String> arr = new ArrayList<>();

        String s;
        while ((s = r.readLine()) != null) {
            arr.add(s);
        }

        /*--------PART 1--------*/

        int ans = 0;
        for (String x : arr) {
            String x1 = x.substring(0, x.length()/2);
            String x2 = x.substring(x.length()/2);

            HashSet<Character> test = new HashSet<>();
            for (int i = 0; i < x1.length(); i++) {
                test.add(x1.charAt(i));
            } //hashset for O(1) searches

            for (int i = 0; i < x2.length(); i++) {
                if (test.contains(x2.charAt(i))) {
                    ans += toInt(x2.charAt(i));
                    break;
                }
            }
        }

        pw.println("Part 1: " + ans);

        /*--------PART 2--------*/

        ans = 0;
        for (int x = 0; x < arr.size(); x += 3) {
            HashSet<Character> test1 = new HashSet<>();
            for (int i = 0; i < arr.get(x).length(); i++) {
                test1.add(arr.get(x).charAt(i));
            }
            HashSet<Character> test2 = new HashSet<>();
            for (int i = 0; i < arr.get(x + 1).length(); i++) {
                test2.add(arr.get(x + 1).charAt(i));
            } //hashset for O(1) searches

            for (int i = 0; i < arr.get(x + 2).length(); i++) {
                if (test1.contains(arr.get(x + 2).charAt(i)) && test2.contains(arr.get(x + 2).charAt(i))) {
                    ans += toInt(arr.get(x + 2).charAt(i));
                    break;
                }
            }
        }

        pw.println("Part 2: " + ans);

        pw.close();
        r.close();
    }

    static int toInt(char value) {
        if (value - 'a' >= 0 && value - 'a' <= 25) {
            return value - 'a' + 1;
        }
        return value - 'A' + 27;
    }
}
