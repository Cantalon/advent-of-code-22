import java.io.*;
import java.util.Collections;
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.ArrayDeque;

public class day11 {

    public static ArrayList<ArrayDeque<Long>> monkeys = new ArrayList<>();
    public static ArrayList<Character> operations = new ArrayList<>();
    public static ArrayList<Integer> amounts = new ArrayList<>();
    public static ArrayList<Integer> tests = new ArrayList<>();
    public static ArrayList<Integer> trues = new ArrayList<>();
    public static ArrayList<Integer> falses = new ArrayList<>();

    public static long modulo = 1;
    public static ArrayList<Long> counters = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader r = new BufferedReader(new FileReader("input.txt"));
        PrintWriter pw = new PrintWriter(System.out);

        String s;
        while ((s = r.readLine()) != null) {
            if (s.length() == 0) {
                continue;
            }
            StringTokenizer st = new StringTokenizer(s); //Monkey []
            st.nextToken(); String monkey = st.nextToken();
            int index = Integer.parseInt(monkey.substring(0, monkey.length() - 1));
            monkeys.add(new ArrayDeque<>());

            st = new StringTokenizer(r.readLine()); // Starting items: []
            st.nextToken(); st.nextToken();
            while (st.hasMoreTokens()) {
                String temp = st.nextToken();
                monkeys.get(index).add(Long.parseLong(temp.substring(0, 2)));
            }

            st = new StringTokenizer(r.readLine()); //Operation: new = old []
            st.nextToken(); st.nextToken(); st.nextToken(); st.nextToken();
            operations.add(st.nextToken().charAt(0));
            String temp = st.nextToken();
            if (temp.equals("old")) {
                amounts.add(-1);
            } else {
                amounts.add(Integer.parseInt(temp));
            }

            st = new StringTokenizer(r.readLine()); //Test: divisible by []
            st.nextToken(); st.nextToken(); st.nextToken();
            tests.add(Integer.parseInt(st.nextToken()));
            modulo *= tests.get(tests.size() - 1);

            st = new StringTokenizer(r.readLine()); //If true: throw to monkey []
            st.nextToken(); st.nextToken(); st.nextToken(); st.nextToken(); st.nextToken();
            trues.add(Integer.parseInt(st.nextToken()));

            st = new StringTokenizer(r.readLine()); // If false: throw to monkey []
            st.nextToken(); st.nextToken(); st.nextToken(); st.nextToken(); st.nextToken();
            falses.add(Integer.parseInt(st.nextToken()));
            counters.add(0L);
        }

        COPY_DATA copy = new COPY_DATA();

        /*--------PART 1--------*/

        for (int turn = 1; turn <= 20; turn++) {
            for (int monkey = 0; monkey < monkeys.size(); monkey++) {
                while (monkeys.get(monkey).size() != 0) { 
                    long item = monkeys.get(monkey).pop(); //inspecting
                    counters.set(monkey, counters.get(monkey) + 1);
                    item = operate(item, operations.get(monkey), amounts.get(monkey));
                    item /= 3;
                    
                    if (item % tests.get(monkey) == 0) { //passing to the next monkey
                        monkeys.get(trues.get(monkey)).add(item);
                    } else {
                        monkeys.get(falses.get(monkey)).add(item);
                    }
                }
            }
        }

        Collections.sort(counters);
        pw.println("Part 1: " + (counters.get(counters.size() - 1) * counters.get(counters.size() - 2)));

        /*--------PART 2--------*/

        copy.reset();

        for (int turn = 1; turn <= 10000; turn++) {
            for (int monkey = 0; monkey < monkeys.size(); monkey++) {
                while (monkeys.get(monkey).size() != 0) {
                    long item = monkeys.get(monkey).pop() % modulo; //inspecting
                    counters.set(monkey, counters.get(monkey) + 1);
                    item = operate(item, operations.get(monkey), amounts.get(monkey));
                    
                    if (item % tests.get(monkey) == 0) { //passing to the next monkey
                        monkeys.get(trues.get(monkey)).add(item);
                    } else {
                        monkeys.get(falses.get(monkey)).add(item);
                    }
                }
            }
        }

        Collections.sort(counters);
        pw.println("Part 2: " + (counters.get(counters.size() - 1) * counters.get(counters.size() - 2)));

        pw.close();
        r.close();
    }

    public static long operate(long item, char operation, int amount) {
        if (amount == -1 && operation == '*') {
            return item * item;
        } else if (operation == '*') {
            return item * amount;
        } else { //operation = '+'
            return item + amount;
        }
    }


    public static class COPY_DATA { //just to store and reset data for part 2
        public ArrayList<ArrayDeque<Long>> mks = new ArrayList<>();
        public ArrayList<Character> ops;
        public ArrayList<Integer> amts;
        public ArrayList<Integer> ts;
        public ArrayList<Integer> t;
        public ArrayList<Integer> f;
        public ArrayList<Long> crs;

        public COPY_DATA() {
            for (ArrayDeque<Long> monkey : monkeys) {
                ArrayDeque<Long> temp = new ArrayDeque<>(monkey);
                mks.add(temp);
            }
            ops = new ArrayList<>(operations);
            amts = new ArrayList<>(amounts);
            ts = new ArrayList<>(tests);
            t = new ArrayList<>(trues);
            f = new ArrayList<>(falses);
            crs = new ArrayList<>(counters);
        }

        public void reset() {
            monkeys = new ArrayList<>(); monkeys.addAll(mks);
            operations = new ArrayList<>(); operations.addAll(ops);
            amounts = new ArrayList<>(); amounts.addAll(amts);
            tests = new ArrayList<>(); tests.addAll(ts);
            trues = new ArrayList<>(); trues.addAll(t);
            falses = new ArrayList<>(); falses.addAll(f);
            counters = new ArrayList<>(); counters.addAll(crs);
        }
    }
}
