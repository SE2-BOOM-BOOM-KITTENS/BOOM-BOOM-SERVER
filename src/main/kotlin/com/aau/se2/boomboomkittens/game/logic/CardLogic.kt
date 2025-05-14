package com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic

import com.aau.se2.boomboomkittens.game.cards.Card
import com.aau.se2.boomboomkittens.game.cards.CardPile
import com.aau.se2.boomboomkittens.game.cards.CardType
import com.aau.se2.boomboomkittens.game.player.Player
import com.aau.se2.boomboomkittens.game.player.PlayerHand
import java.util.*

class CardLogic(playerSize: Int) {
    private val playerMap = mutableMapOf<UUID, Player>()

    var drawPile: CardPile = buildInitialPile(playerSize)

    fun addCardToPlayer(playerId: UUID, card: Card){
        val player = playerMap[playerId]
        requireNotNull(player){
            throw IllegalArgumentException("Player with id $playerId not found")
        }
        player.playerHand.addCard(card)
    }

    fun addPlayer(player: Player){
        playerMap[player.playerId] = player
        giveInitialHand(player)
    }

    fun getPlayerHand(playerId: UUID): PlayerHand? {
        requireNotNull(playerMap[playerId]){
            throw IllegalArgumentException("Player with id $playerId not found")
        }
        return playerMap[playerId]?.playerHand
    }

    fun removeCardFromPlayer(playerId: UUID, card: Card){
        val player = playerMap[playerId]
            ?: throw IllegalArgumentException("Player with id $playerId not found")
        player.playerHand.removeCard(card)
    }

    fun drawCard(playerId: UUID){
        if (drawPile.isEmpty()) {
            throw IllegalStateException("Cannot draw from empty pile")
        }
        val card = drawPile.draw()
        addCardToPlayer(playerId, card)
    }

    fun giveInitialHand(player: Player){
        player.playerHand.addCard(Card(CardType.BLANK))
        player.playerHand.addCard(Card(CardType.DEFUSE))
    }

    fun buildInitialPile(playerCount: Int): CardPile {
        val pile = CardPile()

        fun add(type: CardType, countWithPaw: Int, countWithoutPaw: Int) {
            val count = if (playerCount <= 3) countWithPaw else countWithoutPaw
            repeat(count) { pile.add(Card(type)) }
        }

        // Regelgemäße Karten, noch nicht alle implementiert
        //add(CardType.FAVOR, 2, 4)
        //add(CardType.NOPE, 4, 6)
        //add(CardType.ATTACK, 4, 7)
        //add(CardType.SKIP, 4, 6)
        //add(CardType.SEE_THE_FUTURE, 3, 3)
        //add(CardType.ALTER_THE_FUTURE, 2, 4)
        //add(CardType.SHUFFLE, 2, 4)
        //add(CardType.DRAW_FROM_BOTTOM, 3, 4)
        //add(CardType.FERAL_CAT, 2, 4)

        // Cat Cards
        val catTypes = listOf(
            CardType.CAT_BEARD,
            CardType.CAT_TACO,
            CardType.CAT_HAIRY_POTATO,
            CardType.CAT_RAINBOW_RALPHING,
            CardType.CAT_CATERMELON
        )
        for (cat in catTypes) {
            add(cat, 3, 4)
        }

        // Defuse-Karten (gesamte Anzahl), später gibst du 1 pro Spieler raus
        repeat(if (playerCount <= 3) 3 else 7) {
            pile.add(Card(CardType.DEFUSE))
        }

        // Exploding Kittens: (Spielerzahl - 1)
        repeat(playerCount - 1) {
            pile.add(Card(CardType.EXPLODING_KITTEN))
        }

        pile.shuffle()
        return pile

        //TODO Insert specific cards based on player count
/*
        val cardPile = CardPile()
        val blank = Card(CardType.BLANK)
        val defuse = Card(CardType.DEFUSE)
        val explodingKitten = Card(CardType.EXPLODING_KITTEN)
        var i = 0
        while (i < 100) {
            cardPile.insertAt(i, blank)
            i++
            cardPile.insertAt(i, defuse)
            i++
            cardPile.insertAt(i, explodingKitten)
            i++
        }

        cardPile.shuffle()

        return cardPile*/

    }

    /**
     * Commented out for rework
     */
//    open fun peekTopCards(count: Int): List<Card> {
//        return drawPile.take(count)
//    }
//    open fun allowPlayerToRearrangeTopCards(player: Player, newOrder: List<Card>) {
//        require(newOrder.size <= drawPile.size) {
//            throw IllegalArgumentException("New order has more cards than the draw pile.")
//        }
//        repeat(newOrder.size) { drawPile.removeFirst() }
//
//        for (i in newOrder.size - 1 downTo 0) {
//            drawPile.insertAt(0,newOrder[i])
//        }
//        println("${player.name} rearranged the top ${newOrder.size} cards.")
//    }

}
