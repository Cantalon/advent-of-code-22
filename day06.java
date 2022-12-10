import java.io.*;
import java.util.HashSet;

public class day6 {

    public static void main(String[] args) throws IOException {
        BufferedReader r = new BufferedReader(new FileReader("input.txt"));
        PrintWriter pw = new PrintWriter(System.out);

        String s = r.readLine();

        /*--------PART 1--------*/

        for (int i = 3; i < s.length(); i++) {
            HashSet<Character> temp = new HashSet<>();
            for (int j = 0; j < 4; j++) {
                temp.add(s.substring(i - 3, i + 1).charAt(j));
            }
            if (temp.size() == 4) {
                pw.println("Part 1: " + (i + 1));
                break;
            }
        }

        /*--------PART 2--------*/

        for (int i = 13; i < s.length(); i++) {
            HashSet<Character> temp = new HashSet<>();
            for (int j = 0; j < 14; j++) {
                temp.add(s.substring(i - 13, i + 1).charAt(j));
            }
            if (temp.size() == 14) {
                pw.println("Part 2: " + (i + 1));
                break;
            }
        }

        pw.close();
        r.close();
    }
}
