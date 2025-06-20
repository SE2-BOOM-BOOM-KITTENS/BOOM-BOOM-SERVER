package com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.filipp.server.playerHandshake

import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.stereotype.Component
import org.springframework.web.socket.WebSocketHandler
import org.springframework.web.socket.server.HandshakeInterceptor
import org.springframework.web.util.UriComponentsBuilder
import java.lang.Exception
import java.util.UUID

@Component
class PlayerHandshakeInterceptor : HandshakeInterceptor {
    override fun beforeHandshake(
        request: ServerHttpRequest,
        response: ServerHttpResponse,
        wsHandler: WebSocketHandler,
        attributes: MutableMap<String, Any>
    ): Boolean {
        val queryParams = UriComponentsBuilder.fromUri(request.uri).build().queryParams
        val idParam = queryParams.getFirst("id") ?: return false
        try{
            val id = UUID.fromString(idParam)
            attributes["id"] = id
        }catch (e: IllegalArgumentException){
            println("Invalid UUID: $idParam")
            return false
        }

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