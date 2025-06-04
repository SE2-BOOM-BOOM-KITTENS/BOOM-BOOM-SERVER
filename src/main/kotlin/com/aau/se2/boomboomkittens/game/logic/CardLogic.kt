package com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic

import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.cards.CardDefinition
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

    private fun giveInitialHand(player: Player) {
        // Ziehe 7 Karten vom Stapel
        repeat(7) {
            if (drawPile.isEmpty()) {
                throw IllegalStateException("Nicht genügend Karten im Stapel für Startverteilung!")
            }
            player.playerHand.addCard(drawPile.draw())
        }

        // Gib dem Spieler eine garantierte DEFUSE-Karte
        val defuseCard = Card(CardType.DEFUSE)
        player.playerHand.addCard(defuseCard)
        player.addDefuseCard()  // erhöht logische defuseCount
    }

    fun buildInitialPile(playerSize: Int): CardPile {
        val pile = CardPile()

        // Nur Karten mit Pfote erlauben für 2–3 Spieler, sonst ohne
        val useWithPaw = playerSize <= 3
        val cardDefs = listOf(
            CardDefinition(CardType.DEFUSE, 3, 7), // +1 je Spieler kommt auf die Hand
            CardDefinition(CardType.NOPE, 4, 6),
            //CardDefinition(CardType.FAVOR, 2, 4),
            //CardDefinition(CardType.ATTACK, 4, 7),
            //CardDefinition(CardType.SKIP, 4, 6),
            CardDefinition(CardType.SEE_THE_FUTURE, 3, 3),
            CardDefinition(CardType.ALTER_THE_FUTURE, 2, 4),
            CardDefinition(CardType.SHUFFLE, 2, 4),
            //CardDefinition(CardType.DRAW_FROM_THE_BOTTOM, 3, 4),
            CardDefinition(CardType.FERAL_CAT, 2, 4),
            //CardDefinition(CardType.REVERSE, 2, 3),
            //CardDefintion(CardType.TARGETED_ATTACK, 2, 3),

            // Cat Cards (je 5 Typen)
            CardDefinition(CardType.CAT_TACO, 3, 4),
            CardDefinition(CardType.CAT_BEARD, 3, 4),
            CardDefinition(CardType.CAT_HAIRY_POTATO, 3, 4),
            CardDefinition(CardType.CAT_RAINBOW_RALPHING, 3, 4),
            CardDefinition(CardType.CAT_CATERMELON, 3, 4)
        )

        for (def in cardDefs) {
            val count = if (useWithPaw) def.withPawCount else def.withoutPawCount
            if (def.type != CardType.DEFUSE) {
                repeat(count) {
                    pile.add(Card(def.type))
                }
            }
        }

        // DEFUSE ins Deck: nur restliche nach Verteilung
        val totalDefuses = if (useWithPaw) defOf(CardType.DEFUSE, cardDefs).withPawCount else defOf(CardType.DEFUSE, cardDefs).withoutPawCount
        val defusesForDeck = (totalDefuses - playerSize).coerceAtLeast(0)
        repeat(defusesForDeck) {
            pile.add(Card(CardType.DEFUSE))
        }

        pile.shuffle()
        return pile
    }

    private fun defOf(type: CardType, list: List<CardDefinition>) = list.first { it.type == type }

    fun finalizeDeck(playerSize: Int) {
        repeat(playerSize - 1) {
            drawPile.add(Card(CardType.EXPLODING_KITTEN))
        }
        drawPile.shuffle()
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
