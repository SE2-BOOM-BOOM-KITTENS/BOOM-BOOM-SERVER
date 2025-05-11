package com.aau.se2.boomboomkittens.filipp.server.dtos

import com.aau.se2.boomboomkittens.game.cards.CardPile
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic.GameLogic
import com.aau.se2.boomboomkittens.game.cards.Card
import com.aau.se2.boomboomkittens.game.player.Player
import com.aau.se2.boomboomkittens.game.player.PlayerHand

class GameStateDTOMapper {
    fun gameStateToDTO(gameLogic: GameLogic): GameStateDTO {
        val playerList = gameLogic.playerLogic.getPlayerList()
        val cardLogic = gameLogic.cardLogic

        val playerDTOs = mutableListOf<PlayerDTO>()
        for(player in playerList){
            val playerHand = player.playerHand
            playerDTOs.add(playerToDTO(player))
        }

        val currentPlayer = gameLogic.playerLogic.getCurrentPlayer()
        val currentPlayerHand = currentPlayer?.playerHand
        val currentPlayerDTO = playerToDTO(currentPlayer)

        val nextPlayer = gameLogic.playerLogic.getCurrentPlayerNode()!!.next!!.player
        val nextPlayerHand = nextPlayer.playerHand
        val nextPlayerDTO = playerToDTO(nextPlayer)

        val winner = gameLogic.getWinner()
        var winnerDTO: PlayerDTO? = null
        if(winner != null) {
            val winnerHand = winner.playerHand
            winnerDTO = playerToDTO(gameLogic.getWinner())
        }

        val drawPile = cardPileToDTO(cardLogic.drawPile,true)
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

    private fun playerToDTO(player: Player?): PlayerDTO {
        val id = player!!.playerId
        val name = player.name
        val cardCount = player.playerHand.getCardAmount()
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