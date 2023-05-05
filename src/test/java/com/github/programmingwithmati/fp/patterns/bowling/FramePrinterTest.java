package com.github.programmingwithmati.fp.patterns.bowling;

import org.junit.jupiter.api.Test;

class FramePrinterTest {

  GamePrinter framePrinter = new GamePrinter();

  @Test
  void printBowling() {
    var game = new Game();
    System.out.println(framePrinter.firstRowToString(game));
    System.out.println(framePrinter.secondRowToString(game));
  }

  @Test
  void printBowlingWhenGameStarted() {
    var game = new Game();
    game = game.roll(new ScoreValue(10));
    game = game.roll(new ScoreValue(4));
    game = game.roll(new ScoreValue(2));
    game = game.roll(new ScoreValue(0));
    game = game.roll(new ScoreValue(10));

    System.out.println(framePrinter.firstRowToString(game));
    System.out.println(framePrinter.secondRowToString(game));
  }

  @Test
  void printBowlingWhenGamePerfect() {
    var game = new Game();
    game = game.roll(new ScoreValue(10));
    game = game.roll(new ScoreValue(10));
    game = game.roll(new ScoreValue(10));
    game = game.roll(new ScoreValue(10));
    game = game.roll(new ScoreValue(10));
    game = game.roll(new ScoreValue(10));
    game = game.roll(new ScoreValue(10));
    game = game.roll(new ScoreValue(10));
    game = game.roll(new ScoreValue(10));
    game = game.roll(new ScoreValue(10));
    game = game.roll(new ScoreValue(10));
    game = game.roll(new ScoreValue(10));
    System.out.println(game.score());
    System.out.println(game.allFrames());

    System.out.println(framePrinter.firstRowToString(game));
    System.out.println(framePrinter.secondRowToString(game));
  }

}
