package com.mycodeyatra.screenplay;
public interface Question<T> {
    T answeredBy(Actor actor);
}