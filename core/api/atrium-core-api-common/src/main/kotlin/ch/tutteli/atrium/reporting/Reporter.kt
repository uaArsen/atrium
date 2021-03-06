package ch.tutteli.atrium.reporting

import ch.tutteli.atrium.assertions.Assertion
import ch.tutteli.atrium.core.CoreFactory

/**
 * Represents a reporter which reports about [Assertion]s.
 */
interface Reporter {

    val atriumErrorAdjuster: AtriumErrorAdjuster

    /**
     * Reports about the given [assertion], using the given [sb] where the actual
     * implementation will decide whether the given [assertion] is noteworthy to be reported.
     *
     * For instance, [CoreFactory.newOnlyFailureReporter] will only report failing [Assertion]s.
     *
     * @param sb The [StringBuilder] which can be used for reporting.
     * @param assertion The assertion which should be considered for reporting.
     */
    fun format(assertion: Assertion, sb: StringBuilder)
}
