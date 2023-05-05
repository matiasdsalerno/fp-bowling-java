package com.github.programmingwithmati.fp.patterns.bowling.functional;

import java.util.function.BiFunction;
import java.util.function.Function;

public sealed interface Result<T> permits Result.OkResult, Result.ErrorResult {

  <R> Result<R> map(Function<T, R> mapper);
  <R> Result<R> flatMap(Function<T, Result<R>> mapper);
  T orOnErrorSupply(BiFunction<String, String, T> onErrorSupplier);

  static <T> Result<T> of(T value) {
    return new OkResult<>(value);
  }

  static <T> Result<T> error(String errorCode, String errorMessage) {
    return new ErrorResult<>(errorCode, errorMessage);
  }

  final class OkResult<T> implements Result<T> {

    private final T value;

    private OkResult(T value) {
      this.value = value;
    }

    @Override
    public <R> Result<R> map(Function<T, R> mapper) {
      return new OkResult<>(mapper.apply(value));
    }

    @Override
    public <R> Result<R> flatMap(Function<T, Result<R>> mapper) {
      return mapper.apply(value);
    }

    @Override
    public T orOnErrorSupply(BiFunction<String, String, T> onErrorSupplier) {
      return value;
    }
  }

  final class ErrorResult<T> implements Result<T> {

    private final String errorCode;
    private final String errorMessage;

    public ErrorResult(String errorCode, String errorMessage) {
      this.errorCode = errorCode;
      this.errorMessage = errorMessage;
    }

    @Override
    public <R> Result<R> map(Function<T, R> mapper) {
      return new ErrorResult<>(errorCode, errorMessage);
    }

    @Override
    public <R> Result<R> flatMap(Function<T, Result<R>> mapper) {
      return new ErrorResult<>(errorCode, errorMessage);
    }

    @Override
    public T orOnErrorSupply(BiFunction<String, String, T> onErrorSupplier) {
      return onErrorSupplier.apply(errorCode, errorMessage);
    }
  }
}
