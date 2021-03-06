package ch.tutteli.atrium.spec.integration

import ch.tutteli.atrium.api.cc.en_GB.*
import ch.tutteli.atrium.creating.Assert
import ch.tutteli.atrium.spec.AssertionVerbFactory
import ch.tutteli.atrium.spec.describeFun
import ch.tutteli.atrium.translations.DescriptionCharSequenceAssertion.AT_MOST
import org.jetbrains.spek.api.dsl.SpecBody
import org.jetbrains.spek.api.dsl.context
import org.jetbrains.spek.api.include

abstract class CharSequenceContainsNotOrAtMostAssertionsSpec(
    verbs: AssertionVerbFactory,
    containsNotOrAtMostTriple: Triple<String, (String, String) -> String, Assert<CharSequence>.(Int, Any, Array<out Any>) -> Assert<CharSequence>>,
    containsNotOrAtMostIgnoringCaseTriple: Triple<String, (String, String) -> String, Assert<CharSequence>.(Int, Any, Array<out Any>) -> Assert<CharSequence>>,
    containsNotPair: Pair<String, (Int) -> String>,
    rootBulletPoint: String,
    listBulletPoint: String,
    describePrefix: String = "[Atrium] "
) : CharSequenceContainsSpecBase({

    include(object : SubjectLessAssertionSpec<CharSequence>(describePrefix,
        containsNotOrAtMostTriple.first to mapToCreateAssertion { containsNotOrAtMostTriple.third(this, 2, 2.3, arrayOf()) },
        containsNotOrAtMostIgnoringCaseTriple.first to mapToCreateAssertion { containsNotOrAtMostIgnoringCaseTriple.third(this, 2, 2.3, arrayOf()) }
    ) {})

    include(object : CheckingAssertionSpec<String>(verbs, describePrefix,
        checkingTriple(containsNotOrAtMostTriple.first, { containsNotOrAtMostTriple.third(this, 2, 2.3, arrayOf()) }, "not in there", "2.3,2.3,2.3"),
        checkingTriple(containsNotOrAtMostIgnoringCaseTriple.first, { containsNotOrAtMostIgnoringCaseTriple.third(this, 2, 2.3, arrayOf()) }, "not in there", "2.3,2.3,2.3")
    ) {})

    fun describeFun(vararg funName: String, body: SpecBody.() -> Unit)
        = describeFun(describePrefix, funName, body = body)

    val assert: (CharSequence) -> Assert<CharSequence> = verbs::checkImmediately
    val expect = verbs::checkException
    val fluent = assert(text)
    val fluentHelloWorld = assert(helloWorld)

    val (containsNotOrAtMost, containsNotOrAtMostTest, containsNotOrAtMostFunArr) = containsNotOrAtMostTriple
    fun Assert<CharSequence>.containsNotOrAtMostFun(atLeast: Int, a: Any, vararg aX: Any)
        = containsNotOrAtMostFunArr(atLeast, a, aX)

    val (_, containsNotOrAtMostIgnoringCase, containsNotOrAtMostIgnoringCaseFunArr) = containsNotOrAtMostIgnoringCaseTriple
    fun Assert<CharSequence>.containsNotOrAtMostIgnoringCaseFun(atLeast: Int, a: Any, vararg aX: Any)
        = containsNotOrAtMostIgnoringCaseFunArr(atLeast, a, aX)

    val (containsNot, errorMsgContainsNot) = containsNotPair

    val indentBulletPoint = " ".repeat(rootBulletPoint.length)
    val valueWithIndent = "$indentBulletPoint$listBulletPoint$value"

    describeFun(containsNotOrAtMost) {

        context("throws an $illegalArgumentException") {
            test("for not at all or at most -1 -- only positive numbers") {
                expect {
                    fluent.containsNotOrAtMostFun(-1, "")
                }.toThrow<IllegalArgumentException> { messageContains("positive number", -1) }
            }
            test("for not at all or at most 0 -- points to $containsNot") {
                expect {
                    fluent.containsNotOrAtMostFun(0, "")
                }.toThrow<IllegalArgumentException> { message { toBe(errorMsgContainsNot(0)) } }
            }
            test("if an object is passed as first expected") {
                expect {
                    fluent.containsNotOrAtMostFun(1, fluent)
                }.toThrow<IllegalArgumentException> { messageContains("CharSequence", "Number", "Char") }
            }
            test("if an object is passed as second expected") {
                expect {
                    fluent.containsNotOrAtMostFun(1, "that's fine", fluent)
                }.toThrow<IllegalArgumentException> { messageContains("CharSequence", "Number", "Char") }
            }
        }

        context("text '$helloWorld'") {
            group("happy case with $containsNotOrAtMost once") {
                test("${containsNotOrAtMostTest("'H'", "once")} does not throw") {
                    fluentHelloWorld.containsNotOrAtMostFun(1, 'H')
                }
                test("${containsNotOrAtMostTest("'H' and 'e' and 'W'", "once")} does not throw") {
                    fluentHelloWorld.containsNotOrAtMostFun(1, 'H', 'e', 'W')
                }
                test("${containsNotOrAtMostTest("'W' and 'H' and 'e'", "once")} does not throw") {
                    fluentHelloWorld.containsNotOrAtMostFun(1, 'W', 'H', 'e')
                }
                test("${containsNotOrAtMostTest("'x' and 'y' and 'z'", "twice")} does not throw") {
                    fluentHelloWorld.containsNotOrAtMostFun(2, 'x', 'y', 'z')
                }
                test("${containsNotOrAtMostIgnoringCase("'x' and 'y' and 'z'", "twice")} does not throw") {
                    fluentHelloWorld.containsNotOrAtMostIgnoringCaseFun(2, 'x', 'y', 'z')
                }
            }

            group("failing cases; search string at different positions") {
                test("${containsNotOrAtMostTest("'l'", "once")} throws AssertionError") {
                    expect {
                        fluentHelloWorld.containsNotOrAtMostFun(1, 'l')
                    }.toThrow<AssertionError> { messageContains("$atMost: 1", "$valueWithIndent: 'l'") }
                }
                test("${containsNotOrAtMostTest("'H', 'l'", "once")} throws AssertionError mentioning only 'l'") {
                    expect {
                        fluentHelloWorld.containsNotOrAtMostFun(1, 'H', 'l')
                    }.toThrow<AssertionError> {
                        message {
                            contains("$atMost: 1", "$valueWithIndent: 'l'")
                            containsNot(atLeast, "$valueWithIndent: 'H'")
                        }
                    }
                }
                test("${containsNotOrAtMostTest("'l', 'H'", "once")} once throws AssertionError mentioning only 'l'") {
                    expect {
                        fluentHelloWorld.containsNotOrAtMostFun(1, 'l', 'H')
                    }.toThrow<AssertionError> {
                        message {
                            contains("$atMost: 1", "$valueWithIndent: 'l'")
                            containsNot(atLeast, "$valueWithIndent: 'H'")
                        }
                    }
                }
                test("${containsNotOrAtMostTest("'o', 'E', 'W', 'l'", "once")} throws AssertionError mentioning 'l' and 'o'") {
                    expect {
                        fluentHelloWorld.containsNotOrAtMostIgnoringCaseFun(1, 'o', 'E', 'W', 'l')
                    }.toThrow<AssertionError> {
                        message {
                            contains("$atMost: 1", "$valueWithIndent: 'l'", "$valueWithIndent: 'o'")
                            containsNot(atLeast, "$valueWithIndent: 'E'", "$valueWithIndent: 'W'")
                        }
                    }
                }
            }

            group("multiple occurrences of the search string") {
                test("${containsNotOrAtMostTest("'o'", "once")} throws AssertionError and message contains both, how many times we expected (1) and how many times it actually contained 'o' (2)") {
                    expect {
                        fluentHelloWorld.containsNotOrAtMostFun(1, 'o')
                    }.toThrow<AssertionError> {
                        message {
                            contains(
                                "$rootBulletPoint$containsDescr: $separator" +
                                    "$valueWithIndent: 'o'",
                                "$numberOfOccurrences: 2$separator"
                            )
                            endsWith("$atMost: 1")
                        }
                    }
                }

                test("${containsNotOrAtMostTest("'o'", "twice")} does not throw") {
                    fluentHelloWorld.containsNotOrAtMostFun(2, 'o')
                }
                test("${containsNotOrAtMostIgnoringCase("'o'", "twice")} throws AssertionError") {
                    expect {
                        fluentHelloWorld.containsNotOrAtMostIgnoringCaseFun(2, 'o')
                    }.toThrow<AssertionError> { messageContains(AT_MOST.getDefault()) }
                }

                test("${containsNotOrAtMostTest("'o'", "3 times")} does not throw") {
                    fluentHelloWorld.containsNotOrAtMostFun(3, 'o')
                }
                test("${containsNotOrAtMostTest("'o' and 'l'", "twice")} throws AssertionError and message contains both, how many times we expected (2) and how many times it actually contained 'l' (3)") {
                    expect {
                        fluentHelloWorld.containsNotOrAtMostFun(2, 'o', 'l')
                    }.toThrow<AssertionError> {
                        message {
                            contains(
                                "$rootBulletPoint$containsDescr: $separator" +
                                    "$valueWithIndent: 'l'",
                                "$numberOfOccurrences: 3$separator"
                            )
                            endsWith("$atMost: 2")
                            containsNot("$valueWithIndent: 'o'")
                        }
                    }
                }
                test("${containsNotOrAtMostTest("'l'", "3 times")} does not throw") {
                    fluentHelloWorld.containsNotOrAtMostFun(3, 'l')
                }
                test("${containsNotOrAtMostTest("'o' and 'l'", "3 times")} does not throw") {
                    fluentHelloWorld.containsNotOrAtMostFun(3, 'o', 'l')
                }

            }
        }
    }
})
