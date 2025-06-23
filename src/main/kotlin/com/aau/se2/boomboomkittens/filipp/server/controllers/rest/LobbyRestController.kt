package com.aau.se2.boomboomkittens.filipp.server.controllers.rest

import com.aau.se2.boomboomkittens.game.player.LobbyPlayer
import com.aau.se2.boomboomkittens.game.Lobby
import com.aau.se2.boomboomkittens.filipp.server.services.LobbyService
import com.aau.se2.boomboomkittens.game.player.Player
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
class LobbyRestController(private val lobbyService: LobbyService) {

    @GetMapping
    fun getLobbies(): ConcurrentHashMap<String, Lobby> = lobbyService.getLobbies()

    @GetMapping("/players")
    fun getPlayersInLobby(@RequestHeader lobbyId:String): List<Player>{
        val lobby = lobbyService.getLobby(lobbyId)
        if (lobby != null) {
            return lobby.players
        }
        return listOf()
    }

    @PostMapping
    fun createLobby(@RequestBody request: CreateLobbyRequest): CreateLobbyResponse{
        val lobby = lobbyService.createLobby(request.player, request.maxPlayers)
        return CreateLobbyResponse("Created lobby", lobby.id.toString())
    }
    @PostMapping("/{lobbyId}/players")
    fun joinLobby(
        @RequestHeader("lobbyId") lobbyId: String,
        @RequestHeader("playerId") playerId: UUID
    ): String {
        lobbyService.joinLobby(lobbyId, playerId)
        return "Added Player $playerId"
    }
}

data class CreateLobbyRequest(
    val player: Player,
    val maxPlayers: Int
)

data class CreateLobbyResponse(val message: String, val lobbyId: String)
