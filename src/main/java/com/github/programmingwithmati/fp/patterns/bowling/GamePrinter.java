package com.github.programmingwithmati.fp.patterns.bowling;

import com.github.programmingwithmati.fp.patterns.bowling.frames.*;
import com.github.programmingwithmati.fp.patterns.bowling.functional.ImmutableList;

import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GamePrinter {

  String firstRowToString(Game game) {
    return rowToString(game, (integer, frame) -> firstRowPerFrameToString(frame));
  }

  String rowToString(Game game, BiFunction<Integer, Frame, String> toStringFunction) {
    ImmutableList<Frame> frames = getFullGameFrames(game);
    return "|%s|".formatted(IntStream.range(0, 10)
            .mapToObj(i -> toStringFunction.apply(i, frames.get(i)))
            .collect(Collectors.joining("|")));
  }

  String secondRowToString(Game game) {
    return rowToString(game, (i, frame) -> printSecondRowPerFrame(game, frame, i));
  }

  String firstRowPerFrameToString(Frame frame) {
    return switch (frame) {
      case StrikeFrame ignored -> " X|  ";
      case SpareFrame spareFrame -> "%02d| /".formatted(spareFrame.firstRoll().value());
      case OpenFrame openFrame -> "%s|%s".formatted(printOptionalRoll(openFrame.firstRoll()), printOptionalRoll(openFrame.secondRoll()));
      case LastFrame lastFrame -> "%s|%s|%s".formatted(printOptionalRoll(lastFrame.firstRoll()), printOptionalRoll(lastFrame.secondRoll()), printOptionalRoll(lastFrame.thirdRoll()));
    };
  }

  private String printSecondRowPerFrame(Game game, Frame f, Integer i) {
    return switch (f) {
      case LastFrame lastFrame -> lastFrame.isClosed() ? " %03d ".formatted(accumulatedUntilFrame(i, game)) : "        ";
      case StrikeFrame strikeFrame -> strikeFrame.nextRoll().flatMap(r -> strikeFrame.secondToNext()).map(r -> " %03d ".formatted(accumulatedUntilFrame(i, game))).orElse("     ");
      case SpareFrame spareFrame -> spareFrame.nextRoll().map(r -> " %03d ".formatted(accumulatedUntilFrame(i, game))).orElse("     ");
      case OpenFrame openFrame -> openFrame.isClosed() ? " %03d ".formatted(accumulatedUntilFrame(i, game)) : "     ";
    };
  }

  private int accumulatedUntilFrame(int i, Game game) {
    ImmutableList<Frame> frames = getFullGameFrames(game);
    return frames.stream()
            .limit(i + 1)
            .mapToInt(Frame::score)
            .sum();
  }

  private ImmutableList<Frame> getFullGameFrames(Game game) {
    var allFrames = game.allFrames();
    return allFrames.appendAll(createListWithRemainingOpenFrames(allFrames));
  }

  private ImmutableList<Frame> createListWithRemainingOpenFrames(ImmutableList<Frame> allFrames) {
    var rangeLimit = 10 - allFrames.size();
    return new ImmutableList<>(IntStream.range(0, rangeLimit)
            .mapToObj(i -> (Frame)(i != (rangeLimit - 1) ? new OpenFrame() : new LastFrame()))
            .toList());
  }

  String printOptionalRoll(Optional<ScoreValue> scoreValue) {
    Objects.requireNonNull(scoreValue);
    return scoreValue.map(fr -> "%02d".formatted(fr.value())).orElse("  ");
  }
}
