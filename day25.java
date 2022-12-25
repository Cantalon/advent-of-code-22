import java.io.*;

public class day25 {
    public static void main(String[] args) throws IOException {
        BufferedReader r = new BufferedReader(new FileReader("input.txt"));
        PrintWriter pw = new PrintWriter(System.out);

        /*--------PART 1--------*/

        long sum = 0;
        String s;
        while ((s = r.readLine()) != null) {
            sum += toInteger(s);
        }
        pw.println("Part 1: " + toSnafu(sum));

        /*--------PART 2--------*/
        
        pw.println("Part 2: N/A");

        pw.close();
        r.close();
    }

    public static long toInteger(String s) {
        long amt = 0;
        long pow = 1;
        for (int i = s.length() - 1; i >= 0; i--) {
            if (s.charAt(i) == '=') {
                amt += pow * -2L;
            } else if (s.charAt(i) == '-') {
                amt += pow * -1L;
            } else {
                amt += pow * Long.parseLong(s.substring(i, i + 1));
            }
            pow *= 5;
        }
        return amt;
    }

    public static String toSnafu(long l) {
        String rev = "";
        while (l != 0L) {
            if (l % 5 == 3) {
                rev += '='; 
                l += 2;
            } else if (l % 5 == 4) {
                rev += '-';
                l += 1;
            } else {
                rev += (l % 5); 
                l -= (l % 5);
            }
            l /= 5;
        }
        String ans = "";
        for (int i = rev.length() - 1; i >= 0; i--) {
            ans += rev.charAt(i);
        }
        return ans;
    }
}
