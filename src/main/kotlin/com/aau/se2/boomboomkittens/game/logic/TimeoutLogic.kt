import kotlinx.coroutines.*
import java.util.*

class TimeoutLogic(
    private val ejectPlayer: (UUID, UUID) -> Unit,
    private val timeoutSeconds: Int = 60,
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Default)
) {
    private var currentTimeoutJob: Job? = null

    fun start(lobbyId: UUID, playerId: UUID) {
        cancel(lobbyId)
        currentTimeoutJob = scope.launch {
            var timeLeft = timeoutSeconds

            while (timeLeft > 0) {
                delay(1000)
                timeLeft--

                if (timeLeft == 20) {
                    println("[$playerId] has 20 seconds left.")
                }
            }

            println("TIMEOUT: [$playerId] ejected.")
            ejectPlayer(lobbyId, playerId)
        }
    }

    fun cancel(lobbyId: UUID) {
        currentTimeoutJob?.cancel()
        currentTimeoutJob = null
    }
}
