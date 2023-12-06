package com.comp301.a09akari.model;

import java.util.ArrayList;
import java.util.List;

public class ModelImpl implements Model {
  private final PuzzleLibrary puzzleLibrary;
  private int activePuzzleIndex;
  private boolean[][] lamps;
  private List<ModelObserver> observers;
  private boolean showingTitleScreen = true;

  public ModelImpl(PuzzleLibrary library) {
    this.puzzleLibrary = library;
    this.activePuzzleIndex = 0;
    this.observers = new ArrayList<>();
    initializeLampGrid();
  }

  private void initializeLampGrid() {
    Puzzle activePuzzle = getActivePuzzle();
    if (activePuzzle != null) {
      lamps = new boolean[activePuzzle.getHeight()][activePuzzle.getWidth()];
    } else {
      lamps = null; // No active puzzle, set lamps to null
    }
  }

  @Override
  public void addLamp(int r, int c) {
    checkBounds(r, c);
    if (getActivePuzzle().getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException("Lamps can only be placed on CORRIDOR cells");
    }
    lamps[r][c] = true;
    notifyObservers();
  }

  @Override
  public void removeLamp(int r, int c) {
    checkBounds(r, c);
    if (getActivePuzzle().getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException("Lamps can only be removed from CORRIDOR cells");
    }
    lamps[r][c] = false;
    notifyObservers();
  }

  public boolean isLit(int r, int c) {
    // Check bounds first
    if (r < 0 || c < 0 || r >= getActivePuzzle().getHeight() || c >= getActivePuzzle().getWidth()) {
      throw new IndexOutOfBoundsException("Cell position out of bounds");
    }

    // Check vertically upwards
    for (int i = r; i >= 0; i--) {
      if (lamps[i][c]) {
        System.out.println(
            "isLit: Lamp found at (" + i + ", " + c + ") illuminating (" + r + ", " + c + ")");
        return true; // Lamp found illuminating the cell
      } else if (getActivePuzzle().getCellType(i, c) != CellType.CORRIDOR) {
        break; // Wall or clue cell encountered, stop checking this direction
      }
    }

    // Check vertically downwards
    for (int i = r; i < getActivePuzzle().getHeight(); i++) {
      if (lamps[i][c]) {
        System.out.println(
            "isLit: Lamp found at (" + i + ", " + c + ") illuminating (" + r + ", " + c + ")");
        return true; // Lamp found illuminating the cell
      } else if (getActivePuzzle().getCellType(i, c) != CellType.CORRIDOR) {
        break; // Wall or clue cell encountered, stop checking this direction
      }
    }

    // Check horizontally to the left
    for (int i = c; i >= 0; i--) {
      if (lamps[r][i]) {
        System.out.println(
            "isLit: Lamp found at (" + r + ", " + i + ") illuminating (" + r + ", " + c + ")");
        return true; // Lamp found illuminating the cell
      } else if (getActivePuzzle().getCellType(r, i) != CellType.CORRIDOR) {
        break; // Wall or clue cell encountered, stop checking this direction
      }
    }

    // Check horizontally to the right
    for (int i = c; i < getActivePuzzle().getWidth(); i++) {
      if (lamps[r][i]) {
        System.out.println(
            "isLit: Lamp found at (" + r + ", " + i + ") illuminating (" + r + ", " + c + ")");
        return true; // Lamp found illuminating the cell
      } else if (getActivePuzzle().getCellType(r, i) != CellType.CORRIDOR) {
        break; // Wall or clue cell encountered, stop checking this direction
      }
    }

    // If no lamp is found in any direction, the cell is not lit
    return false;
  }

  @Override
  public boolean isLamp(int r, int c) {
    checkBounds(r, c);
    if (getActivePuzzle().getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException("Query for lamp can only be on CORRIDOR cells");
    }
    return lamps[r][c];
  }

