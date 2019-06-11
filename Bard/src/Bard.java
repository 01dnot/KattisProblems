import java.util.ArrayList;
import java.util.HashSet;

public class Bard {

    public static void main(String args[]) {
        int songs = 0;
        Kattio io = new Kattio(System.in, System.out);
        int nPersons = io.getInt();
        int evenings = io.getInt();
        ArrayList<HashSet<Integer>> members = new ArrayList<>();
        for (int n = 0; n < nPersons; n++) {
            members.add(new HashSet<>());
        }
        int[][] eveningArr = new int[evenings][];

        for (int i = 0; i < evenings; i++) {
            int present = io.getInt();
            eveningArr[i] = new int[present];
            for (int j = 0; j < present; j++) {
                eveningArr[i][j] = io.getInt();
            }
        }

        for (int eve = 0; eve < evenings; eve++) {
            if (eveningContainsBard(eveningArr, eve)) {
                songs++;
                for (int persons : eveningArr[eve]) {
                    members.get(persons - 1).add(songs);
                }
            } else {
                HashSet<Integer> singalongSet = new HashSet<>();
                for (int person : eveningArr[eve]) {
                    singalongSet.addAll(members.get(person - 1));
                }
                for (int person : eveningArr[eve]) {
                    members.set(person - 1, (HashSet<Integer>) singalongSet.clone());
                }
            }
        }
        for (int i = 0; i < members.size(); i++) { //Print members who knows all songs
            if (members.get(i).size() == songs) {
                System.out.println(i + 1);
            }
        }
    }

    private static boolean eveningContainsBard(int[][] eveningArray, int evening) {
        for (int i = 0; i < eveningArray[evening].length; i++) {
            if (eveningArray[evening][i] == 1) {
                return true;
            }
        }
        return false;
    }
}
