package com.aau.se2.boomboomkittens.filipp.server.controllers.rest

import com.aau.se2.boomboomkittens.game.Lobby
import com.aau.se2.boomboomkittens.filipp.server.services.LobbyService
import com.aau.se2.boomboomkittens.filipp.server.services.PlayerService
import com.aau.se2.boomboomkittens.game.player.Player
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap


@RestController
@RequestMapping("/lobbies")
class LobbyRestController(
    private val lobbyService: LobbyService,
    private val playerService: PlayerService) {
    private val logger = LoggerFactory.getLogger(LobbyRestController::class.java)

    @GetMapping
    fun getLobbies(): ConcurrentHashMap<String, Lobby> = lobbyService.getLobbies()

    @GetMapping("/players")
    fun getPlayersInLobby(@RequestHeader lobbyId: String): List<Player> {
        val lobby = lobbyService.getLobby(lobbyId)
        if (lobby != null) {
            return lobby.players
        }
        return listOf()
    }

    @PostMapping
    fun createLobby(@RequestBody request: CreateLobbyRequest): CreateLobbyResponse {
        val player = playerService.getPlayer(request.playerId)
        val lobby = lobbyService.createLobby(player, request.maxPlayers)
        logger.info("Created Lobby: $lobby by Player ${player.playerId}")
        return CreateLobbyResponse("Created lobby", lobby.id.toString())
    }

    @DeleteMapping
    fun deleteLobby(@RequestHeader("lobbyId") id: String):CreateLobbyResponse {
        lobbyService.deleteLobby(id)
        logger.info("Lobby id $id has been deleted")
        return CreateLobbyResponse("Deleted lobby", id.toString())
    }

    @PostMapping("/{lobbyId}/players")
    fun joinLobby(
        @RequestHeader("lobbyId") lobbyId: String,
        @RequestHeader("playerId") playerId: UUID
    ): String {
        val alreadyJoined = getPlayersInLobby(lobbyId).any { it.playerId == playerId }

        return if (alreadyJoined) {
            "Player $playerId already in lobby"
        } else {
            lobbyService.joinLobby(lobbyId, playerId)
            "Added Player $playerId"
        }
    }

    @PostMapping("/{lobbyId}/leave")
    fun leaveLobby(
        @RequestHeader("lobbyId") lobbyId: String,
        @RequestHeader("playerId") playerId: UUID
    ):String {
        val player = playerService.getPlayer(playerId)

        if(player != null) {
            lobbyService.removePlayer(lobbyId, player)
            return "Removed Player $playerId"
        } else{
            return "Player $playerId does not exist"
        }
    }
}


data class CreateLobbyRequest(
    val playerId: UUID,
    val maxPlayers: Int
)

data class CreateLobbyResponse(
    val message: String,
    val lobbyId: String)
