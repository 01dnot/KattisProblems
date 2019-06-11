public class DistributingBallotBoxes {

    private static class Town implements Comparable<Town> {
        double population;
        int boxes;
        double currentPopOnBox;

        public Town(double population) {
            this.population = population;
            this.boxes = 1;
            currentPopOnBox = population / boxes;
        }

        public void addBox() {
            currentPopOnBox = population / ++boxes;
        }

        @Override
        public int compareTo(Town other) {
            return Double.compare(other.currentPopOnBox, this.currentPopOnBox);
        }
    }

    private static class MyPQ {
        Town[] towns;
        int size;

        public MyPQ(int fixedSize) {
            towns = new Town[fixedSize + 1];
            size = 0;
        }

        public void add(Town t) {
            towns[++size] = t;
            swim(size);
        }

        public Town getMax() {
            return towns[1];
        }

        public void sink(int i) {
            while (2 * i <= size) {
                int j = 2 * i;
                if (j < size && towns[j].compareTo(towns[j + 1]) > 0)
                    j++;
                if (!(towns[i].compareTo(towns[j]) > 0))
                    break;
                Town t = towns[j];
                towns[j] = towns[i];
                towns[i] = t;
                i = j;
            }
        }

        public void swim(int i) {
            while ((i > 1) && towns[i / 2].compareTo(towns[i]) > 0) {
                Town t = towns[i / 2];
                towns[i / 2] = towns[i];
                towns[i] = t;
                i /= 2;
            }
        }
    }

    public static void main(String[] args) {
        Kattio io = new Kattio(System.in, System.out);
        int N = io.getInt(), B = io.getInt();

        while (N != -1 && B != -1) {
            MyPQ myPQ = new MyPQ(N);
            for (int i = 0; i < N; i++) {
                myPQ.add(new Town(io.getDouble()));
            }
            B -= N;
            while (B > 0) {
                myPQ.getMax().addBox();
                myPQ.sink(1);
                B--;
            }

            System.out.printf("%.0f\n", Math.ceil((myPQ.getMax().population / myPQ.getMax().boxes)));

            N = io.getInt();
            B = io.getInt();
        }
        io.close();
    }
}