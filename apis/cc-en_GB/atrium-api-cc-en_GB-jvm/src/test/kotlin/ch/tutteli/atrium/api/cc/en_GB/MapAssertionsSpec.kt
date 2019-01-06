package ch.tutteli.atrium.api.cc.en_GB

import ch.tutteli.atrium.verbs.internal.AssertionVerbFactory
import ch.tutteli.atrium.creating.Assert
import ch.tutteli.atrium.verbs.internal.assert
//
//object MapAssertionsSpec : ch.tutteli.atrium.spec.integration.MapAssertionsSpec(
//    AssertionVerbFactory,
//    Assert<Map<String, Int>>::contains.name to Assert<Map<String, Int>>::contains,
//    Assert<Map<String?, Int?>>::containsNullable.name to Assert<Map<String?, Int?>>::containsNullable,
//    Assert<out Map<String, *>>::containsKey.name to Assert<out Map<String, *>>::containsKey,
//    "${Assert<out Map<String?, *>>::containsKey.name} for nullable" to Assert<out Map<String?, *>>::containsKey,
//    Assert<Map<String, Int>>::hasSize.name to Assert<Map<String, Int>>::hasSize,
//    Assert<Map<String, Int>>::isEmpty.name to Assert<Map<String, Int>>::isEmpty,
//    Assert<Map<String, Int>>::isNotEmpty.name to Assert<Map<String, Int>>::isNotEmpty
//)

fun foo(){

    assert(linkedMapOf("a" to 1, "b" to null)).contains(Pair<String, Int?>("a", 1))
    //Using an own data class would work but is ugly -> lower bounds would be better
    assert(linkedMapOf("a" to 1, "b" to 1)).contains(Pair("a", 1), Pair("b", null))// ato 1, "b" ato null)

//    assert(mapOf("a" to 1, "b" to null)).containsNullable()
//    assert(mapOf("a" to 1, "b" to 2)).containsNullable()
}

