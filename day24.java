import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;

public class day24 {

    static int HEIGHT;
    static int WIDTH;
    static HashSet<Point> borders = new HashSet<>();
    static HashSet<Blizzard> blizzards = new HashSet<>();
    static HashSet<Point> occupied = new HashSet<>();

    static char[] dirs = {'>', 'v', '<', '^'};
    static int[] xs = {1, 0, -1, 0};
    static int[] ys = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        BufferedReader r = new BufferedReader(new FileReader("input.txt"));
        PrintWriter pw = new PrintWriter(System.out);

        ArrayList<String> temp = new ArrayList<>();
        String s;
        while ((s = r.readLine()) != null) {
            temp.add(s);
        }
        HEIGHT = temp.size();
        WIDTH = temp.get(0).length();

        for (int y = 0; y < HEIGHT; y++) {
            borders.add(new Point(0, y));
            borders.add(new Point(WIDTH - 1, y));
        }
        for (int x = 0; x < WIDTH; x++) {
            borders.add(new Point(x, 0));
            borders.add(new Point(x, HEIGHT - 1));
        }
        borders.remove(new Point(1, 0));
        borders.remove(new Point(WIDTH - 2, HEIGHT - 1));
        borders.add(new Point(1, -1));
        borders.add(new Point(WIDTH - 2, HEIGHT));

        for (int row = 1; row < temp.size() - 1; row++) {
            for (int col = 1; col < temp.get(0).length() - 1; col++) {
                for (int i = 0; i < 4; i++) {
                    if (temp.get(row).charAt(col) == dirs[i]) {
                        blizzards.add(new Blizzard(col, row, i));
                    }
                }
            }
        }

        /*--------PART 1--------*/

        HashSet<Point> possible = new HashSet<>();
        possible.add(new Point(1, 0));

        int ans = 0;
        ans += bfs(possible, 0, WIDTH - 2, HEIGHT - 1);
        pw.println("Part 1: " + ans);

        /*--------PART 2--------*/

        possible = new HashSet<>();
        possible.add(new Point(WIDTH - 2, HEIGHT - 1));
        ans += bfs(possible, 0, 1, 0); //back to the start

        possible = new HashSet<>();
        possible.add(new Point(1, 0));
        ans += bfs(possible, 0, WIDTH - 2, HEIGHT - 1); //to the end again
        pw.println("Part 2: " + ans);

        pw.close();
        r.close();
    }

    static int bfs(HashSet<Point> frontier, int time, int x, int y) {
        if (frontier.contains(new Point(x, y))) {
            return time;
        }

        HashSet<Point> nextFront = new HashSet<>();
        for (Blizzard b : blizzards) {
            b.forward();
        }

        for (Point p : frontier) {
            if (!occupied.contains(new Point(p.x, p.y))) {
                nextFront.add(p);
            }
            for (int i = 0; i < 4; i++) {
                if (!borders.contains(new Point(p.x + xs[i], p.y + ys[i])) &&
                        !occupied.contains(new Point(p.x + xs[i], p.y + ys[i]))) {
                    nextFront.add(new Point(p.x + xs[i], p.y + ys[i]));
                }
            }
        }
        occupied = new HashSet<>();
        return bfs(nextFront, time + 1, x, y);
    }

    public static class Blizzard {
        int x;
        int y;
        final int dir;

        public Blizzard(int x, int y, int dir) {
            this.x = x;
            this.y = y;
            this.dir = dir;
        }

        public void forward() {
            x = (x + (WIDTH - 2) + xs[dir]) % (WIDTH - 2);
            x = (x == 0) ? WIDTH - 2 : x;
            y = (y + (HEIGHT - 2) + ys[dir]) % (HEIGHT - 2);
            y = (y == 0) ? HEIGHT - 2 : y;
            occupied.add(new Point(x, y));
        }
    }
}
