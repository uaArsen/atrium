package ch.tutteli.atrium.api.cc.infix.en_GB

import ch.tutteli.atrium.api.cc.infix.en_GB.keywords.ERR_KEYWORD_GIVEN_COLLECTION_ASSUMED
import ch.tutteli.atrium.api.cc.infix.en_GB.keywords.Keyword
import ch.tutteli.atrium.creating.Assert
import ch.tutteli.atrium.creating.AssertionPlant
import ch.tutteli.atrium.creating.AssertionPlantNullable
import ch.tutteli.atrium.domain.builders.AssertImpl
import ch.tutteli.atrium.domain.builders.creating.PleaseUseReplacementException

/**
 * Makes the assertion that [AssertionPlantNullable.subject] is not null and if so, uses [assertionCreator]
 * which could create further assertions which are added as a group.
 *
 * @return Notice, that this assertion function cannot provide a fluent API because it depends on whether the first
 *   assertion ([Assert.subject][AssertionPlant.subject] is not null) holds or not. Define subsequent assertions
 *   via the [assertionCreator] lambda.
 *
 * @throws AssertionError Might throw an [AssertionError] if the assertion made is not correct.
 */
inline infix fun <reified T : Any> AssertionPlantNullable<T?>.notToBeNull(noinline assertionCreator: Assert<T>.() -> Unit) {
    AssertImpl.any.typeTransformation.isNotNull(this, T::class, assertionCreator)
}

/**
 * Makes the assertion that [Assert.subject][AssertionPlant.subject] *is a* [TSub] (the same type or a sub-type) and if so,
 * uses [assertionCreator] which could create further assertions which are added as a group.
 *
 * Notice, that asserting a function type is [flawed](https://youtrack.jetbrains.com/issue/KT-27846).
 * The actual types are ignored as function types erase to Function0,
 * Function1 etc. on byte code level, which means the assertion holds as long as the [Assert.subject][AssertionPlant.subject] is a
 * function and has the same amount of arguments regardless if the types differ. For instance
 * `assert({x: Int -> "hello"}).isA<String -> Unit>{}` holds, even though `(Int) -> String` is clearly not
 * a `(String) -> Unit`.
 *
 * More generally speaking, the [flaw](https://youtrack.jetbrains.com/issue/KT-27826) applies to all generic types.
 * For instance `isA<List<String>>` would only check if the [Assert.subject][AssertionPlant.subject] is a `List` without checking if
 * the entry type is actually `String`. Or in other words
 * `assert(listOf(1, 2)).isA<List<String>>{}` holds, even though `List<Int>` is clearly not a `List<String>`.
 *
 * @return Notice, that this assertion function cannot provide a fluent API because it depends on whether the first
 *   assertion ([Assert.subject][AssertionPlant.subject] *is a* [TSub]) holds or not. Define subsequent assertions
 *   via the [assertionCreator] lambda.
 *
 * @throws AssertionError Might throw an [AssertionError] if the assertion made is not correct.
 */
inline infix fun <reified TSub : Any> Assert<Any>.isA(noinline assertionCreator: AssertionPlant<TSub>.() -> Unit) {
    AssertImpl.any.typeTransformation.isA(this, TSub::class, assertionCreator)
}

/**
 * Makes the assertion that [AssertionPlantNullable.subject] is not null but the [expected] value.
 *
 * Is a shortcut for `notToBeNull { this toBe expected }`
 *
 * @return Notice, that this assertion function cannot provide a fluent API because it depends on whether the first
 *   assertion ([Assert.subject][AssertionPlant.subject] is not null) holds or not.
 *
 * @throws AssertionError Might throw an [AssertionError] if the assertion made is not correct.
 */
inline infix fun <reified T : Any> AssertionPlantNullable<T?>.notToBeNullBut(expected: T) {
    notToBeNull { this toBe expected }
}

@Deprecated(ERR_KEYWORD_GIVEN_COLLECTION_ASSUMED, ReplaceWith("this notToBeNullBut (keyword as Any)"))
@Suppress("UNUSED_PARAMETER", "unused")
infix fun <T: Any> AssertionPlantNullable<T>.notToBeNullBut(keyword: Keyword): Nothing
    = throw PleaseUseReplacementException("this notToBe (keyword as Any)")
