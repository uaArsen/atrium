package ch.tutteli.atrium.spec.integration

import ch.tutteli.atrium.creating.Assert
import ch.tutteli.atrium.spec.AssertionVerbFactory
import org.jetbrains.spek.api.dsl.Spec
import org.jetbrains.spek.api.dsl.SpecBody

abstract class IterablePredicateSpecBase(verbs: AssertionVerbFactory, spec: Spec.() -> Unit) : IterableContainsEntriesSpecBase(verbs, spec) {
    companion object {

        fun SpecBody.nonNullableCases(
            describePrefix: String,
            containsPair: Pair<String, Assert<out Iterable<Double>>.(Assert<Double>.() -> Unit) -> Assert<out Iterable<Double>>>,
            containsNullablePair: Pair<String, Assert<out Iterable<Double?>>.((Assert<Double>.() -> Unit)?) -> Assert<out Iterable<Double?>>>,
            action: SpecBody.(Assert<out Iterable<Double>>.(Assert<Double>.() -> Unit) -> Any) -> Unit
        ) {
            group("$describePrefix describe non-nullable cases") {
                mapOf<String, Assert<out Iterable<Double>>.(Assert<Double>.() -> Unit) -> Any>(
                    containsPair.first to { a -> containsPair.second(this, a) },
                    containsNullablePair.first to { a -> containsNullablePair.second(this, a) }
                ).forEach { (describe, containsEntriesFunArr) ->
                    describeFun(describe) {
                        action(containsEntriesFunArr)
                    }
                }
            }
        }
    }
}
