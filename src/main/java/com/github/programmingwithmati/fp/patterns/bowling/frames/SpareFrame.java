package com.github.programmingwithmati.fp.patterns.bowling.frames;

import com.github.programmingwithmati.fp.patterns.bowling.ScoreValue;
import com.github.programmingwithmati.fp.patterns.bowling.functional.Result;

import java.util.Optional;

public record SpareFrame(ScoreValue firstRoll,
                         ScoreValue secondRoll,
                         Optional<ScoreValue> nextRoll) implements Frame {
  public SpareFrame(ScoreValue firstRoll, ScoreValue secondRoll) {
    this(firstRoll, secondRoll, Optional.empty());
  }

  @Override
  public int score() {
    return nextRoll.map(nr -> nr.value() + 10)
            .orElse(10);
  }

  @Override
  public Frame appendRoll(ScoreValue scoreValue) {
    return this;
  }

  @Override
  public Frame addBonus(ScoreValue roll) {
    return nextRoll.map(fr -> this)
            .orElse(new com.github.programmingwithmati.fp.patterns.bowling.frames.SpareFrame(firstRoll, secondRoll, Optional.of(roll)));
  }

  @Override
  public boolean isClosed() {
    return true;
  }

  @Override
  public Result<ScoreValue> validateRoll(ScoreValue scoreValue) {
    return Result.of(scoreValue);
  }
}
