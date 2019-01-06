package ch.tutteli.atrium.domain.robstoll.creating

import ch.tutteli.atrium.assertions.Assertion
import ch.tutteli.atrium.creating.AssertionPlant
import ch.tutteli.atrium.creating.AssertionPlantNullable
import ch.tutteli.atrium.domain.creating.MapAssertions
import ch.tutteli.atrium.domain.robstoll.lib.creating.*
import kotlin.reflect.KClass

/**
 * Robstoll's implementation of [MapAssertions].
 */
class MapAssertionsImpl : MapAssertions {

    override fun <K, V: Any> contains(plant: AssertionPlant<out Map<K, V>>, pairs: List<Pair<K, V>>): Assertion
        = _contains(plant, pairs)

    override fun <K, V: Any> containsNullable(plant: AssertionPlant<out Map<K, V?>>, type: KClass<V>, pairs: List<Pair<K, V?>>): Assertion
        = _containsNullable(plant, type, pairs)


    override fun <K> containsKey(plant: AssertionPlant<out Map<K, *>>, key: K)
        = _containsKey(plant, key)

    override fun <K, V : Any> getExisting(
        plant: AssertionPlant<out Map<K, V>>,
        key: K,
        assertionCreator: AssertionPlant<V>.() -> Unit
    ) = _getExisting(plant, key, assertionCreator)

    override fun <K, V> getExistingNullable(
        plant: AssertionPlant<out Map<K, V>>,
        key: K,
        assertionCreator: AssertionPlantNullable<V>.() -> Unit
    ) = _getExistingNullable(plant, key, assertionCreator)

    override fun hasSize(plant: AssertionPlant<out Map<*, *>>, size: Int)
        = _hasSize(plant, size)

    override fun isEmpty(plant: AssertionPlant<out Map<*, *>>)
        = _isEmpty(plant)

    override fun isNotEmpty(plant: AssertionPlant<out Map<*, *>>)
        = _isNotEmpty(plant)

    override fun <K> keys(plant: AssertionPlant<out Map<K, *>>, assertionCreator: AssertionPlant<Set<K>>.() -> Unit): Assertion
        = _keys(plant, assertionCreator)

    override fun <V> values(plant: AssertionPlant<out Map<*, V>>, assertionCreator: AssertionPlant<Collection<V>>.() -> Unit): Assertion
        = _values(plant, assertionCreator)

}
