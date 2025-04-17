package com.aau.se2.boomboomkittens.filipp.server.controllers.clientImpl

import org.springframework.messaging.simp.stomp.StompFrameHandler
import org.springframework.messaging.simp.stomp.StompHeaders
import java.lang.reflect.Type
import java.util.concurrent.BlockingQueue

open class StompFrameClient(val messageQueue: BlockingQueue<String>): StompFrameHandler {


    override fun getPayloadType(headers: StompHeaders): Type {
        return String::class.java
    }

    override fun handleFrame(headers: StompHeaders, payload: Any?) {
        (payload as String?)?.let { messageQueue.add(it) }
    }
}