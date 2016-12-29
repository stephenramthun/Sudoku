
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.IOException;

/**
 *  SudokuSolver is a small program designed to solve Sudoku-boards of arbitrary sizes.
 *  It assumes that the board it is trying to solve has only one single solution, and that
 *  it also is square in shape.
 *
 *  It uses a backtracking, recursive algorithm that keeps track of all possible valid values
 *  for each square based on allready present values in a square's row, column, and box.
 *
 *  Data format example:
 *  3
 *  3
 *  8x5x1x6x3
 *  3xxxxxxx8
 *  x2x3x8x9x
 *  xx12x43xx
 *  x6xxxxx5x
 *  xx35x17xx
 *  x4x6x9x3x
 *  2xxxxxxx6
 *  6x7x3x9x4
 *
 *  File consists of a series of numbers, x'es, and newlines.
 *  An 'x'-character denotes an empty space. Number of characters per line
 *  and number of rows are always equal.
 *
 *  The two numbers above the grid represents number of boxes
 *  vertically and horizontally on the board.
 */

public class SudokuSolver {

  private static Board board;

  /**
   * Creates a Board-object from a text-file.
   *
   * @param args user should supply a filename to a file containing a sudoku-board to solve.
   *             usage: java SudokuSolver <FILENAME>
   */
  public static void main(String[] args) {
    if (args.length != 1) {
      System.out.println("Error. Usage: java Sudokusolver <FILENAME>\n");
    }

    board = readFile(args[0]);

    if (board == null) {
      System.out.println("Terminating program.");
      return;
    }

    System.out.println("Trying to solve follwing board: ");
    board.printCurrentBoard();

    if (board.puzzleValidity() == false) {
      System.out.println("Error: puzzle is invalid/corrupt.");
      return;
    }

    System.out.printf("\nSolution:\n");
    board.solve();
  }


  /**
   * Reads a text-file containing a Sudoku puzzle.
   *
   * @param fileName name of file to read
   * @return         on success returns a Board based on read file
   *                 returns null on failure
   */
  private static Board readFile(String fileName) {
    Board board;

    try {
      int width  = 0;
      int height = 0;
      int size;

      FileInputStream   fis = new FileInputStream(new File(fileName));
      InputStreamReader isr = new InputStreamReader(fis);

      width  = isr.read() - 48; isr.read();  // Set width and consume newline from stream.
      height = isr.read() - 48; isr.read();  // Set height and consume newline from stream.
      board = new Board(width, height);
      size = board.getSize();

      // Read all characters from file
      Square[][] squares = new Square[size][size];
      for (int x = 0; x < size; x++) {
        for (int y = 0; y < size; y++) {
          squares[x][y] = new Square((char)isr.read());
        }
        isr.read(); // Consume newline
      }

      board.setupBoard(squares);
      fis.close();
      isr.close();

    } catch(FileNotFoundException e) {
      System.out.println("Error: File not found.");
      return null;
    } catch(IOException e) {
      System.out.println("Error: Could not read from file.");
      return null;
    }

    return board;
  }

  /**
   * Utility-method for printing the board with current values.
   */
  public static void printCurrentBoard() {
    board.printCurrentBoard();
  }
}
