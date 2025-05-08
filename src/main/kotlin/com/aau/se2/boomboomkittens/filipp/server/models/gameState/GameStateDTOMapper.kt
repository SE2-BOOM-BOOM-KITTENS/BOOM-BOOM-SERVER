package com.aau.se2.boomboomkittens.filipp.server.models.gameState

import com.aau.se2.boomboomkittens.filipp.server.models.cards.Card
import com.aau.se2.boomboomkittens.filipp.server.models.cards.CardPile
import com.aau.se2.boomboomkittens.filipp.server.models.dtos.CardDTO
import com.aau.se2.boomboomkittens.filipp.server.models.dtos.CardPileDTO
import com.aau.se2.boomboomkittens.filipp.server.models.dtos.GameStateDTO
import com.aau.se2.boomboomkittens.filipp.server.models.dtos.PlayerDTO
import com.aau.se2.boomboomkittens.filipp.server.models.dtos.PlayerHandDTO
import com.aau.se2.boomboomkittens.filipp.server.models.player.Player
import com.aau.se2.boomboomkittens.filipp.server.models.player.playerCircle.PlayerHand

class GameStateDTOMapper {

    fun gameStateToDTO(gameState: GameState): GameStateDTO{
        val playerList = gameState.playerCircle.getPlayerList()

        val playerDTOs = mutableListOf<PlayerDTO>()
        for(player in playerList){
            val playerHand = gameState.playerCircle.getPlayerHand(player.playerId)
            playerDTOs.add(playerToDTO(player, playerHand))
        }

        val currentPlayer = gameState.playerCircle.getCurrentPlayer()
        val currentPlayerHand = gameState.playerCircle.getPlayerHand(currentPlayer!!.playerId)
        val currentPlayerDTO = playerToDTO(currentPlayer,currentPlayerHand)

        val nextPlayer = gameState.playerCircle.getCurrentPlayerNode()!!.next!!.player
        val nextPlayerHand = gameState.playerCircle.getPlayerHand(nextPlayer.playerId)
        val nextPlayerDTO = playerToDTO(nextPlayer,nextPlayerHand)

        val winner = gameState.getWinner()
        var winnerDTO: PlayerDTO? = null
        if(winner != null) {
            val winnerHand = gameState.playerCircle.getPlayerHand(winner.playerId)
            winnerDTO = playerToDTO(gameState.getWinner(), winnerHand)
        }

        val drawPile = cardPileToDTO(gameState.drawPile,true)
        val discardPile = cardPileToDTO(gameState.discardPile,false)


        return GameStateDTO(
            lobbyId = gameState.lobbyId,
            playerCount = gameState.playerCircle.getPlayerCount(),
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

    private fun cardPileToDTO(cardPile: CardPile,isDrawPile: Boolean): CardPileDTO {
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

    private fun playerHandToDTO(playerHand: PlayerHand): PlayerHandDTO{
        val playerId = playerHand.playerId

        val cards = playerHand.cards
        val cardsDTO : MutableList<CardDTO> = mutableListOf()
        for(card in cards){
            cardsDTO.add(cardToDTO(card))
        }
        return PlayerHandDTO(playerId,cardsDTO)
    }
}