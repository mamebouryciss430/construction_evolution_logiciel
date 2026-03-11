package org.atlanmod.risk;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private Player player;
    private Country country1;
    private Country country2;
    private Card card1;
    private Card card2;
    private Card card3;

    @BeforeEach
    void setUp() {
        player = new Player("Alice", 10, 1, false);
        country1 = new Country("France");
        country2 = new Country("Germany");
        card1 = new Card("Infantry", country1);
        card2 = new Card("Cavalry", country2);
        card3 = new Card("Artillery", country1);
    }

    @Test
    void testInitialValues() {
        assertEquals("Alice", player.getName());
        assertEquals(1, player.getIndex());
        assertEquals(10, player.getArmies());
        assertFalse(player.getAI());
        assertTrue(player.getOwnedCountries().isEmpty());
        assertTrue(player.getHand().isEmpty());
    }

    @Test
    void testIncrementAndDecrementArmies() {
        player.incrementArmies(5);
        assertEquals(15, player.getArmies());

        player.decrementArmies(3);
        assertEquals(12, player.getArmies());
    }

    @Test
    void testAddAndRemoveCountries() {
        player.addCountry(country1);
        player.addCountry(country2);

        List<Country> countries = player.getOwnedCountries();
        assertEquals(2, countries.size());
        assertTrue(countries.contains(country1));
        assertTrue(countries.contains(country2));

        player.removeCountry("France");
        countries = player.getOwnedCountries();
        assertEquals(1, countries.size());
        assertFalse(countries.contains(country1));
        assertTrue(countries.contains(country2));
    }

    @Test
    void testAddAndRemoveRiskCards() {
        player.addRiskCard(card1);
        player.addRiskCard(card2);
        player.addRiskCard(card3);

        List<Card> hand = player.getHand();
        assertEquals(3, hand.size());
        assertTrue(hand.contains(card1));
        assertTrue(hand.contains(card2));
        assertTrue(hand.contains(card3));

        // On enlève les cartes (indices 0,1,2)
        player.removeCards(new int[]{0, 1, 2});
        assertTrue(player.getHand().isEmpty());
    }

    @Test
    void testTurnInCount() {
        int first = player.getTurnInCount();
        int second = player.getTurnInCount();
        assertEquals(first + 1, second);
    }

    @Test
    void testMustTurnInCards() {
        assertFalse(player.mustTurnInCards());
        player.addRiskCard(card1);
        player.addRiskCard(card2);
        player.addRiskCard(card3);
        assertTrue(player.mustTurnInCards());
    }
}
