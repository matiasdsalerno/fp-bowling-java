package com.github.programmingwithmati.fp.patterns.bowling;

import com.github.programmingwithmati.fp.patterns.bowling.frames.*;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class FrameTest {


  @Test
  void testScoreOpenFrame() {
    var openFrame = new OpenFrame(Optional.empty(), Optional.empty());

    assertEquals(0, openFrame.score());
  }

  @Test
  void testScoreOpenFrameWhen1Roll() {
    var frame = new OpenFrame(Optional.empty(), Optional.empty());
    var result = frame.appendRoll(new ScoreValue(4));
    assertEquals(4, result.score());
  }

  @Test
  void testScoreOpenFrameWhen2Roll() {
    var frame = new OpenFrame(Optional.empty(), Optional.empty());
    var result = frame.appendRoll(new ScoreValue(4));
    result = ((OpenFrame) result).appendRoll(new ScoreValue(2));
    assertEquals(6, result.score());
  }

  @Test
  void testScoreStrikeWhenNotBonusAdded() {
    var frame = new StrikeFrame();
    assertEquals(10, frame.score());
  }

  @Test
  void testScoreStrikeWhen1Bonus() {
    Frame frame = new StrikeFrame();
    frame = frame.addBonus(new ScoreValue(2));
    assertEquals(12, frame.score());
  }

  @Test
  void testScoreStrikeWhen2Bonus() {
    Frame frame = new StrikeFrame();
    frame = frame.addBonus(new ScoreValue(2));
    frame = frame.addBonus(new ScoreValue(10));
    assertEquals(22, frame.score());
  }

  @Test
  void testScoreStrikeWhen3Bonus() {
    Frame frame = new StrikeFrame();
    frame = frame.addBonus(new ScoreValue(2));
    frame = frame.addBonus(new ScoreValue(10));
    frame = frame.addBonus(new ScoreValue(10));
    assertEquals(22, frame.score());
  }

  @Test
  void testScoreSpareWhenNoBonus() {
    Frame frame = new SpareFrame(new ScoreValue(2), new ScoreValue(8));
    assertEquals(10, frame.score());
  }

  @Test
  void testScoreSpareWhenBonus() {
    Frame frame = new SpareFrame(new ScoreValue(2), new ScoreValue(8));
    frame = frame.addBonus(new ScoreValue(2));
    assertEquals(12, frame.score());
  }

  @Test
  void testScoreSpareWhen2Bonus() {
    Frame frame = new SpareFrame(new ScoreValue(2), new ScoreValue(8));
    frame = frame.addBonus(new ScoreValue(2));
    frame = frame.addBonus(new ScoreValue(2));
    assertEquals(12, frame.score());
  }

  @Test
  void testLastFrameWhen2LowScores() {
    var frame = new LastFrame();
    frame = (LastFrame) frame.appendRoll(new ScoreValue(3));
    var result = frame.appendRoll(new ScoreValue(3));
    assertTrue(result.isClosed());
    assertEquals( 6, result.score());
  }

  @Test
  void testLastFrameWhenSpareAndLowScore() {
    var frame = new LastFrame();
    frame = (LastFrame) frame.appendRoll(new ScoreValue(3));
    frame = (LastFrame) frame.appendRoll(new ScoreValue(7));
    var result = frame.appendRoll(new ScoreValue(2));
    assertTrue(result.isClosed());
    assertEquals( 12, result.score());
  }

  @Test
  void testLastFrameWhenSpareAndStrike() {
    var frame = new LastFrame();
    frame = (LastFrame) frame.appendRoll(new ScoreValue(3));
    frame = (LastFrame) frame.appendRoll(new ScoreValue(7));
    var result = frame.appendRoll(new ScoreValue(10));
    assertTrue(result.isClosed());
    assertEquals( 20, result.score());
  }

  @Test
  void testLastFrameWhenStrikeAndSpare() {
    var frame = new LastFrame();
    frame = (LastFrame) frame.appendRoll(new ScoreValue(10));
    frame = (LastFrame) frame.appendRoll(new ScoreValue(7));
    var result = frame.appendRoll(new ScoreValue(3));
    assertTrue(result.isClosed());
    assertEquals( 20, result.score());
  }

  @Test
  void testLastFrameWhenStrikeAnd2Lows() {
    var frame = new LastFrame();
    frame = (LastFrame) frame.appendRoll(new ScoreValue(10));
    frame = (LastFrame) frame.appendRoll(new ScoreValue(7));
    var result = frame.appendRoll(new ScoreValue(2));
    assertTrue(result.isClosed());
    assertEquals( 19, result.score());
  }

  @Test
  void testLastFrameWhenStrikeStrikeAndLow() {
    var frame = new LastFrame();
    frame = (LastFrame) frame.appendRoll(new ScoreValue(10));
    frame = (LastFrame) frame.appendRoll(new ScoreValue(10));
    var result = frame.appendRoll(new ScoreValue(4));
    assertTrue(result.isClosed());
    assertEquals( 24, result.score());
  }

  @Test
  void testLastFrameWhenStrikeStrikeStrike() {
    var frame = new LastFrame();
    frame = (LastFrame) frame.appendRoll(new ScoreValue(10));
    frame = (LastFrame) frame.appendRoll(new ScoreValue(10));
    var result = frame.appendRoll(new ScoreValue(10));
    assertTrue(result.isClosed());
    assertEquals( 30, result.score());
  }
}
