import java.io.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.awt.Point;

public class day12 {

    static ArrayList<ArrayList<Integer>> map = new ArrayList<>();
    static ArrayList<ArrayList<Boolean>> visited = new ArrayList<>();
    static ArrayList<ArrayList<Integer>> steps = new ArrayList<>();

    static int[] xs = {1, -1, 0, 0};
    static int[] ys = {0, 0, 1, -1};

    public static void main(String[] args) throws IOException {
        BufferedReader r = new BufferedReader(new FileReader("input.txt"));
        PrintWriter pw = new PrintWriter(System.out);

        Point start = new Point(0, 0);
        Point end = new Point(0, 0);

        String s;
        while ((s = r.readLine()) != null) {
            ArrayList<Integer> row = new ArrayList<>();
            ArrayList<Integer> row2 = new ArrayList<>();
            ArrayList<Boolean> row3 = new ArrayList<>();
            for (int j = 0; j < s.length(); j++) {
                if (s.charAt(j) == 'S') {
                    start.setLocation(map.size(), j);
                    row.add(0);
                } else if (s.charAt(j) == 'E') {
                    end.setLocation(map.size(), j);
                    row.add(25);
                } else {
                    row.add(s.charAt(j) - 'a');
                }
                row2.add(Integer.MAX_VALUE);
                row3.add(false);
            }
            map.add(row);
            steps.add(row2);
            visited.add(row3);
        }

        /*--------PART 1--------*/

        steps.get(start.x).set(start.y, 0);

        ArrayDeque<Point> front = new ArrayDeque<>();
        front.add(start);
        bfs(front, 1);

        pw.println("Part 1: " + steps.get(end.x).get(end.y));

        /*--------PART 2--------*/

        for (ArrayList<Integer> arr : steps) { //resets data
            Collections.fill(arr, Integer.MAX_VALUE);
        } for (ArrayList<Boolean> arr : visited) {
            Collections.fill(arr, false);
        }

        front.clear(); front.add(end);
        steps.get(end.x).set(end.y, 0);
        bfs(front, 2);

        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < map.size(); i++) {
            for (int j = 0; j < map.get(0).size(); j++) {
                if (map.get(i).get(j) == 0) {
                    ans = Math.min(ans, steps.get(i).get(j));
                }
            }
        }
        pw.println("Part 2: " + ans);

        pw.close();
        r.close();
    }

    public static void bfs(ArrayDeque<Point> frontier, int part) {
        while (frontier.size() > 0) {
            Point p = frontier.remove();

            if (!visited.get(p.x).get(p.y)) {
                visited.get(p.x).set(p.y, true);

                for (int dir = 0; dir < 4; dir++) {
                    int newX = p.x + xs[dir];
                    int newY = p.y + ys[dir];
                    try {
                        if ((map.get(newX).get(newY) - map.get(p.x).get(p.y) <= 1 && part == 1) ||
                            (map.get(newX).get(newY) - map.get(p.x).get(p.y) >= -1 && part == 2)) {

                            frontier.add(new Point(newX, newY));
                            steps.get(newX).set(newY, Math.min(steps.get(newX).get(newY),
                                                               steps.get(p.x).get(p.y) + 1));
                        }
                    } catch (Exception ignored) {}
                }
            }
        }
    }
}
