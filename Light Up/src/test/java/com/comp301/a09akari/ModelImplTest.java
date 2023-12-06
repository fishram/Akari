package com.comp301.a09akari;

import com.comp301.a09akari.model.ModelImpl;
import com.comp301.a09akari.model.PuzzleImpl;
import com.comp301.a09akari.model.PuzzleLibrary;
import com.comp301.a09akari.model.PuzzleLibraryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ModelImplTest {
  private ModelImpl model;
  private PuzzleLibrary puzzleLibrary;

  @BeforeEach
  void setUp() {
    puzzleLibrary = new PuzzleLibraryImpl();

    // Add puzzles from SamplePuzzles to the library for testing
    puzzleLibrary.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_01));
    puzzleLibrary.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_04));
    // Add more puzzles if needed

    model = new ModelImpl(puzzleLibrary);
  }

  @Test
  void testIsSolvedWithPuzzle01() {
    model.setActivePuzzleIndex(0); // Assuming PUZZLE_01 is at index 0

    // Adding lamps according to the provided solution
    model.addLamp(6, 0);
    model.addLamp(1, 1);
    model.addLamp(5, 2);
    model.addLamp(0, 3);
    model.addLamp(6, 3);
    model.addLamp(3, 4);
    model.addLamp(2, 5);
    model.addLamp(4, 5);
    model.addLamp(3, 6);
    model.addLamp(5, 6);

    model.printPuzzle();
    assertTrue(model.isSolved(), "PUZZLE_01 should be solved when all clues are satisfied");
  }

  @Test
  void testIsSolvedWithPuzzle04() {
    model.setActivePuzzleIndex(1); // Assuming PUZZLE_05 is at index 4 in the puzzle library

    // Adding lamps according to the solution

    model.addLamp(0, 2);
    model.addLamp(0, 9);
    model.addLamp(1, 7);
    model.addLamp(2, 0);
    model.addLamp(2, 6);
    model.addLamp(3, 1);
    model.addLamp(4, 7);
    model.addLamp(5, 9);
    model.addLamp(6, 4);
    model.addLamp(7, 0);
    model.addLamp(7, 3);
    model.addLamp(7, 8);
    model.addLamp(8, 2);
    model.addLamp(9, 0);
    model.addLamp(9, 5);
    model.addLamp(9, 9);

    model.printPuzzle();

    assertTrue(model.isSolved(), "PUZZLE_05 should be solved when all clues are satisfied");
  }

  @Test
  void testClueSatisfied() {
    model.setActivePuzzleIndex(0); // Set to the first puzzle

    // Manually add lamps around a clue to satisfy it
    // Assuming there is a clue at (1, 1) in PUZZLE_01
    model.addLamp(0, 3); // Adjust coordinates as per the actual puzzle layout
    model.printPuzzle();
    assertTrue(model.isClueSatisfied(0, 4), "Clue at (1, 1) should be satisfied");
  }

  @Test
  void testClueSatisfied2() {
    model.setActivePuzzleIndex(1);

    // Manually add lamps around a clue to satisfy it
    // Assuming there is a clue at (1, 1) in PUZZLE_01
    model.addLamp(0, 2); // Adjust coordinates as per the actual puzzle layout
    model.printPuzzle();
    assertTrue(model.isClueSatisfied(0, 1), "Clue at (1, 1) should be satisfied");
  }

  @Test
  void testIsLamp1() {
    model.setActivePuzzleIndex(1);

    model.addLamp(0, 2);
    assertTrue(model.isLamp(0, 2), "Lamp at (0,2) ");
  }

  @Test
  void testIsLampIllegal() {
    model.setActivePuzzleIndex(1);

    model.addLamp(0, 2);
    model.addLamp(0, 3);
    assertTrue(model.isLampIllegal(0, 2), "Lamp with neighbor is illegal");
  }

  @Test
  void testIsLampIllegalFalse() {
    model.setActivePuzzleIndex(1);

    model.addLamp(0, 2);
    model.addLamp(0, 7);
    assertFalse(model.isLampIllegal(0, 2), "Lamp blocked by wall is okay");
  }
}
