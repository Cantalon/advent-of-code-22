import java.io.*;
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.Point;

public class day14 {

    static char[][] pic;
    static int maxX = 0, maxY = 0; //for determining dimensions of pic

    public static void main(String[] args) throws IOException {
        BufferedReader r = new BufferedReader(new FileReader("input.txt"));
        PrintWriter pw = new PrintWriter(System.out);

        ArrayList<ArrayList<Point>> points = new ArrayList<>();

        String s;
        while ((s = r.readLine()) != null) {
            StringTokenizer st = new StringTokenizer(s, " -> ");
            ArrayList<Point> temp = new ArrayList<>();
            while (st.hasMoreTokens()) {
                StringTokenizer split = new StringTokenizer(st.nextToken(), ",");
                
                int x = Integer.parseInt(split.nextToken());
                int y = Integer.parseInt(split.nextToken());

                maxY = Math.max(maxY, y); maxX = Math.max(maxX, x);
                temp.add(new Point(x, y));
            }
            points.add(temp);
        }

        pic = new char[Math.max(maxX + maxY, 500) + 1][maxY + 4]; //sufficient size
        for (char[] arr : pic) {
            Arrays.fill(arr, '.');
        }

        for (ArrayList<Point> arr : points) {
            for (int j = 0; j < arr.size() - 1; j++) {
                draw(arr.get(j), arr.get(j + 1));
            }
        }

        /*--------PART 1--------*/

        int ans = 0;
        while (fall(500, 0)) {
            ans++;
        }
        pw.println("Part 1: " + ans);

        /*--------PART 2--------*/

        draw(new Point(0, maxY + 2), new Point(Math.max(500 + maxY, maxX), maxY + 2));

        while (pic[500][0] == '.') {
            fall(500, 0);
            ans++;
        }
        pw.println("Part 2: " + ans);

        pw.close();
        r.close();
    }

    public static void draw(Point p1, Point p2) {
        if (p1.x == p2.x) {
            for (int i = Math.min(p1.y, p2.y); i <= Math.max(p1.y, p2.y); i++) {
                pic[p1.x][i] = '#';
            }
        } else if (p1.y == p2.y) {
            for (int i = Math.min(p1.x, p2.x); i <= Math.max(p1.x, p2.x); i++) {
                pic[i][p1.y] = '#';
            }
        }
    }

    public static boolean fall(int x, int y) { //true if stuck, false if falls into abyss
        if (y == maxY + 3) {
            return false;
        }
        if (pic[x][y + 1] == '.') {
            return fall(x, y + 1);
        } else if (pic[x - 1][y + 1] == '.') {
            return fall(x - 1, y + 1);
        } else if (pic[x + 1][y + 1] == '.') {
            return(fall(x + 1, y + 1));
        } else {
            pic[x][y] = 'o';
            return true;
        }
    }
}
