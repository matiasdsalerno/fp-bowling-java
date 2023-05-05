package com.github.programmingwithmati.fp.patterns.bowling;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

  @Test
  void testScoreWhenGameEmpty() {
    var game = new Game();
    assertEquals(0, game.score());
  }

  @Test
  void testRollAndScore() {
    var game = new Game();
    game = game.roll(new ScoreValue(3));

    assertEquals(3, game.score());
  }

  @Test
  void testRoll2AndScore() {
    var game = new Game();
    game = game.roll(new ScoreValue(3));
    game = game.roll(new ScoreValue(3));

    assertEquals(6, game.score());
  }

  @Test
  void testRoll3AndScore() {
    var game = new Game();
    game = game.roll(new ScoreValue(3));
    game = game.roll(new ScoreValue(3));
    game = game.roll(new ScoreValue(3));

    assertEquals(9, game.score());
  }

  @Test
  void testRoll3AndScoreWhenSpare() {
    var game = new Game();
    game = game.roll(new ScoreValue(3));
    game = game.roll(new ScoreValue(7));
    game = game.roll(new ScoreValue(3));

    assertEquals(16, game.score());
  }

  @Test
  void testRollWhenStrike() {
    var game = new Game();
    game = game.roll(new ScoreValue(10));
    game = game.roll(new ScoreValue(7));
    game = game.roll(new ScoreValue(2));

    assertEquals(28, game.score());
  }
}
