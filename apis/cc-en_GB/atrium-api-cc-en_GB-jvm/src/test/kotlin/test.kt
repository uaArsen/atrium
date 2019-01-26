import ch.tutteli.atrium.api.cc.en_GB.*
import ch.tutteli.atrium.core.evalOnce
import ch.tutteli.atrium.creating.Assert
import ch.tutteli.atrium.creating.AssertionPlant
import ch.tutteli.atrium.creating.AssertionPlantNullable
import ch.tutteli.atrium.domain.builders.AssertImpl
import ch.tutteli.atrium.domain.builders.reporting.reporterBuilder
import ch.tutteli.atrium.domain.builders.utils.mapArguments
import ch.tutteli.atrium.reporting.translating.Untranslatable
import ch.tutteli.atrium.verbs.internal.assert
import java.util.*
import kotlin.reflect.KFunction0
import kotlin.reflect.KFunction1
import kotlin.reflect.KProperty0

fun myFun(i: Int): String = if (i > 0) {
    i.toString()
} else {
    "no no no"
}

data class Person2(val firstName: String, val lastName: String, val nickName: String?)

data class AProperty<T>(val property: KProperty0<T>)

class MethodCallBuilder<T : Any> {
    //    operator fun <TFeature: Any> invoke(provider: (T) -> KFunction0<TFeature>): FeatureBuilder<T, TFeature> = TODO()
    operator fun <TFeature : Any> invoke(provider: KFunction0<TFeature>): FeatureBuilder<T, TFeature> = TODO()

    fun withArgs(): MethodCallFinishStep<T> = TODO()
}

class MethodCallFinishStep<T : Any> {
    fun <TFeature : Any> call(provider: (T) -> KFunction0<TFeature>): Assert<T> = TODO()
}

fun <T : Any, TFeature : Any> Assert<T>.funCall(provider: (T) -> KFunction0<TFeature>): FeatureBuilder<T, TFeature> =
    TODO()

//fun <T : Any, TFeature : Any> Assert<T>.feature(provider: (T) -> KFunction0<TFeature>): FeatureBuilder<T, TFeature> = TODO()
//fun <T : Any, TFeature : Any, T1> Assert<T>.feature(provider: (T) -> KFunction1<T1, TFeature>): FeatureBuilder<T, TFeature> = TODO()

class FeatureBuilder<T : Any, TFeature : Any>(
    val plant: AssertionPlant<T>,
    private val provider: () -> TFeature,
    private val args: Array<out Any?>
) {


    infix fun assertIt(assertionCreator: Assert<TFeature>.() -> Unit): Assert<T> {
        val feature = provider.evalOnce()
        val methodCallFormatter = AssertImpl.coreFactory.newMethodCallFormatter()
        val formattedArgs = args.joinToString(separator = ",") { methodCallFormatter.formatArgument(it) }
        return plant.addAssertion(
            AssertImpl.builder.feature
                .withDescriptionAndRepresentation(Untranslatable { formattedArgs }, feature)
                .withAssertion(
                    AssertImpl.collector.collect(plant) {
                        AssertImpl.changeSubject(this) { feature() }.addAssertionsCreatedBy(assertionCreator)
                    }
                )
                .build()
        )
    }
}

class F1Builder<T : Any, T1>(plant: Assert<T>) {
    fun <TFeature : Any> f(f: KFunction1<T1, TFeature>): Assert<TFeature> = TODO()
}

class FeatureProvider<T : Any>(private val plant: Assert<T>) {
    val it = plant.subject

    fun <TFeature : Any> p(provider: KProperty0<TFeature>): Assert<TFeature> = TODO()
    fun <T1 : Any, TFeature : Any> p(
        provider: KProperty0<T1>,
        provider2: FeatureProvider<T1>.() -> Assert<TFeature>
    ): Assert<TFeature> = TODO()

    @JvmName("pNullable")
    fun <R : Any?> p(provider: KProperty0<R>): AssertionPlantNullable<R> = TODO()

