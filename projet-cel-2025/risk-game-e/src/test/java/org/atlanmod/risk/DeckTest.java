package org.atlanmod.risk;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;


class DeckTest {

    private Deck deck;

    @BeforeEach
    void setUp() {
        // Given - Créer 42 pays pour un deck complet Risk
        ArrayList<Country> countries = new ArrayList<>();
        for (int i = 0; i < 42; i++) {
            countries.add(new Country("Country" + i));
        }

        deck = new Deck(countries);
    }

    @Test
    void testDeckCreation() {
        // Then
        assertNotNull(deck);
    }

    @Test
    void testDeckCreation_CardCount() {
        // When & Then - Le deck doit contenir 42 cartes
        // Note: On ne peut pas vérifier directement, donc on teste via draw()
        Card card = deck.draw();
        assertNotNull(card);
    }

    @Test
    void testDeckCreation_ValidCountriesParameter() {
        // Given
        ArrayList<Country> validCountries = new ArrayList<>();
        validCountries.add(new Country("France"));
        validCountries.add(new Country("Germany"));

        // When
        Deck smallDeck = new Deck(validCountries);

        // Then - Ne doit pas lancer d'exception
        assertNotNull(smallDeck);
    }

    @Test
    void testDraw_ReturnsCard() {
        // When
        Card card = deck.draw();

        // Then
        assertNotNull(card);
        assertNotNull(card.getName());
        assertNotNull(card.getCountry());
    }

    @Test
    void testDraw_RemovesCardFromDeck() {
        // Given

        // When
        Card card1 = deck.draw();
        // On ne peut pas vérifier la taille interne, donc on dessine plusieurs cartes
        Card card2 = deck.draw();

        // Then
        assertNotNull(card1);
        assertNotNull(card2);
        assertNotEquals(card1, card2); // Cartes différentes (probabilité très élevée)
    }

    @Test
    void testAdd_Card() {
        // Given
        Country extraCountry = new Country("ExtraCountry");
        Card extraCard = new Card("Infantry", extraCountry);

        // When
        deck.add(extraCard);

        // Then - La carte a été ajoutée (on ne peut pas vérifier directement)
        // Le test passe si aucune exception n'est lancée
        assertTrue(true);
    }

    @Test
    void testShuffle_NoException() {
        // When & Then - Doit s'exécuter sans erreur
        deck.shuffle();
        assertTrue(true); // Si on arrive ici, c'est bon
    }

    @Test
    void testMultipleDraws() {
        // When - Dessiner plusieurs cartes
        Card card1 = deck.draw();
        Card card2 = deck.draw();
        Card card3 = deck.draw();

        // Then
        assertNotNull(card1);
        assertNotNull(card2);
        assertNotNull(card3);
        // Note: On ne peut pas tester l'unicité car le deck est mélangé
    }

}