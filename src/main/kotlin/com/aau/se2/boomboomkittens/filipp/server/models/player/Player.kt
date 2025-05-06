package com.aau.se2.boomboomkittens.filipp.server.models.player

import com.aau.se2.boomboomkittens.filipp.server.models.cards.Card
import com.aau.se2.boomboomkittens.filipp.server.models.player.playerCircle.PlayerHand
import java.util.UUID

data class Player(val playerId: UUID = UUID.randomUUID(),
                  val name:String,
                  val status: String = "ACTIVE",
                  val playerHand: PlayerHand = PlayerHand(playerId),){
}