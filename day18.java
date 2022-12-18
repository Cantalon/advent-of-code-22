import java.io.*;
import java.util.HashSet;
import java.util.Objects;

public class day18 {
    static HashSet<Cube> droplet = new HashSet<>();
    static HashSet<Cube> removed = new HashSet<>();
    static HashSet<Cube> cube = new HashSet<>();

    public static void main(String[] args) throws IOException {
        BufferedReader r = new BufferedReader(new FileReader("input.txt"));
        PrintWriter pw = new PrintWriter(System.out);

        for (int i = 0; i < 2159; i++) {
            String[] temp = r.readLine().split(",");
            droplet.add(new Cube(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]), Integer.parseInt(temp[2])));
        } //reading finished

        /*--------PART 1--------*/

        int ans = 6 * droplet.size();
        for (Cube c1 : droplet) {
            for (Cube c2 : droplet) {
                if (c1.equals(c2)) {
                    continue;
                }
                //if c2 is adjacent to c1, then one of c1's faces disappears
                c1.x--; ans -= c1.equals(c2) ? 1 : 0; c1.x++;
                c1.x++; ans -= c1.equals(c2) ? 1 : 0; c1.x--;
                c1.y--; ans -= c1.equals(c2) ? 1 : 0; c1.y++;
                c1.y++; ans -= c1.equals(c2) ? 1 : 0; c1.y--;
                c1.z--; ans -= c1.equals(c2) ? 1 : 0; c1.z++;
                c1.z++; ans -= c1.equals(c2) ? 1 : 0; c1.z--;
            }
        }
        pw.println("Part 1: " + ans);

        /*--------PART 2--------*/

        for (int a = -1; a <= 20; a++) {
            for (int b = -1; b <= 20; b++) {
                //bounding the box for floodfill
                removed.add(new Cube(a, b, -2));
                removed.add(new Cube(a, b, 21));
                removed.add(new Cube(a, -2, b));
                removed.add(new Cube(a, 21, b));
                removed.add(new Cube(-2, a, b));
                removed.add(new Cube(21, a, b));
            }
        }

        for (int x = -1; x < 20; x++) {
            for (int y = -1; y <= 20; y++) {
                for (int z = -1; z < 20; z++) {
                    cube.add(new Cube(x, y, z));
                }
            }
        }

        ans = 0;
        for (int x = -1; x <= 20; x++) {
            for (int y = -1; y <= 20; y++) {
                floodfill(new Cube(x, y, -1));
                floodfill(new Cube(x, y, 21));
                floodfill(new Cube(x, -1, y));
                floodfill(new Cube(x, 20, y));
                floodfill(new Cube(-1, x, y));
                floodfill(new Cube(20, x, y));
            }
        }

        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                for (int k = 0; k < 20; k++) {
                    Cube c = new Cube(i, j, k);
                    if (cube.contains(c)) {
                        c.x++; ans += cube.contains(c) ? 0 : 1; c.x--;
                        c.x--; ans += cube.contains(c) ? 0 : 1; c.x++;
                        c.y++; ans += cube.contains(c) ? 0 : 1; c.y--;
                        c.y--; ans += cube.contains(c) ? 0 : 1; c.y++;
                        c.z++; ans += cube.contains(c) ? 0 : 1; c.z--;
                        c.z--; ans += cube.contains(c) ? 0 : 1; c.z++;
                    }
                }
            }
        }
        pw.println("Part 2: " + ans);

        pw.close();
        r.close();
    }

    public static void floodfill(Cube c) {
        if (removed.contains(c) || droplet.contains(c)) {
            return;
        }
        cube.remove(c);
        removed.add(c);

        floodfill(new Cube(c.x, c.y, c.z + 1));
        floodfill(new Cube(c.x, c.y, c.z - 1));
        floodfill(new Cube(c.x, c.y + 1, c.z));
        floodfill(new Cube(c.x, c.y - 1, c.z));
        floodfill(new Cube(c.x + 1, c.y, c.z));
        floodfill(new Cube(c.x - 1, c.y, c.z));
    }

    public static class Cube {
        public int x;
        public int y;
        public int z;

        public Cube(int a, int b, int c) {
            x = a;
            y = b;
            z = c;
        }

        @Override //random stuff that's apparently necessary
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Cube cube = (Cube) o;
            return x == cube.x && y == cube.y && z == cube.z;
        }

        @Override //random stuff that's apparently necessary
        public int hashCode() {
            return Objects.hash(x, y, z);
        }
    }
}
