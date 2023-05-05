package com.github.programmingwithmati.fp.patterns.bowling;

import com.github.programmingwithmati.fp.patterns.bowling.functional.ImmutableList;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

class ImmutableListTest {

  @Test
  void testListComprehension() {
    cartesian(
            List.of(
                    List.of(1,2),
                    List.of(3,4),
                    List.of(5,6)
            )
    ).forEach(System.out::println);
  }

  <T> List<List<T>> cartesian(List<List<T>> row) {
    if (row.isEmpty()) return List.of(Collections.<T>emptyList());

    return ImmutableList.listComprehension(
            (o, o2) -> Stream.concat(Stream.of(o), o2.stream()).toList(),
            () -> row.get(0),
            () -> cartesian(row.stream().skip(1).toList())
            );
  }

}
