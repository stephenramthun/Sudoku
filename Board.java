import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Board is the class responsible for keeping track of all the squares, rows, columns, and boxes of
 * the Sudoku-board. It also keeps track of which values are valid for the squares based
 * on the dimensions of the board. A board that is 9x9 would have values ranging from 1 to 9, while a
 * board that is 6x6 would have values ranging from 1 to 6.
 *
 * A separate ArrayList of squares that may have their values altered is also kept. Some values on the
 * board are predetermined and should not be changed.
 */

public class Board {

  private final char EMPTY_VALUE = 'x';
  private static String validValues;

  private int width;
  private int height;
  private int size;

  private Sequence[] rows;
  private Sequence[] columns;
  private Sequence[] boxes;
  private Square[][] squares;

  private ArrayList<Square> mutableSquares;

  /**
   * Initialize the Board with all its rows, columns, and boxes.
   *
   * @param width  number of squares horizontally on board.
   * @param height number of squares vertically on board.
   */
  public Board(int width, int height) {
    this.width  = width;
    this.height = height;
    this.size   = width * height;

    rows    = new Sequence[size];
    columns = new Sequence[size];
    boxes   = new Sequence[size];

    // Init Sequences
    for (int i = 0; i < size; i++) {
      rows[i]    = new Sequence(size);
      columns[i] = new Sequence(size);
      boxes[i]   = new Sequence(size);
    }

    Board.setValidValues(size);
    System.out.printf("Valid values: %s\n", Board.getValidValues());
  }

  /**
   * Start solving the board. The first of the mutable Squares gets its "recursiveSolve"-method called,
   * which is then responsible for calling "recursiveSolve" for all the other squares in the puzzle.
   */
  public void solve() {
    if (mutableSquares.get(0) == null) {
      System.out.println("Error, Board has not been properly initialized.");
      return;
    }

    mutableSquares.get(0).recursiveSolve();
  }

  /**
   * Utility method that prints the current board to terminal.
   */
  public void printCurrentBoard() {
    for (int x = 0; x < size; x++) {
      for (int y = 0; y < size; y++) {
        System.out.printf("%c ", squares[x][y].getValue());

        if ((y + 1) % width == 0) {
          System.out.print(" ");
        }
      }
      System.out.println();

      if ((x + 1) % height == 0) {
        System.out.println();
      }
    }
  }

  /**
   * Initializes the board with values and empty spaces where appropriate.
   *
   * @param characters array containing characters on sudoku board from file
   */
  public void setupBoard(Square[][] squares) {
    this.squares = squares;

    // Rows and Columns
    for (int x = 0; x < size; x++) {
      for (int y = 0; y < size; y++) {
        rows[x].setSquare(squares[x][y]);
        squares[x][y].setRow(rows[x]);
        columns[y].setSquare(squares[x][y]);
        squares[x][y].setColumn(columns[y]);
      }
    }

    // Boxes
    int rowPadding = 0;
    int colPadding = 0;
    int index = 0;

    for (int i = 0; i < size; i++) {  // For each box
      for (int x = rowPadding; x < height + rowPadding; x++) { // For each row
        for (int y = colPadding; y < width + colPadding; y++) { // For each column
          boxes[i].setSquare(squares[x][y]);
          squares[x][y].setBox(boxes[i]);
          index++;
        }
      }

      colPadding += width;
      index = 0;

      if (colPadding >= size) {
        colPadding = 0;
        rowPadding += height;
      }
    }

    setupMutableSquares();
  }

  /**
   * Initializes the ArrayList containing the mutable squares of the Sudoku-board.
   * This should not be called in the initializer of the class, as there is no guarantee
   * that the squares of the board has been initialized at that point in time. Calling of this
   * method should only happen when it's reasonably certain that all squares have been initialized.
   */
  private void setupMutableSquares() {
    if (this.squares == null) {
      System.out.println("Error: Board has not been properly initialized.");
      return;
    }

    mutableSquares = new ArrayList<Square>();

    for (int x = 0; x < size; x++) {
      for (int y = 0; y < size; y++) {
        if (squares[x][y].getValue() == EMPTY_VALUE) {
          mutableSquares.add(squares[x][y]);
        }
      }
    }

    // Let each Square know which Square comes after it in the list.
    for (int i = 0; i < mutableSquares.size() - 1; i++) {
      mutableSquares.get(i).setNext(mutableSquares.get(i + 1));
    }
  }

  /**
   * Returns the number of squares in width/height of board.
   * All sudoku-boards are assumed to be square, so width and height are always equal.
   *
   * @return        number of squares in width/height of board.
   */
  public int getSize() {
    return size;
  }

  /**
   * Checks the sequences of the puzzle to see if there are any inconsistencies.
   * Should only be used once after loading puzzle from file.
   *
   * @return    true if valid, false if not.
   */
  public boolean puzzleValidity() {
    for (int i = 0; i < size; i++) {
      if (sequenceContainsDuplicates(rows[i]))    return false;
      if (sequenceContainsDuplicates(columns[i])) return false;
      if (sequenceContainsDuplicates(boxes[i]))   return false;
    }
    return true;
  }

  /**
   * Checks if a given Sequence contains any duplicate values.
   *
   * @param sequence Sequence to check for duplicate values.
   * @return         true if sequence contains duplicate values, false if otherwise.
   */
  private boolean sequenceContainsDuplicates(Sequence sequence) {
    String values    = sequence.activeValues();
    String allValues = Board.getValidValues();

    for (int a = 0; a < allValues.length(); a++) {
      int count = 0;
      for (int b = 0; b < values.length(); b++) {
        char comp = allValues.charAt(a);
        if (comp == values.charAt(b)) {
          count++;
        }
      }

      if (count > 1) {
        return true;
      }
    }

    return false;
  }

  /**
   * Determines all valid values for board based on its size.
   *
   * @param size number of squares either direction in the board.
   */
  public static void setValidValues(int size) {
    String allValues = "123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    validValues = allValues.substring(0, size);
  }

  /**
   * Returns a String with all valid values for the board.
   *
   * @return      all valid values for the board.
   */
  public static String getValidValues() {
    return validValues == null ? "" : validValues;
  }
}
