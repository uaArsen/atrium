@file:Suppress("DEPRECATION" /* TODO remove with 1.0.0*/)
package ch.tutteli.atrium.api.cc.infix.en_UK

import ch.tutteli.atrium.api.cc.infix.en_UK.creating.charsequence.contains.builders.AtLeastCheckerOption
import ch.tutteli.atrium.creating.Assert
import ch.tutteli.atrium.domain.creating.charsequence.contains.CharSequenceContains
import ch.tutteli.atrium.domain.creating.charsequence.contains.searchbehaviours.NoOpSearchBehaviour
import kotlin.reflect.KFunction2

abstract class CharSequenceContainsSpecBase {
    private val containsNotFun: KFunction2<Assert<String>, Any, Assert<String>> = Assert<String>::containsNot
    protected val toContain = "${Assert<String>::to.name} ${contain::class.simpleName}"
    protected val notToContain = "${Assert<String>::notTo.name} ${contain::class.simpleName}"
    protected val containsNotValues = "${containsNotFun.name} ${Values::class.simpleName}"
    protected val containsRegex = "${Assert<String>::to.name} ${contain::class.simpleName} ${RegexPatterns::class.simpleName}"
    protected val atLeast = CharSequenceContains.Builder<*, *>::atLeast.name
    protected val butAtMost = AtLeastCheckerOption<*, *>::butAtMost.name
    protected val exactly = CharSequenceContains.Builder<*, *>::exactly.name
    protected val atMost = CharSequenceContains.Builder<*, *>::atMost.name
    protected val notOrAtMost = CharSequenceContains.Builder<*, *>::notOrAtMost.name
    protected val regex = CharSequenceContains.CheckerOption<*, NoOpSearchBehaviour>::regex.name
    protected val defaultTranslationOf = CharSequenceContains.CheckerOption<*, NoOpSearchBehaviour>::defaultTranslationOf.name
    protected val ignoringCase = "${CharSequenceContains.Builder<*, NoOpSearchBehaviour>::ignoring.name} ${case::class.simpleName}"
}
