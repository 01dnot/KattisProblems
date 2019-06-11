import java.util.HashMap;

public class BingItOn {

    public static void main(String[] args) {
        Kattio io = new Kattio(System.in, System.out);
        int nWords = io.getInt();
        HashMap<String, Integer> subWordCount = new HashMap<>(nWords * 32); // MAX SUBWORDS
        for (int i = 0; i < nWords; i++) {
            String word = io.getWord();
            int searchHits = subWordCount.containsKey(word) ? subWordCount.get(word) : 0;
            io.println(searchHits);
            for (int j = 1; j <= word.length(); j++) {
                String subString = word.substring(0, j);
                int newHits = subWordCount.containsKey(subString) ? subWordCount.get(subString) + 1 : 1;
                subWordCount.put(subString, newHits);
            }
        }
        io.close();
    }
}
