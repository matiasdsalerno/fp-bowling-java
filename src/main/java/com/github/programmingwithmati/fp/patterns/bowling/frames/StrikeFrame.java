package com.github.programmingwithmati.fp.patterns.bowling.frames;

import com.github.programmingwithmati.fp.patterns.bowling.ScoreValue;
import com.github.programmingwithmati.fp.patterns.bowling.functional.Result;

import java.util.Optional;

public record StrikeFrame(Optional<ScoreValue> nextRoll,
                                Optional<ScoreValue> secondToNext) implements Frame {
  public StrikeFrame() {
    this(Optional.empty(), Optional.empty());
  }

  @Override
  public int score() {
    return nextRoll.map(r1 -> r1.value() + secondToNext.orElse(ScoreValue.ZERO).value() + 10)
            .orElse(10);
  }

  @Override
  public Frame appendRoll(ScoreValue scoreValue) {
    return this;
  }

  @Override
  public Frame addBonus(ScoreValue roll) {
    if (nextRoll.isEmpty())
      return new com.github.programmingwithmati.fp.patterns.bowling.frames.StrikeFrame(Optional.of(roll), Optional.empty());
    if (secondToNext.isEmpty())
      return new com.github.programmingwithmati.fp.patterns.bowling.frames.StrikeFrame(this.nextRoll, Optional.of(roll));
    return this;
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

