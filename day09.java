import java.io.*;
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.HashSet;
import java.awt.Point;

public class day9 {
    static int[] x = new int[10];
    static int[] y = new int[10];

    public static void main(String[] args) throws IOException {
        BufferedReader r = new BufferedReader(new FileReader("input.txt"));
        PrintWriter pw = new PrintWriter(System.out);

        ArrayList<String> dirs = new ArrayList<>();
        ArrayList<Integer> amts = new ArrayList<>();

        String s;
        while ((s = r.readLine()) != null) {
            StringTokenizer st = new StringTokenizer(s);
            dirs.add(st.nextToken());
            amts.add(Integer.parseInt(st.nextToken()));
        }

        /*--------PART 1--------*/

        HashSet<Point> visited1 = new HashSet<>();
        HashSet<Point> visited2 = new HashSet<>();

        //simulate for a length 10 snake, since you can get both parts anyway
        for (int i = 0; i < dirs.size(); i++) {
            for (int j = 0; j < amts.get(i); j++) {
                switch (dirs.get(i)) {
                    case "U" -> y[0]++;
                    case "D" -> y[0]--;
                    case "R" -> x[0]++;
                    default -> x[0]--;
                }
                for (int k = 1; k < 10; k++) {
                    update(k);
                }
                visited1.add(new Point(x[1], y[1]));
                visited2.add(new Point(x[9], y[9]));
            }
        }

        pw.println("Part 1: " + visited1.size());

        /*--------PART 2--------*/

        pw.println("Part 2: " + visited2.size());

        pw.close();
        r.close();
    }

    public static void update(int i) { //updates each section of rope
        if (Math.abs(x[i] - x[i - 1]) == 2 && Math.abs(y[i] - y[i - 1]) == 2) {
            x[i] = (x[i] + x[i - 1]) / 2;
            y[i] = (y[i] + y[i - 1]) / 2;
        } else if (Math.abs(x[i] - x[i - 1]) == 2) {
            y[i] = y[i - 1];
            x[i] = (x[i] + x[i - 1]) / 2;
        } else if (Math.abs(y[i] - y[i - 1]) == 2) {
            x[i] = x[i - 1];
            y[i] = (y[i] + y[i - 1]) / 2;
        }
    }
}
