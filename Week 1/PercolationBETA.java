
/**
 * Percolation
 */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class PercolationBETA {
    private int[][] grid;
    private WeightedQuickUnionUF wuUf;
    private int size;
    private int countOpen = 0;
    private int topParent = -2;
    private int bottomParent = -2;
    private boolean topParentOn = false;
    private boolean bottomParentOn = false;


    // creates n-by-n grid, with all sites initially blocked
    public PercolationBETA(int n){
        this.size = n; 
        grid = new int[n][n];
        wuUf = new WeightedQuickUnionUF((int) Math.pow(n, 2));
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = -1;
            }
        } 
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (!isOpen(row, col)) {
            grid[row][col] = size*row + col;
            countOpen++;
            
            if (!topParentOn && row == 0) {
                topParentOn = true;
                topParent = grid[row][col];
            }
            
            if (!bottomParentOn && row == size-1) {
                bottomParentOn = true;
                bottomParent = grid[row][col];
            }
            
            if (topParentOn && row == 0) {wuUf.union(grid[row][col], topParent);}
            if (bottomParentOn && row == size-1) {wuUf.union(grid[row][col], bottomParent);}
            
            

            // checks whether the surrounding sites are open, if they are, merge them into one component -> connected = TRUE
            if (isValidRowCol(row-1, col) && isOpen(row-1, col)){
                wuUf.union(grid[row][col],grid[row-1][col]);
            }
            if (isValidRowCol(row, col+1) && isOpen(row, col+1)){
                wuUf.union(grid[row][col],grid[row][col+1]);
            }
            if (isValidRowCol(row+1, col) && isOpen(row+1, col)){
                wuUf.union(grid[row][col],grid[row+1][col]);
            }
            if (isValidRowCol(row, col-1) && isOpen(row, col-1)){
                wuUf.union(grid[row][col],grid[row][col-1]);
            }
        }
        // else StdOut.print(String.format("Site (%d %d) is already open",row,col));
    }

    // does the neighbouring site even exist within the n x n grid?
    public boolean isValidRowCol(int row, int col) {
        return row>=0 && row<size && col>=0 && col<size;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return grid[row][col] != -1;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        return wuUf.find(grid[row][col]) == wuUf.find(topParent);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return countOpen;
    }

    // does the system percolate?
    // create a virtual parent of the top and the bottom rows
    public boolean percolates() {
        if (!topParentOn || !bottomParentOn) return false;
        return wuUf.find(topParent) == wuUf.find(bottomParent);
    }

    public static void main(String[] args) {
        // Dedo dedo = new Dedo(3);
        // dedo.open(0, 2);
        // dedo.open(1, 0);
        // dedo.open(1, 1);
        // dedo.open(1, 2);
        // dedo.open(2, 2);
        // dedo.open(2, 0);
        // System.out.println(dedo.percolates());
    }
}

