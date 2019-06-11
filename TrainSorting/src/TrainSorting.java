public class TrainSorting {

    public static void main(String[] args) {
        Kattio io = new Kattio(System.in, System.out);
        int N = io.getInt();
        int[] trains = new int[N];
        int[] trainsReverse = new int[N];

        for (int i = 0; i < N; i++) {
            trains[i] = (io.getInt());
        }
        //Reversing trains to be able to find the startValue of the sequence
        int teller = 0;
        for (int i = trains.length - 1; i >= 0; i--) {
            trainsReverse[teller++] = trains[i];
        }

        int[] longestIncSeq = longestIncSeq(trainsReverse);
        int[] longestDeqSeq = longestDeqSeq(trainsReverse);

        int max = 0;
        for (int i = 0; i < trains.length; i++) {
            int longestInc = longestIncSeq[i];
            int longestDeq = longestDeqSeq[i];
            int length = longestDeq + longestInc - 1;
            if (length > max) max = length;
        }
        io.print(max);
        io.close();

    }

    private static int[] longestIncSeq(int[] trains) {
        int[] longestIncSeq = new int[trains.length];
        for (int i = 0; i < trains.length; i++) {
            longestIncSeq[i] = 1;
        }
        for (int i = 1; i < trains.length; i++) {
            for (int j = 0; j < i; j++) {
                if (trains[j] < trains[i] && longestIncSeq[j] + 1 > longestIncSeq[i]) {
                    longestIncSeq[i] = longestIncSeq[j] + 1;
                }
            }
        }
        return longestIncSeq;
    }

    private static int[] longestDeqSeq(int[] trains) {
        int[] longestDecSeq = new int[trains.length];
        for (int i = 0; i < trains.length; i++) {
            longestDecSeq[i] = 1;
        }
        for (int i = 1; i < trains.length; i++) {
            for (int j = 0; j < i; j++) {
                if (trains[j] > trains[i] && longestDecSeq[j] + 1 > longestDecSeq[i]) {
                    longestDecSeq[i] = longestDecSeq[j] + 1;
                }
            }
        }
        return longestDecSeq;
    }

}