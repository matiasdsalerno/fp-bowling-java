package com.github.programmingwithmati.fp.patterns.bowling.frames;

import com.github.programmingwithmati.fp.patterns.bowling.ScoreValue;
import com.github.programmingwithmati.fp.patterns.bowling.functional.Result;

import java.util.Optional;

public record LastFrame(Optional<ScoreValue> firstRoll,
                        Optional<ScoreValue> secondRoll,
                        Optional<ScoreValue> thirdRoll) implements Frame {

  public LastFrame() {
    this(Optional.empty(),
            Optional.empty(),
            Optional.empty());
  }

  public Frame appendRoll(ScoreValue scoreValue) {
    if (isClosed()) return this;
    if (secondRoll.isPresent()) return new LastFrame(firstRoll, secondRoll, Optional.of(scoreValue));
    if (firstRoll.isPresent()) return new LastFrame(firstRoll,  Optional.of(scoreValue), Optional.empty());
    return new LastFrame(Optional.of(scoreValue), Optional.empty(), Optional.empty());
  }

  private boolean isThirdRollAllowed(ScoreValue firstRoll, ScoreValue secondRoll) {
    return firstRoll.value() + secondRoll.value() >= 10;
  }

  @Override
  public int score() {
    return firstRoll.orElse(ScoreValue.ZERO).value() +
            secondRoll.orElse(ScoreValue.ZERO).value() +
            thirdRoll.orElse(ScoreValue.ZERO).value();
  }

  @Override
  public Frame addBonus(ScoreValue roll) {
    return this;
  }

  @Override
  public boolean isClosed() {
    return firstRoll.isPresent() &&
            secondRoll.isPresent() &&
            (thirdRoll.isPresent() || !isThirdRollAllowed(firstRoll.get(), secondRoll.get()));
  }

  @Override
  public Result<ScoreValue> validateRoll(ScoreValue scoreValue) {
    if (thirdRoll.isPresent()) return Result.of(scoreValue);
    if (firstRoll.isPresent() && secondRoll.isPresent() && isThirdRollAllowed(firstRoll.get(), secondRoll.get())) return Result.of(scoreValue);
    if (firstRoll.orElse(ScoreValue.ZERO).value() + scoreValue.value() > 10) return Result.error("INVALID_ROLL_VALUE", "First roll(%d) and Second roll (%d) must sum at most 10.".formatted(firstRoll.orElse(ScoreValue.ZERO).value(), scoreValue.value() ));
    return Result.of(scoreValue);
  }

}
