import java.io.*;
import java.util.*;

public class day7 {

    public static void main(String[] args) throws IOException {
        BufferedReader r = new BufferedReader(new FileReader("input.txt"));
        PrintWriter pw = new PrintWriter(System.out);

        r.readLine(); // "$ cd /"
        Directory root = new Directory("/", null);
        Stack<Directory> navigation = new Stack<>();
        navigation.add(root);

        String s;
        while ((s = r.readLine()) != null) {
            StringTokenizer st = new StringTokenizer(s);

            String s1 = st.nextToken();
            String s2 = st.nextToken();
            if (s1.equals("$")) { //doing something
                if (s2.equals("cd")) { //change directory
                    String s3 = st.nextToken();
                    if (s3.equals("..")) { //go back a level
                        navigation.pop();
                    } else { //go forward a level
                        for (Directory d : navigation.peek().directories) {
                            if (s3.equals(d.name)) {
                                navigation.add(d);
                                break;
                            }
                        }
                    }
                } // if "ls", do nothing
            } else {
                if (s1.equals("dir")) { // is a directory
                    navigation.peek().directories.add(new Directory(s2, navigation.peek()));
                } else { // is a file
                    navigation.peek().files.add(new File(s2, Integer.parseInt(s1)));
                }
            }
        }

        /*--------PART 1--------*/

        int ans = 0;
        ArrayList<Integer> possible = parseThroughAll(root);
        for (int x : possible) {
            if (x <= 100000) {
                ans += x;
            }
        }
        pw.println("Part 1: " + ans);

        /*--------PART 2--------*/

        int totalSize = findSize(root);
        ans = Integer.MAX_VALUE;
        for (int x : possible) {
            if (x >= totalSize - 40000000) {
                ans = Math.min(ans, x);
            }
        }
        pw.println("Part 2: " + ans);

        pw.close();
        r.close();
    }

    public static ArrayList<Integer> parseThroughAll(Directory d) {
        ArrayList<Integer> possible = new ArrayList<>();
        possible.add(findSize(d));
        for (Directory next : d.directories) {
            possible.addAll(parseThroughAll(next));
        }

        return possible;
    }

    public static int findSize(Directory d) {
        int sum = 0;
        for (File f : d.files) {
            sum += f.size;
        }
        for (Directory next : d.directories) {
            sum += findSize(next);
        }
        return sum;
    }

    public static class Directory {
        public String name; public Directory parent;
        public ArrayList<Directory> directories = new ArrayList<>();
        public ArrayList<File> files = new ArrayList<>();

        public Directory(String n, Directory p) {
            name = n; parent = p;
        }
    }

    public static class File {
        public String name; public int size;

        public File(String n, int s) {
            name = n; size = s;
        }
    }
}
