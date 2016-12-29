import java.util.LinkedList;

/**
 * Square represents a single square of a Sudoku-board.
 * A Square knows which row, column, and box it belongs to,
 * as well as which Square is the next one in a list of Squares
 * that have mutable values.
 */

public class Square {

  private Square next;
  private char value;
  private Sequence[] sequences;

  /**
   * Initialized Square with value.
   * Sequence[] sequences holds three sequence-objects representing the square's row, column, and box.
   * Square needs to know which sequences it belongs to to be able to know which values are valid.
   * sequences[0] = row
   * sequences[1] = column
   * sequences[2] = box
   */
  public Square(char value) {
    this.value     = value;
    this.sequences = new Sequence[3];
  }

  /**
   * This backtracking-method is responsible for recursively solving the sudoku-puzzle.
   * Each square goes through a String of all valid values, character by character,
   * and calls recursiveSolve for the Square-object associated with its next-member.
   */
  public void recursiveSolve() {
    char tempValue = value;
    String values  = validValues();

    // Square is blank and there are valid values to insert.
    if (value == 'x' && values.length() > 0) {
      for (int i = 0; i < values.length(); i++) {
        value = values.charAt(i);
        if (next != null) {
          next.recursiveSolve();
        } else {
          // Solution found.
          SudokuSolver.printCurrentBoard();
          return;
        }
      }

    // Square is not blank and there are no more squares after this one.
    // This signifies that a solution has been found.
    } else if (value != 'x' && next == null) {
      SudokuSolver.printCurrentBoard();
      return;

    // There are valid values to insert and square is not the last one.
    } else if (values.length() > 0 && next != null) {
      next.recursiveSolve();

    // No solutions, puzzle is corrupted/not valid.
    } else if (values.length() <= 0 && next == null) {
      System.out.println("Error: Puzzle is not valid and therefore not solveable.");
      return;
    }

    value = tempValue;
  }

  /**
   * Builds and returns a String containing all currently available values this Square can obtain.
   *
   * @return      a String-object containing all currently available values for current Square.
   */
  public String validValues() {
    String result = "";
    String activeValues = activeValues();
    String allValues = Board.getValidValues();

    // If one of the values are not present in row, column, or box, add to result string.
    for (int i = 0; i < allValues.length(); i++) {
      if (activeValues.indexOf(allValues.charAt(i)) == -1) {
        result += allValues.charAt(i);
      }
    }

    return result;
  }

  /**
   * Builds and returns a string of all values currently active in
   * the square's row, column and box, except for it's own value.
   *
   * @return  all values currently active in the square's sequences, except it's own value.
   */
  public String activeValues() {
    String result = "";
    for (int i = 0; i < sequences.length; i++) {
      result += sequences[i].activeValuesExceptFor(this);
    }
    return result;
  }

  public void setNext(Square square) {
    this.next = square;
  }

  public void setRow(Sequence row) {
    this.sequences[0] = row;
  }

  public void setColumn(Sequence column) {
    this.sequences[1] = column;
  }

  public void setBox(Sequence box) {
    this.sequences[2] = box;
  }

  /**
   * Returns a the object's currently associated value.
   *
   * @return        the object's currently associated value.
   */
  public char getValue() {
    return value;
  }

  /**
   * Sets the value for Square object.
   *
   * @param value value to set for Square.
   */
  public void setValue(char value) {
    this.value = value;
  }
}
