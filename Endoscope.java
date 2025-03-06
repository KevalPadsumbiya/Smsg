import java.util.*;

//https://www.hackerrank.com/contests/target-samsung-13-nov19/challenges/endoscope/problem
enum PipeType {
    ZERO(0),
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7);

    private final int value;
    PipeType(int value) {
        this.value = value;
    }

    public static PipeType fromValue(int value) throws Exception {
        for (PipeType pt : values()) {
            if (pt.value == value)
                return pt;
        }
        throw new Exception("Invalid pipe type: " + value);
    }
}

enum Movement {
    TOP(new Move(-1, 0)),
    LEFT(new Move(0, -1)),
    RIGHT(new Move(0, 1)),
    BOTTOM(new Move(1, 0));

    private final Move move;
    Movement(Move move) {
        this.move = move;
    }
    public Move getMove() {
        return move;
    }
}

class Move {
    int x, y;
    public Move(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
}

class Node {
    int row, col, time;
    public Node(int row, int col, int time) {
        this.row = row;
        this.col = col;
        this.time = time;
    }
}

class Queue<T> {
    int r = 0;
    int f = 0;
    int capacity = 1001*1001;
    int size = 0;
    T[] q;

    public Queue() {
        this.q = (T[]) new Object[capacity];
    }

    public Queue(int capacity) {
        this.q = (T[]) new Object[capacity];
    }

    public void add(T value) {
        this.q[f] = value;
        f = (f + 1) % capacity;
        size++;
    }

    public T poll() {
        T val = q[r];
        r++;
        size--;
        return val;
    }

    public boolean isEmpty() {
        return size == 0;
    }
}

public class Endoscope {
    static Movement[] getPossibleMovements(PipeType type) {
        switch (type) {
            case ONE:
                return new Movement[]{Movement.TOP, Movement.LEFT, Movement.RIGHT, Movement.BOTTOM};
            case TWO:
                return new Movement[]{Movement.TOP, Movement.BOTTOM};
            case THREE:
                return new Movement[]{Movement.LEFT, Movement.RIGHT};
            case FOUR:
                return new Movement[]{Movement.TOP, Movement.RIGHT};
            case FIVE:
                return new Movement[]{Movement.RIGHT, Movement.BOTTOM};
            case SIX:
                return new Movement[]{Movement.LEFT, Movement.BOTTOM};
            case SEVEN:
                return new Movement[]{Movement.TOP, Movement.LEFT};
            default:
                return new Movement[]{};
        }
    }

    public static boolean isConnected(PipeType pipe, Movement movement) {
        for (Movement mv : getPossibleMovements(pipe)) {
            if (mv == movement)
                return true;
        }
        return false;
    }

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        StringBuilder sb = new StringBuilder();

        while (t-- > 0) {
            int n = sc.nextInt();
            int m = sc.nextInt();
            int r = sc.nextInt();
            int c = sc.nextInt();
            int L = sc.nextInt();

            int[][] grid = new int[n][m];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    grid[i][j] = sc.nextInt();
                }
            }

            // Distance array to record the minimum time to reach each cell.
            int[][] dist = new int[n][m];
            for (int i = 0; i < n; i++) {
                Arrays.fill(dist[i], Integer.MAX_VALUE);
            }

            Queue<Node> queue = new Queue<>();
            // Start only if starting cell is valid.
            if (grid[r][c] != 0) {
                dist[r][c] = 1;
                queue.add(new Node(r, c, 1));
            }

            while (!queue.isEmpty()) {
                Node cur = queue.poll();
                int row = cur.row, col = cur.col, time = cur.time;
                if (time >= L)
                    continue; // No further moves allowed if reached L

                PipeType curPipe = PipeType.fromValue(grid[row][col]);
                for (Movement move : getPossibleMovements(curPipe)) {
                    int newRow = row + move.getMove().getX();
                    int newCol = col + move.getMove().getY();
                    int newTime = time + 1;

                    // Check boundaries and if destination cell has a valid pipe.
                    if (newRow < 0 || newRow >= n || newCol < 0 || newCol >= m || grid[newRow][newCol] == 0)
                        continue;

                    PipeType nextPipe = PipeType.fromValue(grid[newRow][newCol]);
                    boolean connected = false;
                    if (move == Movement.TOP && isConnected(nextPipe, Movement.BOTTOM))
                        connected = true;
                    else if (move == Movement.BOTTOM && isConnected(nextPipe, Movement.TOP))
                        connected = true;
                    else if (move == Movement.LEFT && isConnected(nextPipe, Movement.RIGHT))
                        connected = true;
                    else if (move == Movement.RIGHT && isConnected(nextPipe, Movement.LEFT))
                        connected = true;
                    if (!connected)
                        continue;

                    // If a faster route is found, update and add to the queue.
                    if (newTime < dist[newRow][newCol]) {
                        dist[newRow][newCol] = newTime;
                        queue.add(new Node(newRow, newCol, newTime));
                    }
                }
            }

            int count = 0;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    if (dist[i][j] != Integer.MAX_VALUE && dist[i][j] <= L)
                        count++;
                }
            }
            sb.append(count).append("\n");
        }
        System.out.print(sb.toString());
        sc.close();
    }
}
