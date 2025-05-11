package com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.filipp.server.playerHandshake

import org.springframework.http.server.ServerHttpRequest
import org.springframework.web.socket.WebSocketHandler
import org.springframework.web.socket.server.support.DefaultHandshakeHandler
import java.security.Principal
import java.util.UUID

class PlayerHandshakeHandler : DefaultHandshakeHandler() {
    override fun determineUser(
        request: ServerHttpRequest,
        wsHandler: WebSocketHandler,
        attributes: Map<String?, Any?>
    ): Principal? {
        val playerId = attributes["playerId"] as UUID
        return Principal {playerId.toString()}
    }
}