package com.aau.se2.boomboomkittens.filipp.server.models.player.playerCircle

import com.aau.se2.boomboomkittens.filipp.server.models.cards.Card
import java.util.Random
import java.util.UUID

class PlayerHand(
    val playerId: UUID,
    val cards: MutableList<Card> = mutableListOf(),
) {

    fun addCard(card: Card){
        cards.add(card)
    }

    fun removeCard(card: Card){
        cards.remove(card)
    }

    fun getRandomCard(seed: Long? = null): Card? {
        if (cards.isEmpty()){
            return null
        }
        if(seed != null){
            return cards.shuffled(Random(seed)).first()
        }
        return cards.shuffled().first()
    }

    fun getCardAmount(): Int{
        return cards.size
    }
}