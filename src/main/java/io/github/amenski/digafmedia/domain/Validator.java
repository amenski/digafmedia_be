package io.github.amenski.digafmedia.domain;

public interface Validator<T> {
  void validate(T entity);
}
