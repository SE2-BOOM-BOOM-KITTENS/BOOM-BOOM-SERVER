package com.aau.se2.boomboomkittens.game.cards.effects

import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic.GameLogic
import com.aau.se2.boomboomkittens.filipp.server.networkPacket.messages.CatComboMessage
import com.aau.se2.boomboomkittens.filipp.server.dtos.messages.ComboType
import com.aau.se2.boomboomkittens.game.cards.Card
import com.aau.se2.boomboomkittens.game.player.Player

object CatComboEffectHandler {

    fun handleCombo(player: Player, cards: List<Card>, game: GameLogic): CatComboMessage? {
        if (cards.isEmpty() || cards.any { !isCatCard(it) }) return null

        return when {
            isTwoSame(cards) -> {
                val opponents = game.playerLogic.getPlayerList()
                    .filter { it.playerId != player.playerId && it.playerHand.cards.isNotEmpty() }

                val target = opponents.randomOrNull() ?: return null
                val stolen = target.playerHand.getRandomCard() ?: return null

                target.playerHand.removeCard(stolen)
                player.playerHand.addCard(stolen)

                discardPlayedCards(player, cards, game)

                CatComboMessage(
                    type = ComboType.RANDOM_STEAL,
                    fromPlayerId = player.playerId,
                    toPlayerId = target.playerId,
                    cardName = stolen.name
                )
            }

            isThreeSame(cards) -> {
                discardPlayedCards(player, cards, game)
                CatComboMessage(
                    type = ComboType.SPECIFIC_REQUEST,
                    fromPlayerId = player.playerId
                )
            }

            isFiveDifferent(cards) -> {
                val discardPile = game.discardPile
                val topCard = discardPile.getPileList().firstOrNull() ?: return null

                discardPile.getPileList().remove(topCard)
                player.playerHand.addCard(topCard)
                discardPlayedCards(player, cards, game)

                CatComboMessage(
                    type = ComboType.DISCARD_RETRIEVE,
                    fromPlayerId = player.playerId,
                    cardName = topCard.name
                )
            }

            else -> null
        }
    }

    private fun isCatCard(card: Card): Boolean {
        return card.type.name.startsWith("CAT_")
    }

    private fun isTwoSame(cards: List<Card>) =
        cards.size == 2 && cards.all { it.name == cards.first().name }

    private fun isThreeSame(cards: List<Card>) =
        cards.size == 3 && cards.all { it.name == cards.first().name }

    private fun isFiveDifferent(cards: List<Card>) =
        cards.size == 5 && cards.map { it.name }.distinct().size == 5

    private fun discardPlayedCards(player: Player, cards: List<Card>, gameLogic: GameLogic) {
        for (card in cards) {
            player.playerHand.removeCard(card)
            gameLogic.discardPile.insertAt(0, card)
        }
    }
}
