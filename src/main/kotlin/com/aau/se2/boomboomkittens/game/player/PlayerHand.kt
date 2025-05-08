package com.aau.se2.boomboomkittens.game.player

import com.aau.se2.boomboomkittens.game.cards.Card
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

    fun getRandomCard(): Card? {
        if (cards.isEmpty()){
            return null
        }
        return cards.shuffled().first()
    }

    fun getCardAmount(): Int{
        return cards.size
    }
}