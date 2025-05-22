import java.util.*;

public class MineSweeper {
    private int row;
    private int col;
    private int mines;
    private char[][] board; // Board to display
    private boolean[][] revealed; // Track revealed cells
    private boolean[][] mineLocations; // Track mine locations
    private boolean isGameOver;
    private boolean isFirstPrint = true;

    public static final int MAX_MINES_PERECENTAGE = 35; // Maximum mines percentage allowed

    // Constructor
    public MineSweeper(int row, int col, int mines) {
        this.row = row;
        this.col = col;
        this.mines = mines;
        this.board = new char[row][col];
        this.revealed = new boolean[row][col];
        this.mineLocations = new boolean[row][col];
    }

    public void initializeBoard() {
        // Initialize the board with underscores
        for (int i = 0; i < row; i++) {
            Arrays.fill(board[i], '_');
        }
    }

    public void plantMines(int numMines) {
        // Randomly place mines on the board
        Random rand = new Random();
        int minesPlaced = 0;

        while (minesPlaced < numMines) {
            int mineRow = rand.nextInt(row);
            int mineCol = rand.nextInt(col);

            if (!mineLocations[mineRow][mineCol]) { 
                mineLocations[mineRow][mineCol] = true; // Place a mine
                minesPlaced++;
            }
        }
    }

    public int countAdjacentMines(int r, int c) {
        // Update the numbers around the mine
        int count = 0;
        for (int i= r - 1; i <= r + 1; i++) {
            for (int j = c - 1; j <= c + 1; j++) {
                if (i >= 0 && i < row && j >= 0 && j < col && mineLocations[i][j]) {
                    count++;
                }
            }
        }
        return count;
    }

    public void updateBoard() {
        // Update the board with mine counts
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (mineLocations[i][j]) {
                    board[i][j] = '*'; // Mark mine
                } else {
                    int count = countAdjacentMines(i, j);
                    board[i][j] = (char) ('0' + count); // Convert to char
                }
            }
        }
    }

    public void revealCell(int r, int c) {
        if (r < 0 || r >= row || c < 0 || c >= col || revealed[r][c]) {
            return; // Out of bounds or already revealed
        }
        
        revealed[r][c] = true;

        if (mineLocations[r][c]) {
            isGameOver = true; // Hit a mine
            System.out.println("Oh no, you detonated a mine! Game over.");
            return;
        }

        
        // If the cell is empty (0), reveal adjacent cells until a number is found
        if (board[r][c] == '0') {
            for (int i = r - 1; i <= r + 1; i++) {
                for (int j = c - 1; j <= c + 1; j++) {
                    if (i != r || j != c) {
                        revealCell(i, j);
                    }
                }
            }
        }
    }
    
    public char indexToAlphabet(int index) {
        // Convert index to alphabet
        return (char) ('A' + index);
    }
    
    public int alphabetToIndex(char alphabet) {
        // Convert alphabet to index
        return alphabet - 'A';
    }
    
    public void printBoard(boolean showAll) {
        // Print the board
        if (isFirstPrint) {
            System.out.println("\nHere is your minefield:");
            isFirstPrint = false;
        } else {
            System.out.println("\nHere is your updated minefield:");
        }
        System.out.print("  ");
        for (int j = 0; j < col; j++) System.out.print(j + " ");
        System.out.println();
        for (int i = 0; i < row; i++) {
            System.out.print(indexToAlphabet(i) + " ");
            for (int j = 0; j < col; j++) {
                if (showAll || revealed[i][j]) {
                    System.out.print(board[i][j] + " ");
                } else {
                    System.out.print("_ ");
                }
            }
            System.out.println();
        }
    }

    public boolean isWin() {
        // Check if all non-mine cells are revealed
        int noMineCells = row * col - mines;
        int revealedCells = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (revealed[i][j]) {
                    revealedCells++;
                }
            }
        }
        return revealedCells == noMineCells;
    }

    public void startGame() {
        // Start the game
        Scanner scanner = new Scanner(System.in);
        initializeBoard();
        plantMines(mines);
        updateBoard();

        while (!isGameOver) {
            printBoard(false);
            System.out.print("Select a square to reveal (e.g. A1): ");
            String input = scanner.nextLine();
            if (input.length() < 2) {
                System.out.println("Invalid input.");
                continue;
            }
            char r = Character.toUpperCase(input.charAt(0));
            String colStr = input.substring(1);
            int c;
            int rowIndex = alphabetToIndex(r);
            try {
                c = Integer.parseInt(colStr);
            } catch (Exception e) {
                System.out.println("Invalid input.");
                continue;
            }
            if (rowIndex < 0 || rowIndex >= row || c < 0 || c >= col) {
                System.out.println("Coordinates out of bounds.");
                continue;
            }
            revealCell(rowIndex, c);

            // Show adjacent mine count after revealing
            if (!isGameOver) {
                System.out.println("This square contains " + board[alphabetToIndex(r)][c] + " adjacent mines.");
                
                if (isWin()) {
                    printBoard(true);
                    System.out.println("Congratulations, you have won the game!");
                    break;
                }
            }

            
        }
        scanner.close();
    }

    // For testing purposes
    public void setMineAt(int r, int c, boolean hasMine) {
        mineLocations[r][c] = hasMine;
    }

    // For testing purposes
    public boolean isGameOver() {
        return isGameOver;
    }

    // For testing purposes
    public char[][] getBoard() {
        return board;
    }

    public static void main(String[] args) {
        System.out.println("Welcome to Minesweeper!");
        // Prompt user for grid size and number of mines
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Enter the size of the grid (e.g. 4 for a 4x4 grid):");
            int grid = scanner.nextInt();
            int mines = 0;
            while (true){
                System.out.println("Enter the number of mines to place on the grid (maximum is 35% of the total squares):");
                mines = scanner.nextInt();
                if (grid * grid * MAX_MINES_PERECENTAGE/100 < mines) {
                    System.out.println("Too many mines! Please try again.");
                }
                else {
                    break;
                }
            }
            MineSweeper game = new MineSweeper(grid, grid, mines);
            game.startGame();
        } finally {
            scanner.close();
        }
    }
}
