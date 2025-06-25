package com.aau.se2.boomboomkittens.game.logic

import com.aau.se2.boomboomkittens.game.player.Player
import com.aau.se2.boomboomkittens.game.Lobby
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.filipp.server.services.GameLogicService
import com.aau.se2.boomboomkittens.game.cards.Card
import com.aau.se2.boomboomkittens.game.cards.CardType
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.filipp.server.networkPacket.CheckCardNetworkPacket
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.*
import kotlin.test.assertFailsWith




@SpringBootTest
class GameLogicServiceTest {

    private lateinit var player: Player
    private lateinit var player2: Player
    private lateinit var lobby: Lobby
    private lateinit var card: Card



    @Autowired
    lateinit var service: GameLogicService

    @BeforeEach
    fun setup() {
        player = Player(name = "Alice")
        player2 = Player(name = "Bob")
        lobby = Lobby(UUID.randomUUID(), player, mutableListOf(player, player2), 4)
        service.createGame(lobby)

        card = Card(type = CardType.FAVOR, name = "FavorCard")
        player.playerHand.addCard(card)
    }

    @Test
    fun `playCards with valid card`() {
        service.playCards(lobby.id, player.playerId, card)
    }

//    @Test
//    fun `playCards with null card`() {
//        service.playCards(lobby.id, player.playerId, null)
//    }

    @Test
    fun `pass ends turn and sends game state`() {
        service.pass(lobby.id, player.playerId)
    }

    @Test
    fun `exitPlayer removes player`() {
        service.exitPlayer(lobby.id, player.playerId)
    }

    @Test
    fun `getInitState sends game state`() {
        service.getInitState(lobby.id, player.playerId)
    }

    @Test
    fun `getPlayerHand sends hand`() {
        service.getPlayerHand(lobby.id, player.playerId)
    }

    @Test
    fun `joinGame adds player and sends state`() {
        val newPlayerId = UUID.randomUUID()
        service.joinGame(lobby.id, newPlayerId, "Charlie")
    }

    @Test
    fun `explodePlayer removes and notifies`() {
        service.explodePlayer(lobby.id, player.playerId)
    }

//    @Test
//    fun `checkIfDuplicate detects cheating`() {
//        val duplicate = Card(id = card.id, type = card.type, name = card.name, cheatDuplicated = true)
//        player2.playerHand.addCard(duplicate)
//
//        val packet = CheckCardNetworkPacket(targetId = player2.playerId, card = duplicate.id)
//        service.checkIfDuplicate(lobby.id, player.playerId, packet)
//    }

//    @Test
//    fun `checkIfDuplicate wrong accusation`() {
//        val legit = Card(id = UUID.randomUUID(), type = card.type, name = card.name)
//        player2.playerHand.addCard(legit)
//
//        val packet = CheckCardNetworkPacket(targetId = player2.playerId, card = legit.id)
//        service.checkIfDuplicate(lobby.id, player.playerId, packet)
//    }

//    @Test
//    fun `checkIfDuplicate with invalid payload`() {
//        service.checkIfDuplicate(lobby.id, player.playerId, "invalid_payload")
//    }

//    @Test
//    fun `cheatDuplicate duplicates card`() {
//        val cardId = card.id
//        service.cheatDuplicate(lobby.id, player.playerId, mapOf("id" to cardId))
//    }

    @Test
    fun `playCatCombo executes without crash`() {
        val comboCards = listOf(
            Card(type = CardType.CAT_TACO),
            Card(type = CardType.CAT_TACO)
        )
        comboCards.forEach { player.playerHand.addCard(it) }
        service.playCatCombo(lobby.id, player.playerId, comboCards, player2.playerId)
    }

    @Test
    fun `chooseFromDiscard returns card if available`() {
        val discardCard = Card(CardType.ATTACK)
        val game = service.getGame(lobby.id)
        game.cardLogic.discardPile.getPileList().add(discardCard)

        service.chooseFromDiscard(lobby.id, player.playerId, CardType.ATTACK)
    }

    @Test
    fun `chooseFromDiscard no card available`() {
        service.chooseFromDiscard(lobby.id, player.playerId, CardType.ATTACK)
    }

    @Test
    fun `shuffleDeck works`() {
        service.shuffleDeck(lobby.id, player.playerId)
    }

    @Test
    fun `sendGameCreated sends confirmation`() {
        service.sendGameCreated(lobby.id, player.playerId)
    }

    @Test
    fun `getGame throws when not found`() {
        assertFailsWith<IllegalArgumentException> {
            service.getGame(UUID.randomUUID())
        }
    }
}