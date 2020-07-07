import java.util.LinkedList;
import java.util.List;


public class Board {

    private final int n;
    private final int[][] grid;
    private int blankRow;
    private int blankCol;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        if (tiles == null) {
            throw new NullPointerException();
        }
        
        n = tiles[0].length;
        grid = copyOf(tiles);

        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (grid[row][col] == 0) {
                    blankRow = row;
                    blankCol = col;
                    return;
                }
            }
        }
    }
                                           
    // string representation of this board
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(n).append("\n");
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                builder.append(String.format("%2d ", grid[row][col]));
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        int counter = 0;
        
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                if (grid[i][j] != grid.length*i + j + 1 && grid[i][j] != 0) {
                    counter++;
                } 
            } 
        }
        return counter;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int counter = 0;
        
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                if (grid[i][j] != 0) {
                    // difference between where it is and where it should be
                    counter += Math.abs(i - (grid[i][j] - 1) / n) + Math.abs(j - (grid[i][j] - 1) % n);
                }
            } 
        }
        return counter;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this)
            return true;
        if (y == null)
            return false;
        if (this.getClass() != y.getClass())
            return false;
        Board that = (Board) y;
        if (this.blankCol != that.blankCol)
            return false;
        if (this.blankRow != that.blankRow)
            return false;
        if (this.n != that.n)
            return false;
        for (int row = 0; row < n; row++)
            for (int col = 0; col < n; col++)
                if (this.grid[row][col] != that.grid[row][col])
                    return false;
        return true;
    }

    
    // all neighboring boards
    public Iterable<Board> neighbors() {
        List<Board> neighbors = new LinkedList<>();

        if (blankRow > 0) {
            int[][] north = copyOf(grid);
            swap(north, blankRow, blankCol, blankRow - 1, blankCol);
            neighbors.add(new Board(north));
        }
        if (blankRow < n - 1) {
            int[][] south = copyOf(grid);
            swap(south, blankRow, blankCol, blankRow + 1, blankCol);
            neighbors.add(new Board(south));
        }
        if (blankCol > 0) {
            int[][] west = copyOf(grid);
            swap(west, blankRow, blankCol, blankRow, blankCol - 1);
            neighbors.add(new Board(west));
        }
        if (blankCol < n - 1) {
            int[][] east = copyOf(grid);
            swap(east, blankRow, blankCol, blankRow, blankCol + 1);
            neighbors.add(new Board(east));
        }
        return neighbors;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] cloned = copyOf(grid);
        if (blankRow != 0) {
            swap(cloned, 0, 0, 0, 1);
        } else {
            swap(cloned, 1, 0, 1, 1);
        }
        return new Board(cloned);
    }

    private void swap(int[][] v, int rowA, int colA, int rowB, int colB) {
        int swap = v[rowA][colA];
        v[rowA][colA] = v[rowB][colB];
        v[rowB][colB] = swap;
    }

    private int[][] copyOf(int[][] matrix) {
        int[][] clone = new int[matrix.length][];
        for (int row = 0; row < matrix.length; row++) {
            clone[row] = matrix[row].clone();
        }
        return clone;
    }

    // unit testing (not graded)
    public static void main(String[] args) {        
        
        int[][] tiles = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};

        // int[][] tiles = {{8, 1, 3},{4, 0, 2},{7, 6, 5}};

        Board b = new Board(tiles);
        System.out.println(b.toString());

        System.out.println("Hamming: " + b.hamming());
        System.out.println("Manhattan: " + b.manhattan());

        System.out.println("Possible movements to: ");
        for (Board x : (List<Board>) b.neighbors()) {
            System.out.println(x.toString());
        }

    }

}