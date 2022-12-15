import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.awt.Point;

public class day15 {

    static ArrayList<Point> sensors = new ArrayList<>();
    static ArrayList<Integer> minDist = new ArrayList<>();
    static ArrayList<Point> beacons = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader r = new BufferedReader(new FileReader("input.txt"));
        PrintWriter pw = new PrintWriter(System.out);

        int minX = Integer.MAX_VALUE;
        int maxX = 0;
        int maxDist = 0; // only for determining search bounds

        String s;
        while ((s = r.readLine()) != null) {
            StringTokenizer st = new StringTokenizer(s);
            st.nextToken(); st.nextToken(); //sensor at

            String t = st.nextToken();
            int x = Integer.parseInt(t.substring(2, t.length() - 1));
            t = st.nextToken();
            int y = Integer.parseInt(t.substring(2, t.length() - 1));
            minX = Math.min(minX, x); maxX = Math.max(maxX, x);
            sensors.add(new Point(x, y));

            st.nextToken(); st.nextToken(); //closest beacon
            st.nextToken(); st.nextToken(); //is at
            t = st.nextToken();
            x = Integer.parseInt(t.substring(2, t.length() - 1));
            t = st.nextToken();
            y = Integer.parseInt(t.substring(2));
            minX = Math.min(minX, x); maxX = Math.max(maxX, x);
            beacons.add(new Point(x, y));
        }

        for (int i = 0; i < sensors.size(); i++) {
            minDist.add(dist(sensors.get(i), beacons.get(i)));
            maxDist = Math.max(maxDist, minDist.get(i));
        }

        /*--------PART 1--------*/

        long ans = 0;
        for (int x = minX - maxDist - 1; x <= maxX + maxDist + 1; x++) {
            Point p = new Point(x, 2000000);
            if (beacons.contains(p)) { //position p contains a beacon
                continue;
            }
            for (int i = 0; i < sensors.size(); i++) {
                if (dist(sensors.get(i), p) <= minDist.get(i)) {
                    ans++;
                    break;
                }
            }
        }
        pw.println("Part 1: " + ans);

        /*--------PART 2--------*/

        ans = 0;
        outer:
        for (int i = 0; i < sensors.size(); i++) { //for each sensor,
            Point sen = sensors.get(i);
            int dis = minDist.get(i) + 1;
            for (int change = 0; change < dis; change++) { //searching the 4 diagonals
                if (check(sen.x + change, sen.y + dis - change)) {
                    ans = (sen.x + change) * 4000000L +  sen.y + dis - change;
                    break outer;
                } if (check(sen.x + dis - change, sen.y - change)) {
                    ans = (sen.x + dis - change) * 4000000L + sen.y - change;
                    break outer;
                } if (check(sen.x - change, sen.y - dis + change)) {
                    ans = (sen.x - change) * 4000000L + sen.y - dis + change;
                    break outer;
                } if (check(sen.x - dis + change, sen.y + change)) {
                    ans = (sen.x - dis + change) * 4000000L + sen.y + change;
                    break outer;
                }
            }
        }

        pw.println("Part 2: " + ans);

        pw.close();
        r.close();
    }

    public static boolean check(int x, int y) { //sees if the point is good
        if (x > 4000000 || y > 4000000 || x < 0 || y < 0) {
            return false;
        }
        for (int i = 0; i < 30; i++) {
            if (dist(sensors.get(i), new Point(x, y)) <= minDist.get(i)) {
                return false;
            }
        }
        return true;
    }

    public static int dist(Point a, Point b) {
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }
}
