package com.aau.se2.boomboomkittens.filipp.server.networkPacket

import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic.CardLogic
import com.aau.se2.boomboomkittens.game.cards.CardPile
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic.GameLogic
import com.aau.se2.boomboomkittens.game.cards.Card
import com.aau.se2.boomboomkittens.game.player.Player
import com.aau.se2.boomboomkittens.game.player.PlayerHand

class NetworkPacketMapper {
    fun gameStateToNetworkPacket(gameLogic: GameLogic, cardLogic: CardLogic): GameStateNetworkPacket {
        val playerLogic = gameLogic.playerLogic
        val playerList = playerLogic.getPlayerList()
        val playerNetworkPackets = mutableListOf<PlayerNetworkPacket>()

        for(player in playerList){
            val playerHand = player.playerHand
            playerNetworkPackets.add(playerToNetworkPacket(player, playerHand))
        }

        val currentPlayer = playerLogic.getCurrentPlayer()
        val currentPlayerHand = currentPlayer!!.playerHand
        val currentPlayerDTO = playerToNetworkPacket(currentPlayer,currentPlayerHand)

        val nextPlayer = playerLogic.getCurrentPlayerNode()!!.next!!.player
        val nextPlayerHand = nextPlayer.playerHand
        val nextPlayerDTO = playerToNetworkPacket(nextPlayer,nextPlayerHand)

        val winner = gameLogic.getWinner()
        var winnerDTO: PlayerNetworkPacket? = null
        if(winner != null) {
            val winnerHand = winner.playerHand
            winnerDTO = playerToNetworkPacket(gameLogic.getWinner(), winnerHand)
        }

        val drawPile = cardPileToNetworkPacket(cardLogic.drawPile,true)
        val discardPile = cardPileToNetworkPacket(gameLogic.discardPile,false)


        return GameStateNetworkPacket(
            lobbyId = gameLogic.lobbyId,
            playerCount = playerLogic.getPlayerCount(),
            players = playerNetworkPackets,
            currentPlayer = currentPlayerDTO,
            nextPlayer = nextPlayerDTO,
            winner = winnerDTO,
            drawPile = drawPile,
            discardPile = discardPile,)
    }

    fun playerToNetworkPacket(player: Player?, playerHand: PlayerHand): PlayerNetworkPacket {
        val id = player!!.playerId
        val name = player.name
        val cardCount = playerHand.getCardAmount()
        return PlayerNetworkPacket(id,name,cardCount)
    }

     fun cardPileToNetworkPacket(cardPile: CardPile, isDrawPile: Boolean): CardPileNetworkPacket {
        val cardCount = cardPile.size
        var cards : MutableList<CardNetworkPacket>? = null
        if(!isDrawPile){
            val list : List<Card> = cardPile.getPileList()
            cards = mutableListOf()
            for(card in list){
                cards.add(cardToNetworkPacket(card))
            }
        }
        return CardPileNetworkPacket(cardCount,cards)
    }

    private fun cardToNetworkPacket(card: Card): CardNetworkPacket {
        val name = card.name
        val type = card.type
        return CardNetworkPacket(name,type)
    }
}