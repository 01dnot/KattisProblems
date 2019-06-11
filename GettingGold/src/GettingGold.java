
public class GettingGold {

    private static int WIDTH;
    private static int HEIGHT;
    private static int SIZE;


    public static void main(String[] args) {
        Kattio io = new Kattio(System.in, System.out);

        WIDTH = io.getInt();
        HEIGHT = io.getInt();
        SIZE = WIDTH * HEIGHT;
        boolean[] marked = new boolean[SIZE];
        int pPos = 0;
        String[] maze = new String[SIZE];

        String mazeStr = "";
        for (int i = 0; i < HEIGHT; i++) {
            mazeStr += io.getWord();
        }

        for (int i = 0; i < SIZE; i++) {
            String field = "" + mazeStr.charAt(i);
            if (field.equals("P")) {
                pPos = i;
            }
            maze[i] = field;
        }
        dfs(pPos, marked, maze);

        int goldCount = 0;
        for (int i = 0; i < maze.length; i++) {
            if (maze[i].equals("G") && marked[i]) {
                goldCount++;
            }
        }
        io.print(goldCount);
        io.close();
    }

    private static void dfs(int pos, boolean[] marked, String[] maze) {
        if (marked[pos]) return;
        marked[pos] = true;
        if (canSmellDraft(pos, maze)) return;
        int right = pos + 1;
        int left = pos - 1;
        int down = pos + WIDTH;
        int up = pos - WIDTH;

        if (up > 0 && !maze[up].equals("#")) {
            dfs(up, marked, maze);
        }
        if (down < SIZE && !maze[down].equals("#")) {
            dfs(down, marked, maze);

        }
        if (left % WIDTH != WIDTH - 1 && !maze[left].equals("#")) {
            dfs(left, marked, maze);

        }
        if (right % WIDTH != 0 && !maze[right].equals("#")) {
            dfs(right, marked, maze);

        }

    }

    private static boolean canSmellDraft(int p, String[] maze) {
        int right = p + 1;
        int left = p - 1;
        int down = p + WIDTH;
        int up = p - WIDTH;
        if (up > 0 && maze[up].equals("T")) {
            return true;
        }
        if (down < SIZE && maze[down].equals("T")) {
            return true;
        }
        if (left % WIDTH != WIDTH - 1 && maze[left].equals("T")) {
            return true;
        }
        return right % WIDTH != 0 && maze[right].equals("T");
    }


}
