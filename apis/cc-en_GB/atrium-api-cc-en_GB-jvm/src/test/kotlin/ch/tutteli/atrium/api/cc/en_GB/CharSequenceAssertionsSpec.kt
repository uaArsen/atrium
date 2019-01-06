package ch.tutteli.atrium.api.cc.en_GB

import ch.tutteli.atrium.creating.Assert
import ch.tutteli.atrium.reporting.translating.Translatable
import ch.tutteli.atrium.verbs.internal.AssertionVerbFactory

class CharSequenceAssertionsSpec : ch.tutteli.atrium.spec.integration.CharSequenceAssertionsSpec(
    AssertionVerbFactory,
    "containsDefaultTranslationOf no longer in this API" to Companion::containsDefaultTranslationOf,
    "containsNotDefaultTranslationOf no longer in this API" to Companion::containsNotDefaultTranslationOf,
    Assert<out CharSequence>::isEmpty.name to Assert<out CharSequence>::isEmpty,
    Assert<out CharSequence>::isNotEmpty.name to Assert<out CharSequence>::isNotEmpty,
    Assert<out CharSequence>::isNotBlank.name to Assert<out CharSequence>::isNotBlank,
    Assert<out CharSequence>::startsWith.name to Assert<out CharSequence>::startsWith,
    Assert<out CharSequence>::startsNotWith.name to Assert<out CharSequence>::startsNotWith,
    Assert<out CharSequence>::endsWith.name to Assert<out CharSequence>::endsWith,
    Assert<out CharSequence>::endsNotWith.name to Assert<out CharSequence>::endsNotWith,
    "◆ ", "⚬ "
){
    companion object{

        fun containsDefaultTranslationOf(plant: Assert<out CharSequence>, translatable: Translatable, arrayOfTranslatables: Array<out Translatable>): Assert<out CharSequence> {
            return plant.contains(translatable.getDefault(), *arrayOfTranslatables.map { it.getDefault() }.toTypedArray())
        }

        fun containsNotDefaultTranslationOf(plant: Assert<out CharSequence>, translatable: Translatable, arrayOfTranslatables: Array<out Translatable>): Assert<out CharSequence> {
            return plant.containsNot(translatable.getDefault(), *arrayOfTranslatables.map { it.getDefault() }.toTypedArray())
        }
    }
}
