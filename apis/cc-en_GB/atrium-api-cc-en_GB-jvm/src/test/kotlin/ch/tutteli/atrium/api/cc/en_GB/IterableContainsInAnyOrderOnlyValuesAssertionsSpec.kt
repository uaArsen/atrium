package ch.tutteli.atrium.api.cc.en_GB

import ch.tutteli.atrium.verbs.internal.AssertionVerbFactory
import ch.tutteli.atrium.creating.Assert

class IterableContainsInAnyOrderOnlyValuesAssertionsSpec : ch.tutteli.atrium.spec.integration.IterableContainsInAnyOrderOnlyValuesAssertionsSpec(
    AssertionVerbFactory,
    getContainsPair(),
    getContainsNullablePair(),
    "◆ ", "✔ ", "✘ ", "❗❗ ", "⚬ "
) {
    companion object : IterableContainsSpecBase() {
        fun getContainsPair()
            = "$contains.$inAnyOrder.$only.$inAnyOrderOnlyValues" to Companion::containsInAnyOrderOnlyValues

        private fun containsInAnyOrderOnlyValues(plant: Assert<out Iterable<Double>>, a: Double, aX: Array<out Double>): Assert<out Iterable<Double>> {
            return if (aX.isEmpty()) {
                plant.contains.inAnyOrder.only.value(a)
            } else {
                plant.contains.inAnyOrder.only.values(a, *aX)
            }
        }

        fun getContainsNullablePair() =
            "$contains.$inAnyOrder.$only.$inAnyOrderOnlyValues nullable" to Companion::containsInAnyOrderOnlyNullableValues

        private fun containsInAnyOrderOnlyNullableValues(plant: Assert<out Iterable<Double?>>, a: Double?, aX: Array<out Double?>): Assert<out Iterable<Double?>> {
            return if (aX.isEmpty()) {
                plant.contains.inAnyOrder.only.nullableValue(a)
            } else {
                plant.contains.inAnyOrder.only.nullableValues(a, *aX)
            }
        }
    }
}
