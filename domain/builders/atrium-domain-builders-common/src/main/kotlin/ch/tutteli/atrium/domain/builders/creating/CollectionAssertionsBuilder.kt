@file:Suppress("OVERRIDE_BY_INLINE", "NOTHING_TO_INLINE")
package ch.tutteli.atrium.domain.builders.creating

import ch.tutteli.atrium.creating.AssertionPlant
import ch.tutteli.atrium.domain.creating.CollectionAssertions
import ch.tutteli.atrium.domain.creating.collectionAssertions
import ch.tutteli.atrium.core.polyfills.loadSingleService

/**
 * Delegates inter alia to the implementation of [CollectionAssertions].
 * In detail, it implements [CollectionAssertions] by delegating to [collectionAssertions]
 * which in turn delegates to the implementation via [loadSingleService].
 */
object CollectionAssertionsBuilder : CollectionAssertions {

    override inline fun hasSize(plant: AssertionPlant<out Collection<*>>, size: Int)
        = collectionAssertions.hasSize(plant, size)

    override inline fun isEmpty(plant: AssertionPlant<out Collection<*>>)
        = collectionAssertions.isEmpty(plant)

    override inline fun isNotEmpty(plant: AssertionPlant<out Collection<*>>)
        = collectionAssertions.isNotEmpty(plant)
}
