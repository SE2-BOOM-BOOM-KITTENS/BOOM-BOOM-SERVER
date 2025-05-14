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

    fun giveInitialHand(player: Player){
        player.playerHand.addCard(Card(CardType.BLANK))
        player.playerHand.addCard(Card(CardType.DEFUSE))
    }

    fun buildInitialPile(playerCount: Int): CardPile {
        //TODO Insert specific cards based on player count

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

        return cardPile
    }

}