package com.aau.se2.boomboomkittens.filipp.server.config

import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.filipp.server.playerHandshake.PlayerHandshakeHandler
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.filipp.server.playerHandshake.PlayerHandshakeInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer
@Configuration
@EnableWebSocketMessageBroker
open class WebSocketConfig : WebSocketMessageBrokerConfigurer {



    @Override
    override fun configureMessageBroker(config: MessageBrokerRegistry) {
        config.enableSimpleBroker("/topic")
        config.setApplicationDestinationPrefixes("/app")
        config.setUserDestinationPrefix("/user")
    }

    @Override
    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
        registry.addEndpoint("/game")
//            .addInterceptors(PlayerHandshakeInterceptor())
//            .setHandshakeHandler(PlayerHandshakeHandler())
            .setAllowedOrigins("*")
        registry.addEndpoint("/game/").withSockJS()
    }
}