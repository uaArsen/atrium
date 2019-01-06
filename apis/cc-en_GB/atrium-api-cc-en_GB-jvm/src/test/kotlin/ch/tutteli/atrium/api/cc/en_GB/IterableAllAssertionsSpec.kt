package ch.tutteli.atrium.api.cc.en_GB

import ch.tutteli.atrium.verbs.internal.AssertionVerbFactory
import ch.tutteli.atrium.creating.Assert

class IterableAllAssertionsSpec: ch.tutteli.atrium.spec.integration.IterableAllAssertionsSpec(
    AssertionVerbFactory,
    Assert<out Iterable<Double>>::all.name to Assert<out Iterable<Double>>::all,
    Assert<out Iterable<Double?>>::allOfNullable.name to Assert<out Iterable<Double?>>::allOfNullable,
    "◆ ", "❗❗ ", "⚬ ", "» ", "▶ ", "◾ "
)
