# Sudoku
A simple terminal-based sudoku-solver written in Java, using a backtracking, recursive algorithm.

```
File format example:

3
3
8x5x1x6x3
3xxxxxxx8
x2x3x8x9x
xx12x43xx
x6xxxxx5x
xx35x17xx
x4x6x9x3x
2xxxxxxx6
6x7x3x9x4

The two numbers above the main grid represents the number of subgrids (boxes) horizontally and vertically in the puzzle. This example shows a standard sudoku-puzzle of 9x9 squares and 3x3 boxes. An 'x'-character represents an empty square.

Usage: 
java SudokuSolver \<FILENAME>
```
