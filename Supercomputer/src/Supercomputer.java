public class Supercomputer {

    private static int offset;
    private static int[] BITS;

    public static void main(String[] args) {
        Kattio io = new Kattio(System.in, System.out);
        int bits = io.getInt();
        int queries = io.getInt();

        int nBitArr = 2;
        offset = 2;
        while (2 * bits + 3 > nBitArr)
            nBitArr *= 2;
        while (bits + 2 > offset)
            offset *= 2;
        offset += 1;

        BITS = new int[nBitArr];
        for (int i = 0; i < nBitArr; i++) {
            BITS[i] = 0;
        }

        for (int query = 0; query < queries; query++) {
            String cmd = io.getWord();
            if (cmd.equals("F")) {
                int index = io.getInt();
                flipBit(index);
            } else if (cmd.equals("C")) {
                int from = io.getInt();
                int to = io.getInt();
                io.println(count(from, to));
            }
        }

        io.close();

    }

    public static void flipBit(int index) {
        int treeIndex = index + offset;
        BITS[treeIndex] = BITS[treeIndex] == 1 ? 0 : 1;
        while (treeIndex > 1) {
            treeIndex = treeIndex / 2;
            BITS[treeIndex] = BITS[treeIndex * 2] + BITS[treeIndex * 2 + 1];
        }
    }

    public static int count(int start, int stop) {
        int sum = 0;
        start += offset - 1;
        stop += offset + 1;
        while (true) {
            boolean leftManGoesRight = start % 2 == 0;
            boolean rightManGoesLeft = stop % 2 == 1;
            start = start / 2;
            stop = stop / 2;
            if (start == stop)
                break;
            if (leftManGoesRight) {
                sum += BITS[start * 2 + 1];
            }
            if (rightManGoesLeft) {
                sum += BITS[stop * 2];
            }
        }
        return sum;
    }

}
