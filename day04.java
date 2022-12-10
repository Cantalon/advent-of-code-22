import java.io.*;
import java.util.ArrayList;

public class day4 {

    public static void main(String[] args) throws IOException {
        BufferedReader r = new BufferedReader(new FileReader("input.txt"));
        PrintWriter pw = new PrintWriter(System.out);

        ArrayList<ArrayList<Integer>> arr = new ArrayList<>();

        String s;
        while ((s = r.readLine()) != null) {
            ArrayList<Integer> temp = new ArrayList<>();
            String[] ranges = s.split(",");
            for (String range : ranges) {
                String[] nums = range.split("-");
                for (String num : nums) {
                    temp.add(Integer.parseInt(num));
                }
            } 
            arr.add(temp);
            //there is probably a better way to read the input
        }

        /*--------PART 1--------*/

        int ans = 0;
        for (ArrayList<Integer> elf : arr) {
            if ((elf.get(0) >= elf.get(2) && elf.get(1) <= elf.get(3)) ||
                    (elf.get(0) <= elf.get(2) && elf.get(1) >= elf.get(3))) {
                ans++;
            }
        }

        pw.println("Part 1: " + ans);

        /*--------PART 2--------*/

        ans = 0;
        for (ArrayList<Integer> elf : arr) {
            if (Math.min(elf.get(1), elf.get(3)) >= Math.max(elf.get(0), elf.get(2))) {
                ans++;
            }
        }

        pw.println("Part 2: " + ans);

        pw.close();
        r.close();
    }
}
