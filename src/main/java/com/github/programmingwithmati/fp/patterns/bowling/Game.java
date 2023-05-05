package com.github.programmingwithmati.fp.patterns.bowling;

import com.github.programmingwithmati.fp.patterns.bowling.frames.Frame;
import com.github.programmingwithmati.fp.patterns.bowling.frames.LastFrame;
import com.github.programmingwithmati.fp.patterns.bowling.frames.OpenFrame;
import com.github.programmingwithmati.fp.patterns.bowling.functional.ImmutableList;
import com.github.programmingwithmati.fp.patterns.bowling.functional.Result;

public record Game(Frame currentFrame,
                   ImmutableList<Frame> frames) {

  public Game() {
    this(           new OpenFrame(),
            ImmutableList.empty());
  }

  public int score() {
    return frames.append(currentFrame).stream()
            .mapToInt(Frame::score)
            .sum();
  }

  public Game roll(ScoreValue scoreValue) {
    if (isCompleted()) return this;

    var newCurrentFrame = currentFrame.appendRoll(scoreValue);
    var newFrames = new ImmutableList<>(
            frames.stream()
            .map(f -> f.addBonus(scoreValue))
            .toList()
    );

    if (newCurrentFrame.isClosed() && frames.size() < 8) return new Game(new OpenFrame(), newFrames.append(newCurrentFrame));
    if (newCurrentFrame.isClosed() && frames.size() == 8) return new Game(new LastFrame(), newFrames.append(newCurrentFrame));
    return new Game(newCurrentFrame, newFrames);
  }

  private boolean isCompleted() {
    return frames.size() == 9 && currentFrame().isClosed();
  }

  public ImmutableList<Frame> allFrames() {
    return frames.append(currentFrame);
  }

  public Result<ScoreValue> validateRoll(ScoreValue scoreValue) {
    return currentFrame.validateRoll(scoreValue);
  }
}