    fun <R : Any> f(provider: KFunction0<R>): Assert<R> = TODO()
    fun <R : Any, T1> f(provider: KFunction1<T1, R>, arg1: T1): Assert<R> =
        AssertImpl.feature.returnValueOf1(plant, provider, arg1)

    fun <T1> args(arg: T1): F1Builder<T, T1> = TODO()
//    fun <TFeature, T1> f(provider: KFunction1<T1, TFeature>, arg1: T1): AssertionPlantNullable<TFeature> = TODO()
//    @JvmName("f1Char")
//    fun f(provider: KFunction1<(Char) -> Boolean, Char>, arg1: (Char) -> Boolean): Assert<Char> = TODO()
//    @JvmName("fByte")
//    fun f(provider: KFunction0<Byte>): Assert<Byte> = TODO()

}

//val <T : Any> Assert<T>.feature get(): MethodCallBuilder<T> = TODO()
//fun <T : Any, TFeature : Any> Assert<T>.feature(provider: FBuilder<T>.() -> KProperty0<TFeature>): FeatureBuilder<T, TFeature> = TODO()
fun <T : Any, TFeature : Any> Assert<T>.feature(provider: FeatureProvider<T>.() -> Assert<TFeature>): Assert<TFeature> {
    val f = FeatureProvider(this)
    return f.provider()
}

fun <T : Any, TFeature : Any> Assert<T>.feature(
    provider: FeatureProvider<T>.() -> Assert<TFeature>,
    assertionCreator: Assert<T>.() -> Unit
): Assert<T> = TODO()

fun <T : Any, TFeature : Any?> Assert<T>.featureNullable(
    provider: FeatureProvider<T>.() -> AssertionPlantNullable<TFeature>,
    assertionCreator: AssertionPlantNullable<T>.() -> Unit
): Assert<T> = TODO()


//@JvmName("test")
//fun <T : Any, TFeature : Any> Assert<T>.feature(provider: (T) -> KFunction0<TFeature>): FeatureBuilder<T, TFeature> = TODO()
fun <T : Any> Assert<T>.featureWithArg(): MethodCallBuilder<T> = TODO()

fun <T : Any> foo(f: KFunction0<T>) {}
@JvmName("fooByte")
fun foo(f: KFunction0<Byte>) {
}

fun <T1> bar(t: T1) {
}

fun main2(args: Array<String>) {
    foo("a"::toByte)
    foo(1::toByte)
    //    foo("a"::toInt)
//
    data class Person(val firstName: String, val lastName: String, val nickName: String?) {
        fun name() = "$firstName $lastName"
    }
    assert(Person2("robert", "stoll", null))
        .feature({ p(it::firstName) }) { toBe("hello") }
        .featureNullable({ p(it::nickName) }) { notToBeNullBut("robstoll") }
        .feature { p(it::firstName) }.toBe("hello")
        .apply {
            feature { f(it::chars) }.toBe('a')
            feature { f<Byte>(it::toByte) }.toBe('a')
            feature { f<Char>(it::single) }.toBe('a')
            feature { args({ _: Char -> true }).f<Char>(it::single) }
            feature { f(it::intern) }
            feature { assert("a") }
            Unit
        }

    bar({ _: Char -> true })
}
fun main(args: Array<String>) {

    //Example for data-driven tests #69

    assert("calling myFun with...") {
        mapOf(
            -1 to "1",
            0 to "0",
            1 to "12",
            2 to "32"
        ).forEach { arg, expectedResult ->
            returnValueOf(::myFun, arg).toBe(expectedResult)
        }
    }
//
//                    feature { it::length }
//                    subject.feature{}
//
//                    feature.withArgs().call { it::single }

//            (subject::single)


//            feature { it::startsWith } withArgs(1, 2, 3)
//
//
//            funCall(subject::startsWith).withArgs(1, 2, 3) { toBe(1) }
//            funCall(subject::startsWith).withArgs(1, 2, 3) { toBe(1) }
//
//            funCall { myFun(1,2,3 ) } withArgs (1, 2, 3)
//            funCall({ myFun(1, 2, 3) }, 1, 2, 3)
//            feature("myFun($arg)" to { myFun(arg) }) { toBe(1) }
//                }
//            }
//        }
}


