package com.github.programmingwithmati.fp.patterns.bowling.frames;

import com.github.programmingwithmati.fp.patterns.bowling.ScoreValue;
import com.github.programmingwithmati.fp.patterns.bowling.functional.Result;

public sealed interface Frame permits OpenFrame, SpareFrame, StrikeFrame, LastFrame {

  int score();

  Frame appendRoll(ScoreValue scoreValue);

  Frame addBonus(ScoreValue roll);

  boolean isClosed();

  static boolean isStrke(ScoreValue scoreValue) {
    return scoreValue.value() == 10;
  }

  static boolean isSpare(ScoreValue firstRoll, ScoreValue secondRoll) {
    var totalScoreOfFrame = secondRoll.value() + firstRoll.value();
    if (totalScoreOfFrame > 10) throw new IllegalStateException("The sum of the 2 rolls in the frame can't be greater than 10, but was: %d".formatted(totalScoreOfFrame));
    return totalScoreOfFrame == 10;
  }

  Result<ScoreValue> validateRoll(ScoreValue scoreValue);
}

