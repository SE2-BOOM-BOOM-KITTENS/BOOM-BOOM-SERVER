package com.aau.se2.boomboomkittens.game.cards.effects

import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.filipp.server.networkPacket.messages.ServerMessage
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic.CardLogic
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic.GameLogic
import com.aau.se2.boomboomkittens.game.cards.Card
import com.aau.se2.boomboomkittens.game.cards.CardType
import com.aau.se2.boomboomkittens.game.player.Player
import java.util.*

class CatComboEffectHandler(private val cardLogic: CardLogic, private val sendToPlayer: (UUID, Any) -> Unit) {

    fun applyCombo(player: Player, cards: List<Card>, target: Player?) {
        val resolvedTypes = cards.map {
            if (it.type == CardType.FERAL_CAT) {
                it.aliasType ?: throw IllegalArgumentException("Feral Cat benötigt aliasType für Kombination.")
            } else {
                it.type
            }
        }

        when {
            resolvedTypes.size == 2 && resolvedTypes.toSet().size == 1 -> {
                requireNotNull(target) { "Zielspieler erforderlich für 2er-Cat-Kombo." }
                stealRandomCard(player, target)
                log("${player.name} stiehlt eine zufällige Karte von ${target.name}.")
            }

            resolvedTypes.size == 3 && resolvedTypes.toSet().size == 1 -> {
                requireNotNull(target) { "Zielspieler erforderlich für 3er-Cat-Kombo." }
                stealDefuseCard(player, target)
                log("${player.name} versucht eine DEFUSE-Karte von ${target.name} zu stehlen.")
            }

            resolvedTypes.size == 5 && resolvedTypes.toSet().size == 5 -> {
                val discardTypes = cardLogic.discardPile.getPileList().map { it.type }.distinct()
                log("${player.name} sieht den Ablagestapel: $discardTypes")
                val msg = ServerMessage(
                    type = "CHOOSE_FROM_DISCARD",
                    message = "Wähle eine Karte aus dem Ablagestapel",
                    gameState = discardTypes
                )
                sendToPlayer(player.playerId, msg)

            }

            else -> {
                log("Ungültige Cat-Kombination von ${player.name}.")
            }
        }


        // Alle Karten zur Ablage hinzufügen
        cards.forEach { cardLogic.discardPile.add(it) }
        }

        private fun stealRandomCard(from: Player, to: Player) {
            val card = to.playerHand.getRandomCard()
            if (card != null) {
                to.playerHand.removeCard(card)
                from.playerHand.addCard(card)
            }
        }

        private fun stealDefuseCard(from: Player, to: Player) {
            val defuseCard = to.playerHand.cards.firstOrNull { it.type == CardType.DEFUSE }
            if (defuseCard != null) {
                to.playerHand.removeCard(defuseCard)
                from.playerHand.addCard(defuseCard)
            }
    }

    private fun log(message: String) {
        println("[CatComboEffectHandler] $message")
    }
}

