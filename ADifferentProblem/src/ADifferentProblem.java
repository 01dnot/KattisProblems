import java.util.Scanner;

public class ADifferentProblem {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()) {
            long s = in.nextLong();
            long b = in.nextLong();
            System.out.println(Math.abs(s - b));
            in.nextLine();
        }
    }
}
