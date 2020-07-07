package PercolationStats;

/**
 * Percolation
 */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int[][] grid;
    private final WeightedQuickUnionUF wuUf;
    private final int size;
    private int countOpen = 0;
    private int topParent = -2;
    private int bottomParent = -2;
    private boolean topParentOn = false;
    private boolean bottomParentOn = false;


    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("Invalid constructor argument");
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
    public void open(int rowX, int colX) {
        if (rowX < 1 || rowX > size && colX < 1 || colX > size) throw new IllegalArgumentException("Invalid row or col argument");
        int row = rowX-1;
        int col = colX-1;
        if (!isOpen(rowX, colX)) {
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
            
            if (topParentOn && row == 0) {
                wuUf.union(grid[row][col], topParent); 
            }

            if (bottomParentOn && row == size-1) {
                wuUf.union(grid[row][col], bottomParent); 
            }
            
            
            // checks whether the surrounding sites are open, if they are, merge them into one component -> connected = TRUE
            if (isValidRowCol(rowX-1, colX) && isOpen(rowX-1, colX)) {
                wuUf.union(grid[row][col], grid[row-1][col]);
            }
            if (isValidRowCol(rowX, colX+1) && isOpen(rowX, colX+1)) {
                wuUf.union(grid[row][col], grid[row][col+1]);
            }
            if (isValidRowCol(rowX+1, colX) && isOpen(rowX+1, colX)) {
                wuUf.union(grid[row][col], grid[row+1][col]);
            }
            if (isValidRowCol(rowX, colX-1) && isOpen(rowX, colX-1)) {
                wuUf.union(grid[row][col], grid[row][col-1]);
            }
        }
        // else StdOut.print(String.format("Site (%d %d) is already open",row,col));
    }

    // does the neighbouring site even exist within the n x n grid?
    

    // is the site (row, col) open?
    public boolean isOpen(int rowX, int colX) {
        if (rowX < 1 || rowX > size && colX < 1 || colX > size) throw new IllegalArgumentException("Invalid row or col argument");
        int row = rowX-1;
        int col = colX-1;
        return grid[row][col] != -1;
    }

    // is the site (row, col) full?
    public boolean isFull(int rowX, int colX) {
        if (rowX < 1 || rowX > size && colX < 1 || colX > size) throw new IllegalArgumentException("Invalid row or col argument");      
        int row = rowX-1;
        int col = colX-1;
        if (topParentOn) return wuUf.find(grid[row][col]) == wuUf.find(topParent);
        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return countOpen;
    }
    
    private boolean isValidRowCol(int rowX, int colX) {
        int row = rowX-1;
        int col = colX-1;
        return row >= 0 && row < size && col >= 0 && col < size;
    }

    // does the system percolate?
    // create a virtual parent of the top and the bottom rows
    public boolean percolates() {
        if (!topParentOn || !bottomParentOn) return false;
        return wuUf.find(topParent) == wuUf.find(bottomParent);
    }

    public static void main(String[] args) {
        Percolation dedo = new Percolation(2);
        dedo.open(0, 3);
        dedo.open(2, 1);
        dedo.open(2, 2);
        dedo.open(2, 3);
        dedo.open(3, 3);
        dedo.open(3, 1);
        System.out.println(dedo.percolates() + ", " + dedo.isOpen(1, 3));
    }
}

