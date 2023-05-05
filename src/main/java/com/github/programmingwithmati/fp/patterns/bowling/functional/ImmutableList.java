package com.github.programmingwithmati.fp.patterns.bowling.functional;

import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

public record ImmutableList<T>(List<T> elements) {

  public static <T> ImmutableList<T> empty() {
    return new ImmutableList<>(Collections.emptyList());
  }

  public ImmutableList<T> append(T element) {
    return new ImmutableList<>(Stream.concat(elements.stream(), Stream.of(element)).toList());
  }

  public Stream<T> stream() {
    return elements.stream();
  }

  public int size() {
    return elements.size();
  }

  public ImmutableList<T> appendAll(ImmutableList<? extends T> list) {
    return new ImmutableList<>(Stream.concat(elements.stream(), list.stream()).toList());
  }

  public int indexOf(T element) {
    return elements().indexOf(element);
  }

  public T get(int i) {
    return elements.get(i);
  }

  public static <T,V,R> List<R> listComprehension(
          Function<T, R> acc,
          Supplier<List<T>> gen1
  ) {
    var ts = gen1.get();
    return ts.stream().map(acc).toList();
  }

  public static <T,V,R> List<R> listComprehension(
          BiFunction<T, V,R> acc,
          Supplier<List<T>> gen1,
          Supplier<List<V>> gen2
  ) {
    var ts = gen1.get();
    var vs = gen2.get();
    return ts.stream().flatMap(t -> vs.stream().map(v -> acc.apply(t,v))).toList();
  }
}
