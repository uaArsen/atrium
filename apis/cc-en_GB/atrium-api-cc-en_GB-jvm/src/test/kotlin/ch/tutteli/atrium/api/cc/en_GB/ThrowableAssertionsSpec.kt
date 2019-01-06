package ch.tutteli.atrium.api.cc.en_GB

import ch.tutteli.atrium.verbs.internal.AssertionVerbFactory
import ch.tutteli.atrium.creating.Assert
import ch.tutteli.atrium.domain.creating.throwable.thrown.ThrowableThrown

class ThrowableAssertionsSpec : ch.tutteli.atrium.spec.integration.ThrowableAssertionsSpec(
    AssertionVerbFactory,
    getToThrowTriple(),
    getMessagePair(),
    Companion::messageWithContainsFun,
    getMessageContainsPair()
) {

    companion object {

        private fun getToThrowTriple(): Triple<String, ThrowableThrown.Builder.() -> Unit, ThrowableThrown.Builder.(assertionCreator: Assert<out Throwable>.() -> Unit) -> Unit>
            = Triple("toThrow", Companion::toThrowImmediate, Companion::toThrowLazy)

        private fun toThrowImmediate(builder: ThrowableThrown.Builder) {
            builder.toThrow<IllegalArgumentException>{}
        }

        private fun toThrowLazy(builder: ThrowableThrown.Builder, assertionCreator: Assert<out Throwable>.() -> Unit) {
            builder.toThrow<IllegalArgumentException>(assertionCreator)
        }

        private fun getMessagePair()
            = Assert<out Throwable>::message.name to Assert<out Throwable>::message

        private fun messageWithContainsFun(plant: Assert<out Throwable>, expected: Any)
            = plant.message { contains(expected) }

        private fun getMessageContainsPair()
            = Assert<out Throwable>::messageContains.name to Companion::messageContains

        private fun messageContains(plant: Assert<out Throwable>, expected: Any, otherExpected: Array<out Any>)
            = plant.messageContains(expected, *otherExpected)
    }
}
