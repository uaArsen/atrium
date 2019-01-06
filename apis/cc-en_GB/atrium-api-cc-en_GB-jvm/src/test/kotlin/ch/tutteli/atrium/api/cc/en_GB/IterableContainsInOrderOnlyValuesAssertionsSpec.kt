package ch.tutteli.atrium.api.cc.en_GB

import ch.tutteli.atrium.verbs.internal.AssertionVerbFactory
import ch.tutteli.atrium.creating.Assert
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.include
import kotlin.reflect.KFunction3

class IterableContainsInOrderOnlyValuesAssertionsSpec : Spek({

    include(BuilderSpec)
    include(ShortcutSpec)

}) {
    object BuilderSpec : ch.tutteli.atrium.spec.integration.IterableContainsInOrderOnlyValuesAssertionsSpec(
        AssertionVerbFactory,
        getContainsPair(),
        getContainsNullablePair(),
        "◆ ", "✔ ", "✘ ", "❗❗ ", "⚬ ", "▶ ", "◾ ",
        "[Atrium][Builder] "
    )

    object ShortcutSpec : ch.tutteli.atrium.spec.integration.IterableContainsInOrderOnlyValuesAssertionsSpec(
        AssertionVerbFactory,
        getContainsShortcutPair(),
        getContainsNullableShortcutPair(),
        "◆ ", "✔ ", "✘ ", "❗❗ ", "⚬ ", "▶ ", "◾ ",
        "[Atrium][Shortcut] "
    )

    companion object : IterableContainsSpecBase() {
        fun getContainsPair()
            = "$contains.$inOrder.$only.$inOrderOnlyValues" to Companion::containsInOrderOnlyValues

        private fun containsInOrderOnlyValues(plant: Assert<out Iterable<Double>>, a: Double, aX: Array<out Double>): Assert<out Iterable<Double>> {
            return if (aX.isEmpty()) {
                plant.contains.inOrder.only.value(a)
            } else {
                plant.contains.inOrder.only.values(a, *aX)
            }
        }

        fun getContainsNullablePair()
            = "$contains.$inOrder.$only.$inOrderOnlyValues nullable" to Companion::containsInOrderOnlyNullableValues

        private fun containsInOrderOnlyNullableValues(plant: Assert<out Iterable<Double?>>, a: Double?, aX: Array<out Double?>): Assert<out Iterable<Double?>> {
            return if (aX.isEmpty()) {
                plant.contains.inOrder.only.nullableValue(a)
            } else {
                plant.contains.inOrder.only.nullableValues(a, *aX)
            }
        }

        private val containsShortcutFun: KFunction3<Assert<out Iterable<Double>>, Double, Array<out Double>, Assert<out Iterable<Double>>> = Assert<out Iterable<Double>>::containsStrictly
        fun getContainsShortcutPair() = containsShortcutFun.name to Companion::containsInOrderOnlyValuesShortcut

        private fun containsInOrderOnlyValuesShortcut(plant: Assert<out Iterable<Double>>, a: Double, aX: Array<out Double>)
            = plant.containsStrictly(a, *aX)

        private val containsNullableShortcutFun: KFunction3<Assert<out Iterable<Double?>>, Double?, Array<out Double?>, Assert<out Iterable<Double?>>> = Assert<out Iterable<Double?>>::containsStrictlyNullableValues
        fun getContainsNullableShortcutPair() = containsNullableShortcutFun.name to Companion::containsInOrderOnlyNullableValuesShortcut

        private fun containsInOrderOnlyNullableValuesShortcut(plant: Assert<out Iterable<Double?>>, a: Double?, aX: Array<out Double?>): Assert<out Iterable<Double?>> {
            return if (aX.isEmpty()) {
                plant.containsStrictlyNullableValue(a)
            } else {
                plant.containsStrictlyNullableValues(a, *aX)
            }
        }
    }
}

