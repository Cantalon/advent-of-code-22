import java.io.*;
import java.awt.Point;
import java.util.HashMap;
import java.util.HashSet;

public class day23 {
    static HashMap<Point, Integer> proposals = new HashMap<>();
    static HashSet<Point> occupied = new HashSet<>();
    static HashSet<Elf> elves = new HashSet<>();

    //{north, south, west, east}
    static final int[][] xs = {{-1, 0, 1}, {-1, 0, 1}, {-1, -1, -1}, {1, 1, 1}};
    static final int[][] ys = {{-1, -1, -1}, {1, 1, 1}, {-1, 0, 1}, {-1, 0, 1}};

    static int stopped = 0;
    static int turn = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader r = new BufferedReader(new FileReader("input.txt"));
        PrintWriter pw = new PrintWriter(System.out);

        String s;
        int y = 0;
        while ((s = r.readLine()) != null) {
            y++;
            for (int x = 0; x < s.length(); x++) {
                if (s.charAt(x) == '#') {
                    elves.add(new Elf(x, y));
                }
            }
        }

        /*--------PART 1--------*/

        int rounds = 0;
        for (int i = 0; i < 10; i++) {
            rounds++;
            stopped = 0;
            simulateRound();
        }

        int[] rect = {Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE};
        for (Elf e : elves) {
            rect[0] = Math.min(rect[0], e.x); rect[1] = Math.max(rect[1], e.x);
            rect[2] = Math.min(rect[2], e.y); rect[3] = Math.max(rect[3], e.y);
        }
        pw.println("Part 1: " + ((rect[1] - rect[0] + 1) * (rect[3] - rect[2] + 1) - elves.size()));

        /*--------PART 2--------*/

        while (stopped != elves.size()) {
            rounds++;
            stopped = 0;
            simulateRound();
        }
        pw.println("Part 2: " + rounds);

        pw.close();
        r.close();
    }

    public static void simulateRound() {
        proposals.clear();
        occupied.clear();
        for (Elf e : elves) {
            occupied.add(new Point(e.x, e.y));
        }
        for (Elf e : elves) {
            e.setProposal();
        }
        for (Elf e : elves) {
            e.move();
        }
        turn++;
    }

    public static class Elf {
        public int x;
        public int y;
        Point proposal = new Point();

        public Elf(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public void setProposal() {
            boolean[] containsElf = new boolean[4];
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 3; j++) {
                    containsElf[i] = containsElf[i] || occupied.contains(new Point(x + xs[i][j], y + ys[i][j]));
                }
            }
            if (!containsElf[0] && !containsElf[1] && !containsElf[2] && !containsElf[3]) {
                stopped++;
                proposal = null;
                return; //no elves nearby, do nothing
            }
            for (int i = turn; i < turn + 4; i++) {
                if (!containsElf[i % 4]) {
                    proposal = new Point(x + xs[i % 4][1], y + ys[i % 4][1]);
                    proposals.put(proposal, proposals.getOrDefault(proposal, 0) + 1);
                    return;
                }
            }
            proposal = null;
        }

        public void move() {
            if (proposal == null) {
                return;
            }
            if (proposals.get(proposal) == 1) {
                x = proposal.x;
                y = proposal.y;
            }
        }
    }
}
