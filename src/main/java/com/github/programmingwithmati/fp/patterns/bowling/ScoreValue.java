package com.github.programmingwithmati.fp.patterns.bowling;

import com.github.programmingwithmati.fp.patterns.bowling.functional.Result;

public record ScoreValue(int value) {

  public static final ScoreValue ZERO = new ScoreValue(0);

  public static Result<ScoreValue> of(int value) {
    if (value < 0 || value > 10) return Result.error("SCORE_VALUE_ERROR", "Value must be between 0 and 10, but was: %d".formatted(value));

    return Result.of(new ScoreValue(value));
  }
}
