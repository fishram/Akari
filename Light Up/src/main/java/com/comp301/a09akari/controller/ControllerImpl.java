package com.comp301.a09akari.controller;

import com.comp301.a09akari.model.Model;
import com.comp301.a09akari.model.Puzzle;
import com.comp301.a09akari.model.CellType;

import java.util.Random;

public class ControllerImpl implements AlternateMvcController {
  private Model model;
  private Random rand;

  public ControllerImpl(Model model) {
    this.model = model;
    this.rand = new Random();
  }

  @Override
  public void clickNextPuzzle() {
    int newIndex = (model.getActivePuzzleIndex() + 1) % model.getPuzzleLibrarySize();
    model.setActivePuzzleIndex(newIndex);
  }

  @Override
  public void clickPrevPuzzle() {
    int newIndex = model.getActivePuzzleIndex() - 1;
    if (newIndex < 0) {
      newIndex = model.getPuzzleLibrarySize() - 1;
    }
    model.setActivePuzzleIndex(newIndex);
  }

  public void setActivePuzzleAndNotify(int index) {
    model.setActivePuzzleIndex(index);
    // notifyObservers() will be called within setActivePuzzleIndex if it changes the state
  }

  @Override
  public void clickRandPuzzle() {
    int randomIndex = rand.nextInt(model.getPuzzleLibrarySize());
    setActivePuzzleAndNotify(randomIndex); // This will notify observers
  }

  @Override
  public void clickResetPuzzle() {
    model.resetPuzzle();
  }

  @Override
  public void clickCell(int r, int c) {
    // Ensure the click is within bounds and on a corridor cell
    if (model.getActivePuzzle().getCellType(r, c) == CellType.CORRIDOR) {
      if (model.isLamp(r, c)) {
        model.removeLamp(r, c);
      } else {
        model.addLamp(r, c);
      }
    }
  }

  @Override
  public boolean isLit(int r, int c) {
    return model.isLit(r, c);
  }

  @Override
  public boolean isLamp(int r, int c) {
    return model.isLamp(r, c);
  }

  public boolean isLampIllegal(int r, int c) {
    return model.isLampIllegal(r, c);
  }

  @Override
  public boolean isClueSatisfied(int r, int c) {
    return model.isClueSatisfied(r, c);
  }

  @Override
  public boolean isSolved() {
    return model.isSolved();
  }

  @Override
  public Puzzle getActivePuzzle() {
    return model.getActivePuzzle();
  }

  public int getActivePuzzleIndex() {
    return model.getActivePuzzleIndex();
  }

  public int getPuzzleLibrarySize() {
    return model.getPuzzleLibrarySize();
  }

  public void showTitleScreen() {

    model.showTitleScreen();
  }

  public void selectPuzzle(int index) {
    model.selectPuzzle(index);
  }
}
