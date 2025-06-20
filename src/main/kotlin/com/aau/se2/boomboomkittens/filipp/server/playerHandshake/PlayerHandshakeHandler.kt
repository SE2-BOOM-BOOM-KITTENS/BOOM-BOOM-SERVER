package com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.filipp.server.playerHandshake

import com.aau.se2.boomboomkittens.filipp.server.services.PlayerService
import org.springframework.http.server.ServerHttpRequest
import org.springframework.stereotype.Component
import org.springframework.web.socket.WebSocketHandler
import org.springframework.web.socket.server.support.DefaultHandshakeHandler
import java.security.Principal
import java.util.UUID

@Component
class PlayerHandshakeHandler(
    private val playerService: PlayerService
) : DefaultHandshakeHandler() {
    override fun determineUser(
        request: ServerHttpRequest,
        wsHandler: WebSocketHandler,
        attributes: Map<String?, Any?>
    ): Principal {
        println(attributes["id"])
        val playerId = attributes["id"] as UUID
        val name = playerService.getPlayer(playerId)!!.name
        return UserPrincipal(playerId, name)
    }
}

class UserPrincipal(private val id: UUID,private val name: String) : Principal {
    fun getUserName(): String = name
    override fun getName(): String = id.toString()
}