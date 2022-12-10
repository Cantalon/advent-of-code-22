import java.io.*;
import java.util.StringTokenizer;

public class day10 {
    public static void main(String[] args) throws IOException {
        BufferedReader r = new BufferedReader(new FileReader("input.txt"));
        PrintWriter pw = new PrintWriter(System.out);
        
        //part 1 vars
        int cycle = 0;
        int X = 1;
        int ans = 0;

        //part 2 vars
        char[] grid = new char[240];
        int crt = -1;

        String s;
        while ((s = r.readLine()) != null) {
            StringTokenizer st = new StringTokenizer(s);
            //part 2
            crt++;
            if (Math.abs(crt % 40 - X) <= 1) {grid[crt] = '#';}
            else {grid[crt] = '.';}

            //part 1
            cycle++;
            ans += strength(X, cycle);

            if (st.nextToken().equals("addx")) {
                //part 2
                crt++;
                if (Math.abs(crt % 40 - X) <= 1) {grid[crt] = '#';}
                else {grid[crt] = '.';}

                //part 1
                cycle++;
                ans += strength(X, cycle);
                X += Integer.parseInt(st.nextToken());
            }
        }

        /*--------PART 1--------*/

        pw.println("Part 1: " + ans);

        /*--------PART 2--------*/

        pw.println("Part 2: ");

        for (int i = 0; i < 240; i++) {
            pw.print(grid[i]);
            if (i % 40 == 39) {
                pw.println();
            }
        }

        pw.close();
        r.close();
    }

    static int strength(int X, int cycle) {
        if (cycle % 40 == 20) {
            return X * cycle;
        }
        return 0;
    }
}
