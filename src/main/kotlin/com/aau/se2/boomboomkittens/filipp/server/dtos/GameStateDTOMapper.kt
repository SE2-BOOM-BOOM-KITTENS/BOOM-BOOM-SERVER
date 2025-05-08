package com.aau.se2.boomboomkittens.filipp.server.dtos

import com.aau.se2.boomboomkittens.game.cards.CardPile
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic.GameLogic
import com.aau.se2.boomboomkittens.game.cards.Card
import com.aau.se2.boomboomkittens.game.player.Player
import com.aau.se2.boomboomkittens.game.player.PlayerHand

class GameStateDTOMapper {
    fun gameStateToDTO(gameLogic: GameLogic): GameStateDTO {
        val playerList = gameLogic.playerLogic.getPlayerList()

        val playerDTOs = mutableListOf<PlayerDTO>()
        for(player in playerList){
            val playerHand = gameLogic.playerLogic.getPlayerHand(player.playerId)
            playerDTOs.add(playerToDTO(player, playerHand))
        }

        val currentPlayer = gameLogic.playerLogic.getCurrentPlayer()
        val currentPlayerHand = gameLogic.playerLogic.getPlayerHand(currentPlayer!!.playerId)
        val currentPlayerDTO = playerToDTO(currentPlayer,currentPlayerHand)

        val nextPlayer = gameLogic.playerLogic.getCurrentPlayerNode()!!.next!!.player
        val nextPlayerHand = gameLogic.playerLogic.getPlayerHand(nextPlayer.playerId)
        val nextPlayerDTO = playerToDTO(nextPlayer,nextPlayerHand)

        val winner = gameLogic.getWinner()
        var winnerDTO: PlayerDTO? = null
        if(winner != null) {
            val winnerHand = gameLogic.playerLogic.getPlayerHand(winner.playerId)
            winnerDTO = playerToDTO(gameLogic.getWinner(), winnerHand)
        }

        val drawPile = cardPileToDTO(gameLogic.drawPile,true)
        val discardPile = cardPileToDTO(gameLogic.discardPile,false)


        return GameStateDTO(
            lobbyId = gameLogic.lobbyId,
            playerCount = gameLogic.playerLogic.getPlayerCount(),
            players = playerDTOs,
            currentPlayer = currentPlayerDTO,
            nextPlayer = nextPlayerDTO,
            winner = winnerDTO,
            drawPile = drawPile,
            discardPile = discardPile,)
    }

    private fun playerToDTO(player: Player?, playerHand: PlayerHand): PlayerDTO {
        val id = player!!.playerId
        val name = player.name
        val cardCount = playerHand.getCardAmount()
        return PlayerDTO(id,name,cardCount)
    }

    private fun cardPileToDTO(cardPile: CardPile, isDrawPile: Boolean): CardPileDTO {
        val cardCount = cardPile.size
        var cards : MutableList<CardDTO>? = null
        if(!isDrawPile){
            val list : List<Card> = cardPile.getPileList()
            cards = mutableListOf()
            for(card in list){
                cards.add(cardToDTO(card))
            }
        }
        return CardPileDTO(cardCount,cards)
    }

    private fun cardToDTO(card: Card): CardDTO {
        val name = card.name
        return CardDTO(name)
    }

    private fun playerHandToDTO(playerHand: PlayerHand): PlayerHandDTO {
        val playerId = playerHand.playerId

        val cards = playerHand.cards
        val cardsDTO : MutableList<CardDTO> = mutableListOf()
        for(card in cards){
            cardsDTO.add(cardToDTO(card))
        }
        return PlayerHandDTO(playerId,cardsDTO)
    }
}