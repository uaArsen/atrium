package ch.tutteli.atrium.domain.creating

import ch.tutteli.atrium.assertions.Assertion
import ch.tutteli.atrium.core.polyfills.loadSingleService
import ch.tutteli.atrium.creating.AssertionPlant
import ch.tutteli.atrium.creating.AssertionPlantNullable

/**
 * The access point to an implementation of [PairAssertions].
 *
 * It loads the implementation lazily via [loadSingleService].
 */
val pairAssertions by lazy { loadSingleService(PairAssertions::class) }


/**
 * Defines the minimum set of assertion functions and builders applicable to [Map],
 * which an implementation of the domain of Atrium has to provide.
 */
interface PairAssertions {
    fun <K: Any> first(plant: AssertionPlant<Pair<K, *>>, assertionCreator: AssertionPlant<K>.() -> Unit): Assertion
    fun <V: Any> second(plant: AssertionPlant<Pair<*, V>>, assertionCreator: AssertionPlant<V>.() -> Unit): Assertion
    fun <K> nullableFirst(plant: AssertionPlant<Pair<K, *>>, assertionCreator: AssertionPlantNullable<K>.() -> Unit): Assertion
    fun <V> nullableSecond(plant: AssertionPlant<Pair<*, V>>, assertionCreator: AssertionPlantNullable<V>.() -> Unit): Assertion
}
