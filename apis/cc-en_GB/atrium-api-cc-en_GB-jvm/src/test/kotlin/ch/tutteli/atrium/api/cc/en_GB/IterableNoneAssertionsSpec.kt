package ch.tutteli.atrium.api.cc.en_GB

import ch.tutteli.atrium.verbs.internal.AssertionVerbFactory
import ch.tutteli.atrium.creating.Assert
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.include

class IterableNoneAssertionsSpec : Spek({

    include(PredicateSpec)
    include(BuilderSpec)

}) {

    object PredicateSpec : ch.tutteli.atrium.spec.integration.IterableNoneAssertionsSpec(
        AssertionVerbFactory,
        Assert<out Iterable<Double>>::none.name to Assert<out Iterable<Double>>::none,
        Assert<out Iterable<Double?>>::noneOfNullable.name to Assert<out Iterable<Double?>>::noneOfNullable,
        "◆ ", "✔ ", "✘ ", "⚬ ", "» ", "▶ ", "◾ ",
        "[Atrium][Predicate] "
    )

    object BuilderSpec : ch.tutteli.atrium.spec.integration.IterableNoneAssertionsSpec(
        AssertionVerbFactory,
        getContainsNotPair(),
        getContainsNotNullablePair(),
        "◆ ", "✔ ", "✘ ", "⚬ ", "» ", "▶ ", "◾ ",
        "[Atrium][Builder] "
    )
    companion object : IterableContainsSpecBase() {

        private fun getContainsNotPair()
            = containsNot to Companion::containsNotFun

        private fun containsNotFun(plant: Assert<out Iterable<Double>>, a: Assert<Double>.() -> Unit)
                = plant.containsNot.entry(a)

        private fun getContainsNotNullablePair() = "$containsNot nullable" to Companion::containsNotNullableFun

        private fun containsNotNullableFun(plant: Assert<out Iterable<Double?>>, a: (Assert<Double>.() -> Unit)?)
                = plant.containsNot.nullableEntry(a)
    }
}
