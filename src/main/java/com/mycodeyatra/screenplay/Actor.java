package com.mycodeyatra.screenplay;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents the Actor performing the test scenarios.
 * An Actor has Abilities, performs Tasks/Interactions, and answers Questions.
 */
public class Actor {
    private final String name;
    private final Map<Class<? extends Ability>, Ability> abilities = new HashMap<>();

    private Actor(String name) {
        this.name = name;
    }

    public static Actor named(String name) {
        return new Actor(name);
    }

    public <T extends Ability> Actor can(T ability) {
        abilities.put(ability.getClass(), ability);
        return this;
    }

    @SuppressWarnings("unchecked")
    public <T extends Ability> T usingAbilityTo(Class<T> abilityClass) {
        T ability = (T) abilities.get(abilityClass);
        if (ability == null) {
            throw new IllegalArgumentException("Actor " + name + " does not have the ability: " + abilityClass.getSimpleName());
        }
        return ability;
    }

    public void attemptsTo(Performable... tasks) {
        for (Performable task : tasks) {
            task.performAs(this);
        }
    }

    public <T> T asksFor(Question<T> question) {
        return question.answeredBy(this);
    }

    public String getName() {
        return name;
    }
}
