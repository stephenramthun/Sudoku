import java.util.LinkedList;

/**
 * A Sequence represents either a row, column, or box of squares in a sudoku-puzzle.
 * It is responsible for knowing which values are currently unclaimed among its squares
 * at any given time.
 */
public class Sequence {

  private LinkedList<Square> squares;

  public Sequence(int size) {
    squares = new LinkedList<Square>();
  }

  /**
   * Adds given Square to Sequence.
   *
   * @param square  square to be placed in Sequence.
   */
  public void setSquare(Square square) {
    squares.add(square);
  }

  /**
   * Builds a String of all values currently claimed by all squares in the sequence except for one.
   *
   * @param square the square which value should be excluded from the String
   * @return       a string with all the values currently claimed by the squares in the sequence.
   */
  public String activeValuesExceptFor(Square square) {
    String activeValues = "";

    for (int i = 0; i < squares.size(); i++) {
      if (squares.get(i) != square) {
        activeValues += squares.get(i).getValue();
      }
    }

    return activeValues;
  }

  /**
   * Builds a String of all values currently claimed by all squares in the sequence.
   *
   * @return      a string with all the values currently claimed by the squares in the sequence.
   */
  public String activeValues() {
    String activeValues = "";

    for (int i = 0; i < squares.size(); i++) {
      activeValues += squares.get(i).getValue();
    }

    return activeValues;
  }
}
