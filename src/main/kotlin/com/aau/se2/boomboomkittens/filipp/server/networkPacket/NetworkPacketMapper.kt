package com.aau.se2.boomboomkittens.filipp.server.networkPacket

import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic.CardLogic
import com.aau.se2.boomboomkittens.game.cards.CardPile
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic.GameLogic
import com.aau.se2.boomboomkittens.game.cards.Card
import com.aau.se2.boomboomkittens.game.player.Player
import com.aau.se2.boomboomkittens.game.player.PlayerHand

class NetworkPacketMapper {
    fun gameStateToNetworkPacket(gameLogic: GameLogic, cardLogic: CardLogic): GameStateNetworkPacket {
        val playerLogic = gameLogic.getPlayerLogic()
        val playerList = playerLogic.getPlayerList()
        val playerNetworkPackets = mutableListOf<PlayerNetworkPacket>()

        for(player in playerList){
            val playerHand = player.playerHand
            playerNetworkPackets.add(playerToDTO(player, playerHand))
        }

        val currentPlayer = playerLogic.getCurrentPlayer()
        val currentPlayerHand = currentPlayer!!.playerHand
        val currentPlayerDTO = playerToDTO(currentPlayer,currentPlayerHand)

        val nextPlayer = playerLogic.getCurrentPlayerNode()!!.next!!.player
        val nextPlayerHand = nextPlayer.playerHand
        val nextPlayerDTO = playerToDTO(nextPlayer,nextPlayerHand)

        val winner = gameLogic.getWinner()
        var winnerDTO: PlayerNetworkPacket? = null
        if(winner != null) {
            val winnerHand = winner.playerHand
            winnerDTO = playerToDTO(gameLogic.getWinner(), winnerHand)
        }

        val drawPile = cardPileToDTO(cardLogic.drawPile,true)
        val discardPile = cardPileToDTO(gameLogic.getDiscardPile(),false)


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

    private fun playerToDTO(player: Player?, playerHand: PlayerHand): PlayerNetworkPacket {
        val id = player!!.playerId
        val name = player.name
        val cardCount = playerHand.getCardAmount()
        return PlayerNetworkPacket(id,name,cardCount)
    }

    private fun cardPileToDTO(cardPile: CardPile, isDrawPile: Boolean): CardPileNetworkPacket {
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

    private fun playerHandToNetworkPacket(playerHand: PlayerHand): PlayerHandNetworkPacket {
        val playerId = playerHand.playerId

        val cards = playerHand.cards
        val cardsDTO : MutableList<CardNetworkPacket> = mutableListOf()
        for(card in cards){
            cardsDTO.add(cardToNetworkPacket(card))
        }
        return PlayerHandNetworkPacket(playerId,cardsDTO)
    }
}