package ch.tutteli.atrium.domain.robstoll.lib.creating.charsequence.contains.searchers

import ch.tutteli.atrium.domain.creating.charsequence.contains.CharSequenceContains.Searcher
import ch.tutteli.atrium.domain.creating.charsequence.contains.searchbehaviours.IgnoringCaseSearchBehaviour

/**
 * Represents a [Searcher] which implements the [IgnoringCaseSearchBehaviour] behaviour and evaluates
 * the matches of a given regular expression on the input of the search.
 */
class IgnoringCaseRegexSearcher : Searcher<IgnoringCaseSearchBehaviour> {
    private val searcher = RegexSearcher()

    override fun search(searchIn: CharSequence, searchFor: Any): Int {
        val pattern = Regex(searchFor.toString(), RegexOption.IGNORE_CASE)
        return searcher.search(searchIn, pattern)
    }
}