fun Assert<Date>.isBetween(lowerBoundInlc: Date, upperBoundExclusive: Date) = addAssertionsCreatedBy {
    isGreaterOrEquals(lowerBoundInlc)
    isLessThan(upperBoundExclusive)
}

data class Person(
    val firstName: String,
    val lastName: String,
    val children: Collection<Person>,
    val age: Int /* ...  and others */
)

fun Assert<Person>.hasNumberOfChildren(number: Int) =
    property(Person::children).hasSize(number)

fun Assert<Person>.hasAdultChildren() =
    property(Person::children)
        .all { property(Person::age).isGreaterOrEquals(18) }
//        .size.isGreaterThan(1)

val Assert<Person>.children get(): Assert<Collection<Person>> = property(Person::children)
fun Assert<Person>.children(assertionCreator: Assert<Collection<Person>>.() -> Unit): Assert<Person> =
    apply { children.addAssertionsCreatedBy(assertionCreator) }

fun Assert<List<Pair<String, String>>>.sameInitialsAs(
    person: Person, vararg otherPersons: Person
): Assert<List<Pair<String, String>>> {
    val (pair, otherPairs) = mapArguments(person, otherPersons).toAssert<Pair<String, String>> {
        first.startsWith(it.firstName[0].toString())
        second.startsWith(it.lastName[0].toString())
    }
//    AssertImpl.builder.descriptive
//        .withTest {  }
//        .withFailureHint {  }
//        .showOnlyIf {  }.
    return contains.inOrder.only.entries(pair, *otherPairs)
}

fun main3(args: Array<String>) {
    val ownReporter = reporterBuilder
        .withoutTranslationsUseDefaultLocale()
        .withDetailedObjectFormatter()
        .withDefaultAssertionFormatterController()
        .withDefaultAssertionFormatterFacade()
        .withTextSameLineAssertionPairFormatter()
        .withTextCapabilities()
        .withDefaultAtriumErrorAdjusters()
        .withOnlyFailureReporter()
        .build()

    fun <T : Any> assert(t: T, assertionCreator: Assert<T>.() -> Unit): Assert<T> =
        AssertImpl.coreFactory.newReportingPlantAndAddAssertionsCreatedBy(
            Untranslatable("assert"),
            { t },
            ownReporter,
            assertionCreator
        )

    fun expect(act: () -> Unit) =
        AssertImpl.throwable.thrownBuilder(
            Untranslatable("assert"), act, ownReporter
        )
//
//    expect {
//        //this block does something but eventually...
//        throw IllegalArgumentException("name is empty", RuntimeException("a cause"))
//    }.notToThrow()

    //    fun Assert<Person>.hasNumberOfChildren(number: Int) =
//        property(Person::children).hasSize(number)
    fun myFun(i: Int): String = if (i > 0) {
        i.toString()
    } else {
        "no no no"
    }

    assert("calling myFun with...") {
        mapOf(
            -1 to "1",
            0 to "0",
            1 to "12",
            2 to "32"
        ).forEach { arg, expectedResult ->
            val result = myFun(arg)
            addAssertion(AssertImpl.builder.feature
                .withDescriptionAndRepresentation(Untranslatable { arg.toString() }, result)
                .withAssertion(
                    AssertImpl.collector.collect(this) {
                        AssertImpl.changeSubject(this) { result }.toBe(expectedResult)
                    }
                )
                .build()
            )
        }
    }
//    assert(Person("Susanne", "Whitley", listOf(Person("a", "b", listOf(), 15), Person("a", "b", listOf(), 18)), 30)).hasAdultChildren()
//    assert(Date()).isBetween(Date(Long.MIN_VALUE), Date(Long.MAX_VALUE))
}

