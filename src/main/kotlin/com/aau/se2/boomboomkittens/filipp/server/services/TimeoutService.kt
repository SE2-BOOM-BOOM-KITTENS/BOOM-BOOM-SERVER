package com.aau.se2.boomboomkittens.filipp.server.services

import TimeoutLogic
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.filipp.server.services.GameLogicService
import org.springframework.stereotype.Service
import java.util.*

@Service
class TimeoutService(
    private val gameLogicService: GameLogicService
) {
    private val timers = mutableMapOf<UUID, TimeoutLogic>()

    fun startTimeout(lobbyId: UUID, playerId: UUID) {
        val timeoutLogic = TimeoutLogic(
            ejectPlayer = { lobbyId, playerId ->
                gameLogicService.exitPlayer(lobbyId, playerId)
            }
        )

        timers[lobbyId] = timeoutLogic

        timeoutLogic.start(lobbyId, playerId)
    }

    fun cancelTimeout(lobbyId: UUID) {
        val timeoutLogic = timers[lobbyId]

        timeoutLogic!!.cancel(lobbyId)
    }
}
