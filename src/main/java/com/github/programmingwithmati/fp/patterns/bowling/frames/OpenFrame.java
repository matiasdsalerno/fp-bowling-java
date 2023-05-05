package com.github.programmingwithmati.fp.patterns.bowling.frames;

import com.github.programmingwithmati.fp.patterns.bowling.ScoreValue;
import com.github.programmingwithmati.fp.patterns.bowling.functional.Result;

import java.util.Optional;

public record OpenFrame(Optional<ScoreValue> firstRoll,
                        Optional<ScoreValue> secondRoll) implements Frame {

  public OpenFrame() {
    this(Optional.empty(), Optional.empty());
  }

  @Override
  public int score() {
    return firstRoll.map(r -> r.value() + secondRoll.orElse(ScoreValue.ZERO).value())
            .orElse(0);
  }

  @Override
  public Frame addBonus(ScoreValue roll) {
    return this;
  }

  @Override
  public boolean isClosed() {
    return secondRoll.isPresent();
  }

  @Override
  public Result<ScoreValue> validateRoll(ScoreValue scoreValue) {
    var firstRollValue = firstRoll.orElse(ScoreValue.ZERO);
    if (firstRollValue.value() + scoreValue.value() > 10) return Result.error("INVALID_ROLL_VALUE","Roll value: %d is not valid because firstRoll(%d) and secondRoll must sum up to 10 at this stage.".formatted(scoreValue.value(), firstRollValue.value()));

    return Result.of(scoreValue);
  }

  public Frame appendRoll(ScoreValue scoreValue) {
    if (isClosed()) return this;
    if (firstRoll.isPresent() && Frame.isSpare(scoreValue, firstRoll.get())) return new SpareFrame(firstRoll.get(), scoreValue);

    return firstRoll.map(roll -> (Frame) new OpenFrame(firstRoll, Optional.of(scoreValue)))
            .orElseGet(() -> Frame.isStrke(scoreValue) ?
                    new StrikeFrame() :
                    new OpenFrame(Optional.of(scoreValue), Optional.empty())
            );
  }

}
