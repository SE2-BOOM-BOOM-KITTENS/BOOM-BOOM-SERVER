import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.*
import java.util.concurrent.atomic.AtomicBoolean

@OptIn(ExperimentalCoroutinesApi::class)
class TimeoutLogicTest {

    private val testDispatcher = StandardTestDispatcher()
    private val lobbyId = UUID.randomUUID()
    private val playerId = UUID.randomUUID()

    @Test
    fun `ejectPlayer is called after timeout`() = runTest {
        val wasEjected = AtomicBoolean(false)
        val timeoutLogic = TimeoutLogic(
            ejectPlayer = { _, _ -> wasEjected.set(true) },
            timeoutSeconds = 3,
            scope = this // use the test scope
        )

        timeoutLogic.start(lobbyId, playerId)

        advanceTimeBy(3000)
        runCurrent()

        assertTrue(wasEjected.get(), "Player should have been ejected after timeout")
    }

    @Test
    fun `cancel prevents ejectPlayer from being called`() = runTest(testDispatcher) {
        val wasEjected = AtomicBoolean(false)

        val timeoutLogic = TimeoutLogic(
            ejectPlayer = { _, _ -> wasEjected.set(true) },
            timeoutSeconds = 3
        )

        timeoutLogic.start(lobbyId, playerId)
        timeoutLogic.cancel(lobbyId)

        advanceTimeBy(3000)
        runCurrent()

        assertFalse(wasEjected.get(), "Player should not be ejected after cancel")
    }
}
