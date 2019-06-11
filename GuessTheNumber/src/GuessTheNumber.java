import java.util.Scanner;

public class GuessTheNumber {

    public static Scanner console = new Scanner(System.in);
    public static int count = 0;

    public static void main(String[] args) {
        guess(0, 1000);
    }

    public static void guess(int low, int high) {
        if (count == 10) return;
        int mid = low + (high - low) / 2;
        System.out.println(mid);
        count++;
        String answer = console.nextLine();
        if (answer.equals("lower")) guess(low, mid);
        else if (answer.equals("higher")) guess(mid + 1, high);
    }

}
