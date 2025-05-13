package com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic

import com.aau.se2.boomboomkittens.game.cards.Card
import com.aau.se2.boomboomkittens.game.cards.CardPile
import com.aau.se2.boomboomkittens.game.cards.CardType
import com.aau.se2.boomboomkittens.game.player.Player
import com.aau.se2.boomboomkittens.game.player.PlayerNode
import java.util.*

class CardLogic {
    private val playerMap = mutableMapOf<UUID, PlayerNode>()
    val players: MutableList<Player> = mutableListOf()
    var drawPile: CardPile = buildInitialPile(players.size)

    fun addCardToPlayer(playerId: UUID, card: Card){
        val player = playerMap[playerId]?.player
        requireNotNull(player){
            throw IllegalArgumentException("Player with id $playerId not found")
        }
        player.playerHand.addCard(card)
    }

    fun removeCardFromPlayer(playerId: UUID, card: Card){
        val player = playerMap[playerId]?.player
        requireNotNull(player){
            throw IllegalArgumentException("Player with id $playerId not found")
        }
        player.playerHand.removeCard(card)
    }

    fun drawCard(playerId: UUID){
        check(!(drawPile.isEmpty())){
            throw IllegalStateException("Cannot draw from empty pile")
        }
        val card = drawPile.draw()
        addCardToPlayer(playerId, card)
    }

    fun buildInitialPile(playerCount: Int): CardPile {
        val pile = CardPile()

        fun add(type: CardType, countWithPaw: Int, countWithoutPaw: Int) {
            val count = if (playerCount <= 3) countWithPaw else countWithoutPaw
            repeat(count) { pile.add(Card(type)) }
        }

        /*// Regelgemäße Karten, noch nicht alle implementiert
        add(CardType.FAVOR, 2, 4)
        add(CardType.NOPE, 4, 6)
        add(CardType.ATTACK, 4, 7)
        add(CardType.SKIP, 4, 6)
        add(CardType.SEE_THE_FUTURE, 3, 3)
        add(CardType.ALTER_THE_FUTURE, 2, 4)
        add(CardType.SHUFFLE, 2, 4)
        add(CardType.DRAW_FROM_BOTTOM, 3, 4)
        add(CardType.FERAL_CAT, 2, 4)*/

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
    }

}