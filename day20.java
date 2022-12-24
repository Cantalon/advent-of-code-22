import java.io.*;
import java.util.ArrayList;

public class day20 {

    static int LENGTH = 0;
    static long KEY = 811589153L;
    static ArrayList<FileInt> file = new ArrayList<>();
    static ArrayList<FileInt> order = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader r = new BufferedReader(new FileReader("input.txt"));
        PrintWriter pw = new PrintWriter(System.out);

        String s;
        while ((s = r.readLine()) != null) {
            int val = Integer.parseInt(s);
            FileInt temp = new FileInt(val, LENGTH);
            file.add(temp); order.add(temp);
            LENGTH++;
        }

        /*--------PART 1--------*/

        for (FileInt f : order) {
            f.mix();
        }

        int zeroIndex = -1;
        for (int i = 0; i < file.size(); i++) {
            if (file.get(i).val == 0) {
                zeroIndex = i;
                break;
            }
        }
        pw.println("Part 1: " + (file.get((zeroIndex + 1000) % LENGTH).val +
                file.get((zeroIndex + 2000) % LENGTH).val +
                file.get((zeroIndex + 3000) % LENGTH).val));

        /*--------PART 2--------*/

        for (int i = 0; i < order.size(); i++) {
            FileInt f = order.get(i);
            f.index = i;
            f.trueVal = f.val * KEY;
            f.val = (int) (((f.trueVal) % (LENGTH - 1) + LENGTH - 1) % (LENGTH - 1)); //modulo magic
            file.set(i, f);
        }

        for (int i = 0; i < 10; i++) {
            for (FileInt f : order) {
                f.mix();
            }
        }

        for (int i = 0; i < file.size(); i++) {
            if (file.get(i).trueVal == 0L) {
                zeroIndex = i;
                break;
            }
        }
        pw.println("Part 2: " + (file.get((zeroIndex + 1000) % LENGTH).trueVal +
                file.get((zeroIndex + 2000) % LENGTH).trueVal +
                file.get((zeroIndex + 3000) % LENGTH).trueVal));

        pw.close();
        r.close();
    }

    static void swap(int a, int b) {
        FileInt temp = file.get(a);
        file.set(a, file.get(b));
        file.set(b, temp);

        file.get(a).index = a;
        file.get(b).index = b;
    }

    static class FileInt {
        public long trueVal = 0L; //for part 2
        public int val;
        public int index; //reduces runtime by eliminating the need for ArrayList indexOf()

        public FileInt(int val, int index) {
            this.val = val;
            this.index = index;
        }

        public void mix() {
            for (int i = 0; i < val; i++) {
                swap(index, (index + 1) % LENGTH);
            }
            for (int i = 0; i > val; i--) {
                swap(index, (index - 1 + LENGTH) % LENGTH);
            }
        }
    }
}
