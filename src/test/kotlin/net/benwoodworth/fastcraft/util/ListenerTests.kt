package net.benwoodworth.fastcraft.util

import net.benwoodworth.fastcraft.ImplementationTests
import net.benwoodworth.fastcraft.dependencies.abstractions.event.Listener
import org.junit.Assert.*
import org.junit.jupiter.api.Test

/**
 * Tests for [Listener].
 */
class ListenerTests : ImplementationTests<Listener<Any?>>() {

    override fun createInstance() = Listener<Any?>()

    @Test
    fun `when adding a handler, it should be notified`() {
        var notifyCount = 0
        val listener: () -> Unit = { notifyCount++ }

        testInstance += listener

        testInstance.notifyHandlers(null)

        assertEquals(1, notifyCount)
    }

    @Test
    fun `when adding a handler twice, should be notified twice`() {
        var notifyCount = 0
        val listener: () -> Unit = { notifyCount++ }

        testInstance += listener
        testInstance += listener

        testInstance.notifyHandlers(null)

        assertEquals(2, notifyCount)
    }

    @Test
    fun `when removing a handler when there are two, should only remove one`() {
        var notifyCount = 0
        val listener: () -> Unit = { notifyCount++ }

        testInstance += listener
        testInstance += listener
        testInstance -= listener

        testInstance.notifyHandlers(null)

        assertEquals(1, notifyCount)
    }
}
