import java.math.BigInteger;

public class IHateTheNumberNine {


    public static void main(String[] args) {
        Kattio io = new Kattio(System.in, System.out);

        final BigInteger eight = BigInteger.valueOf(8);
        final BigInteger nine = BigInteger.valueOf(9);
        final BigInteger MODULO = BigInteger.valueOf(1000000007);

        final int NUMBERS = io.getInt();
        for (int i = 0; i < NUMBERS; i++) {
            long digits = io.getLong();
            if (digits < 2) {
                io.println(8);
                continue;
            }
            BigInteger powOfNine = BigInteger.valueOf(digits - 1);
            io.println(eight.multiply(nine.modPow(powOfNine, MODULO)).mod(MODULO)); //First can only be 1-8 rest 0-8
        }
        io.close();
    }
}
