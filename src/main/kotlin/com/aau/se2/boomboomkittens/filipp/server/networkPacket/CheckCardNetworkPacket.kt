package com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.filipp.server.networkPacket

import com.aau.se2.boomboomkittens.game.cards.Card
import java.util.UUID

data class CheckCardNetworkPacket(
    val card: Card,
    val targetId: UUID
) {

}