package com.aau.se2.boomboomkittens.game.player

import com.aau.se2.boomboomkittens.game.cards.Card
import com.aau.se2.boomboomkittens.game.cards.CardType
import java.util.UUID

class PlayerHand(
    val playerId: UUID,
    val cards: MutableList<Card> = mutableListOf(),
) {

    fun addCard(card: Card){
        cards.add(card)
    }

    fun removeCard(card: Card){
        val toRemove = cards.firstOrNull { it.type == card.type }
        if (toRemove != null) {
            cards.remove(toRemove)
        }
    }

    fun getRandomCard(): Card? {
        if (cards.isEmpty()){
            return null
        }
        return cards.shuffled().first()
    }

    fun getCardById(cardId: UUID): Card {
        return cards.first { it.id == cardId }
    }

    fun containsCard(card: Card): Boolean {
        return cards.any { it.type == card.type }
    }

    fun containsCardType(cardType: CardType): Boolean {
        return cards.any { it.type == cardType }
    }

    fun getCardAmount(): Int{
        return cards.size
    }
}