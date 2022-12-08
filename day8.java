import java.io.*;
import java.util.ArrayList;

public class day8 {

    public static void main(String[] args) throws IOException {
        BufferedReader r = new BufferedReader(new FileReader("input.txt"));
        PrintWriter pw = new PrintWriter(System.out);

        ArrayList<ArrayList<Integer>> arr = new ArrayList<>();
        String s;
        while ((s = r.readLine()) != null) {
            ArrayList<Integer> temp = new ArrayList<>();
            for (int j = 0; j < s.length(); j++) {
                temp.add(Integer.parseInt(s.substring(j, j + 1)));
            }
            arr.add(temp);
        }

        /*--------PART 1--------*/


        boolean[][] visible = new boolean[arr.size()][arr.size()];
        for (int row = 0; row < arr.size(); row++) {
            int min = -1;
            for (int col = 0; col < arr.size(); col++) {
                if (arr.get(row).get(col) > min) {
                    visible[row][col] = true;
                    min = arr.get(row).get(col);
                }
            }
            min = -1;
            for (int col = arr.size() - 1; col >= 0; col--) {
                if (arr.get(row).get(col) > min) {
                    visible[row][col] = true;
                    min = arr.get(row).get(col);
                }
            }
        }
        for (int col = 0; col < arr.size(); col++) {
            int min = -1;
            for (int row = 0; row < arr.size(); row++) {
                if (arr.get(row).get(col) > min) {
                    visible[row][col] = true;
                    min = arr.get(row).get(col);
                }
            }
            min = -1;
            for (int row = arr.size() - 1; row >= 0; row--) {
                if (arr.get(row).get(col) > min) {
                    visible[row][col] = true;
                    min = arr.get(row).get(col);
                }
            }
        }

        int ans = 0;
        for (boolean[] row : visible) {
            for (boolean x : row) {
                ans += x ? 1 : 0;
            }
        }

        pw.println("Part 1: " + ans);

        /*--------PART 2--------*/

        ans = 0;
        for (int row = 1; row < arr.size() - 1; row++) {
            for (int col = 1; col < arr.size() - 1; col++) {
                int a = 0; int b = 0;
                int c = 0; int d = 0;
                for (int i = row + 1; i < arr.size(); i++) {
                    a++;
                    if (arr.get(i).get(col) >= arr.get(row).get(col)) {
                        break;
                    }
                }
                for (int i = col + 1; i < arr.size(); i++) {
                    b++;
                    if (arr.get(row).get(i) >= arr.get(row).get(col)) {
                        break;
                    }
                }
                for (int i = row - 1; i >= 0; i--) {
                    c++; //haha funny
                    if (arr.get(i).get(col) >= arr.get(row).get(col)) {
                        break;
                    }
                }
                for (int i = col - 1; i >= 0; i--) {
                    d++;
                    if (arr.get(row).get(i) >= arr.get(row).get(col)) {
                        break;
                    }
                }
                ans = Math.max(ans, a * b * c * d);
            }
        }

        pw.println("Part 2: " + ans);

        pw.close();
        r.close();
    }
}
