package com.comp301.a09akari.model;

public class PuzzleImpl implements Puzzle {
  private final CellType[][] cellTypes;
  private final int[][] clues;

  public PuzzleImpl(int[][] board) {
    cellTypes = new CellType[board.length][board[0].length];
    clues = new int[board.length][board[0].length];

    for (int r = 0; r < board.length; r++) {
      for (int c = 0; c < board[r].length; c++) {
        switch (board[r][c]) {
          case 0:
          case 1:
          case 2:
          case 3:
          case 4:
            cellTypes[r][c] = CellType.CLUE;
            clues[r][c] = board[r][c];
            break;
          case 5:
            cellTypes[r][c] = CellType.WALL;
            clues[r][c] = -1;
            break;
          case 6:
            cellTypes[r][c] = CellType.CORRIDOR;
            clues[r][c] = -1;
            break;
          default:
            throw new IllegalArgumentException("Invalid cell value: " + board[r][c]);
        }
      }
    }
  }

  @Override
  public int getWidth() {
    return cellTypes[0].length;
  }

  @Override
  public int getHeight() {
    return cellTypes.length;
  }

  @Override
  public CellType getCellType(int r, int c) {
    if (r < 0 || r >= getHeight() || c < 0 || c >= getWidth()) {
      throw new IndexOutOfBoundsException("Cell position out of bounds");
    }
    return cellTypes[r][c];
  }

  @Override
  public int getClue(int r, int c) {
    if (r < 0 || r >= getHeight() || c < 0 || c >= getWidth()) {
      throw new IndexOutOfBoundsException("Cell position out of bounds");
    }
    if (cellTypes[r][c] != CellType.CLUE) {
      throw new IllegalArgumentException("Cell at (" + r + ", " + c + ") is not of type CLUE");
    }
    return clues[r][c];
  }
}
