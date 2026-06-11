package com.mycodeyatra.screenplay;

/**
 * Interface representing a query to inspect the state of the UI or system.
 */
public interface Question<T> {
    T answeredBy(Actor actor);
}
