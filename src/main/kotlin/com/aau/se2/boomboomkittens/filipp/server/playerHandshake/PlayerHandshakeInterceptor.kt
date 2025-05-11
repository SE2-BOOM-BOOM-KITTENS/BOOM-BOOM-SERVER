package com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.filipp.server.playerHandshake

import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.web.socket.WebSocketHandler
import org.springframework.web.socket.server.HandshakeInterceptor
import org.springframework.web.util.UriComponentsBuilder
import java.lang.Exception
import java.util.UUID

class PlayerHandshakeInterceptor : HandshakeInterceptor {
    override fun beforeHandshake(
        request: ServerHttpRequest,
        response: ServerHttpResponse,
        wsHandler: WebSocketHandler,
        attributes: Map<String, Any>
    ): Boolean {
        val queryParams = UriComponentsBuilder.fromUri(request.uri).build().queryParams
        val name = queryParams.getFirst("name") ?: return false
        val playerId = UUID.randomUUID()
        (attributes as MutableMap<String, Any>)["playerId"] = playerId
        attributes["name"] = name
        return true
    }

    override fun afterHandshake(
        request: ServerHttpRequest,
        response: ServerHttpResponse,
        wsHandler: WebSocketHandler,
        exception: Exception?
    ) {
    }
}