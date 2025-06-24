import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class TimeoutLogic(
    private val ejectPlayer: (String) -> Unit,
    private val timeoutSeconds: Int = 60,
    private val coroutineContext: CoroutineContext = Dispatchers.Default
) {
    private var currentTimeoutJob: Job? = null

    fun start(playerId: String) {
        cancel() // Cancel any previous job just in case
        currentTimeoutJob = CoroutineScope(coroutineContext).launch {
            var timeLeft = timeoutSeconds

            while (timeLeft > 0) {
                delay(1000)
                timeLeft--

                if(timeLeft == 20){
                    println("[$playerId] has 20 seconds to finish their turn!")
                }
            }

            println("TIMEOUT: [$playerId] left the game.")
            ejectPlayer(playerId)
        }
    }

    fun cancel() {
        currentTimeoutJob?.cancel()
        currentTimeoutJob = null
    }
}