  @Override
  public boolean isLampIllegal(int r, int c) {
    checkBounds(r, c);
    if (!isLamp(r, c)) {
      throw new IllegalArgumentException("This cell does not contain a lamp");
    }

    int iR = r + 1;
    while (iR < this.getActivePuzzle().getHeight()
        && this.getActivePuzzle().getCellType(iR, c) == CellType.CORRIDOR) {
      if (isLamp(iR, c)) {
        return true;
      }
      iR++;
    }

    iR = r - 1;
    while (iR >= 0 && this.getActivePuzzle().getCellType(iR, c) == CellType.CORRIDOR) {
      if (isLamp(iR, c)) {
        return true;
      }
      iR--;
    }

    int iC = c + 1;
    while (iC < this.getActivePuzzle().getWidth()
        && this.getActivePuzzle().getCellType(r, iC) == CellType.CORRIDOR) {
      if (isLamp(r, iC)) {
        return true;
      }
      iC++;
    }

    iC = c - 1;
    while (iC >= 0 && this.getActivePuzzle().getCellType(r, iC) == CellType.CORRIDOR) {
      if (isLamp(r, iC)) {
        return true;
      }
      iC--;
    }

    return false;
  }

  @Override
  public Puzzle getActivePuzzle() {
    if (activePuzzleIndex == -1) {
      // No puzzle is selected
      return null;
    }
    return puzzleLibrary.getPuzzle(activePuzzleIndex);
  }

  @Override
  public int getActivePuzzleIndex() {
    return this.activePuzzleIndex;
  }

  @Override
  public void setActivePuzzleIndex(int index) {
    if ((index >= 0 && index < getPuzzleLibrarySize())) {
      this.activePuzzleIndex = index;
      initializeLampGrid();
      notifyObservers();
    } else {
      throw new IndexOutOfBoundsException("Puzzle index is out of bounds");
    }
  }

  @Override
  public int getPuzzleLibrarySize() {
    return puzzleLibrary.size();
  }

  @Override
  public void resetPuzzle() {
    initializeLampGrid();
    notifyObservers();
  }

  @Override
  public boolean isSolved() {
    for (int i = 0; i < getActivePuzzle().getHeight(); i++) {
      for (int j = 0; j < getActivePuzzle().getWidth(); j++) {
        if (getActivePuzzle().getCellType(i, j) == CellType.CLUE) {

          if (!isClueSatisfied(i, j)) {
            return false;
          }
        }
        if (getActivePuzzle().getCellType(i, j) == CellType.CORRIDOR) {
          if (!isLit(i, j)) {
            return false;
          }

          if (isLamp(i, j) && isLampIllegal(i, j)) {
            return false;
          }
        }
      }
    }

    return true;
  }

  @Override
  public boolean isClueSatisfied(int r, int c) {
    checkBounds(r, c);
    if (getActivePuzzle().getCellType(r, c) != CellType.CLUE) {
      throw new IllegalArgumentException("The specified cell is not a CLUE cell");
    }

    int clueValue = getActivePuzzle().getClue(r, c);
    int adjacentLampsCount = 0;

    // Check the cell above
    if (r > 0 && lamps[r - 1][c]) {
      adjacentLampsCount++;
    }

    // Check the cell below
    if (r < getActivePuzzle().getHeight() - 1 && lamps[r + 1][c]) {
      adjacentLampsCount++;
    }

    // Check the cell to the left
    if (c > 0 && lamps[r][c - 1]) {
      adjacentLampsCount++;
    }

    if (c < getActivePuzzle().getWidth() - 1 && lamps[r][c + 1]) {
      adjacentLampsCount++;
    }
    return adjacentLampsCount == clueValue;
  }

  @Override
  public void addObserver(ModelObserver observer) {
    if (!observers.contains(observer)) {
      observers.add(observer);
    }
  }

  @Override
  public void removeObserver(ModelObserver observer) {
    observers.remove(observer);
  }

  private void notifyObservers() {
    for (ModelObserver observer : observers) {
      observer.update(this);
    }
  }

  private void checkBounds(int r, int c) {
    if (r < 0 || r >= getActivePuzzle().getHeight() || c < 0 || c >= getActivePuzzle().getWidth()) {
      throw new IndexOutOfBoundsException("Cell position (" + r + ", " + c + ") is out of bounds");
    }
  }

  @Override
  public void showTitleScreen() {
    showingTitleScreen = true;
    notifyObservers();
  }

  @Override
  public boolean isShowingTitleScreen() {
    return showingTitleScreen;
  }

  @Override
  public void selectPuzzle(int index) {
    if (index < 0 || index >= getPuzzleLibrarySize()) {
      throw new IndexOutOfBoundsException("Puzzle index is out of bounds");
    }
    showingTitleScreen = false;
    this.activePuzzleIndex = index;
    initializeLampGrid();
    notifyObservers();
  }
}
