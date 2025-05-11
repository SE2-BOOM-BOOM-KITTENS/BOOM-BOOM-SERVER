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
        cards.remove(card)
    }

    fun getRandomCard(): Card? {
        if (cards.isEmpty()){
            return null
        }
        return cards.shuffled().first()
    }

    fun containsCard(card: Card): Boolean {
        return cards.contains(card)
    }

    fun containsCardType(cardType: CardType): Boolean {
        for(card in cards){
            if(card.type == cardType){
                return true
            }
        }
        return false
    }

    fun getCardAmount(): Int{
        return cards.size
    }
}