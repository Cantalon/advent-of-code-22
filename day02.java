import java.io.*;
import java.util.ArrayList;

public class day2 {

    public static void main(String[] args) throws IOException {
        BufferedReader r = new BufferedReader(new FileReader("input.txt"));
        PrintWriter pw = new PrintWriter(System.out);

        ArrayList<Integer> first = new ArrayList<>();
        ArrayList<Integer> second = new ArrayList<>();

        String s;
        while ((s = r.readLine()) != null) {
            first.add(s.charAt(0) - 'A' + 1);
            second.add(s.charAt(2) - 'X' + 1);
            //turns A,B,C and X,Y,Z into 1,2,3
        }

        /*--------PART 1--------*/

        int points = 0;
        for (int i = 0; i < first.size(); i++) {
            points += second.get(i);
            if (first.get(i) == second.get(i)) { //tie
                points += 3;
            } else if ((second.get(i) - first.get(i) + 3) % 3 == 1) { //win
                points += 6;
            }
        }

        pw.println("Part 1: " + points);

        /*--------PART 2--------*/

        points = 0;
        for (int i = 0; i < first.size(); i++) {
            points += 3 * (second.get(i) - 1);
            if (second.get(i) == 2) { //tie
                points += first.get(i);
            } else if (second.get(i) == 3) {//win
                points += first.get(i) % 3 + 1;
            } else { //lose
                points += (first.get(i) + 1) % 3 + 1;
            }
        }

        pw.println("Part 2: " + points);

        pw.close();
        r.close();
    }
}
