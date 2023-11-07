import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

// A program that attempts to solve a game using Genetic Algorithms
public class GeneticAlgorithmGameSolver {

    // Main method
    public static void main(String[] args) throws FileNotFoundException {
        // Declare a 2D array to represent the game board, and initialize it by reading data from the file specified in the command line arguments
        int board[][] = getBoard(args);

        // Initialize the current position (pos) of the player and the score
        int[] pos = {0, 0};
        int score = 0;

        // Extract initial player position coordinates
        int x = pos[0];
        int y = pos[1];

        char di; // Declare a variable to store the current direction (L, R, U, or D)

        // Loop through the instructions provided as command line arguments
        for (int i = 0; i < args[1].length(); i++) {
            di = args[1].charAt(i); // Get the current direction

            if (score >= 0) { // Ensure that the score is not negative (player has not lost)

                // Check the direction and calculate the new position (x, y)
                if (di == 'L' || di == 'l') {
                    y = pos[1] - 1;
                } else if (di == 'R' || di == 'r') {
                    y = pos[1] + 1;
                } else if (di == 'U' || di == 'u') {
                    x = pos[0] - 1;
                } else if (di == 'D' || di == 'd') {
                    x = pos[0] + 1;
                }

                int[] newp = {x, y}; // Create an array to represent the new position

                // Check if the new position is out of bounds or invalid
                if (y > board[0].length - 1 || y < 0 || x > board.length || x < 0) {
                    y = 0;
                    x = 0;
                    score = -1; // Set the score to -1 to indicate an invalid move
                } else if (board[newp[0]][newp[1]] == 0) {
                    y = 0;
                    x = 0;
                    score = -1; // Set the score to -1 to indicate an invalid move
                } else {
                    if (board[pos[0]][pos[1]] > board[newp[0]][newp[1]]) {
                        // If the move is valid, update the game board
                        board[newp[0]][newp[1]] += board[pos[0]][pos[1]];
                        board[pos[0]][pos[1]] = 0;
                        pos[0] = newp[0];
                        pos[1] = newp[1];
                    } else {
                        board[pos[0]][pos[1]] = -1; // Set the current cell to -1 to indicate an invalid move
                    }

                    print(board); // Print the current state of the game board
                    score = board[pos[0]][pos[1]]; // Update the score
                }
            }
        }

        if (score == -1) {
            System.out.println("You have lost because you tried to make an invalid move. The score is " + score);
        } else {
            System.out.println("The Score is " + score);
        }
    }

    // Function to read the game board from a file specified in the command line arguments
    public static int[][] getBoard(String[] args) throws FileNotFoundException {
        File file = new File(args[0]); // Create a File object for the game board file
        Scanner scan = new Scanner(file); // Create a scanner to read the file

        int x = 0; // Initialize variables to keep track of board dimensions
        int y = 0;
        String hold = "";

        // Loop through the file to determine the number of rows (x)
        while (scan.hasNextLine()) {
            hold = scan.nextLine();
            x++;
        }

        // Close the previous scanner
        scan.close();

        // Create a new scanner for reading the file again
        Scanner scan2 = new Scanner(file);

        String[] counts = hold.split(", "); // Split the last line of the file to determine the number of columns (y)
        y = counts.length;

        // Create a 2D array to represent the game board
        int[][] multiples = new int[x][y];

        // Loop through the file again to populate the game board
        for (int i = 0; i < x; i++) {
            String holds = scan2.nextLine(); // Read a line from the file
            String put[] = holds.split(", "); // Split the line into individual values

            for (int j = 0; j < y; j++) {
                multiples[i][j] = Integer.parseInt(put[j]); // Parse each value as an integer and store it in the board
            }
        }

        print(multiples); // Print the initial state of the game board
        return multiples; // Return the populated game board
    }

    // Function to print the current state of the game board
    public static void print(int multiples[][]) {
        // Loop through the rows and columns of the board and print each value
        for (int i = 0; i < multiples.length; i++) {
            for (int j = 0; j < multiples[0].length; j++) {
                System.out.print(multiples[i][j] + ",");
            }
            System.out.println(""); // Move to the next row
        }
    }
}