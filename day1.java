import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class day1 {

    public static void main(String[] args) throws IOException {
        BufferedReader r = new BufferedReader(new FileReader("input.txt"));
        PrintWriter pw = new PrintWriter(System.out);

        ArrayList<Integer> arr = new ArrayList<>();

        String s;
        int running = 0;
        while ((s = r.readLine()) != null) {
            if (s.length() == 0) {
                arr.add(running);
                running = 0;
            } else {
                running += Integer.parseInt(s);
            }
        }

        Collections.sort(arr);

        pw.println("Part 1: " + arr.get(arr.size() - 1));
        pw.println("Part 2: " + (arr.get(arr.size() - 1) + arr.get(arr.size() - 2) + arr.get(arr.size() - 3)));
        
        pw.close();
        r.close();
    }
}
