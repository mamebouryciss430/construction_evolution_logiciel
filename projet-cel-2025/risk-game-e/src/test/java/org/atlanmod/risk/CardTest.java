package org.atlanmod.risk;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {

    private Card card;
    private Country country;

    @BeforeEach
    void setUp() {
        // Arrange - Préparation avant chaque test
        country = new Country("France");
        card = new Card("Territory", country);
    }

    @Test
    @DisplayName("getName() devrait retourner le format 'Pays, Type'")
    void getName() {
        // Act
        String result = card.getName();

        // Assert
        assertEquals("France, Territory", result,
                "Le nom devrait être au format 'Pays, Type'");
    }

    @Test
    @DisplayName("getType() devrait retourner le type exact de la carte")
    void getType() {
        // Act
        String result = card.getType();

        // Assert
        assertEquals("Territory", result,
                "Le type devrait être 'Territory'");
    }

    @Test
    @DisplayName("getCountry() devrait retourner la même instance de Country")
    void getCountry() {
        // Act
        Country result = card.getCountry();

        // Assert
        assertSame(country, result,
                "Devrait retourner la même instance de Country");
        assertEquals("France", result.getName(),
                "Le pays devrait avoir le nom 'France'");
    }

    @Test
    @DisplayName("Deux cartes identiques devraient avoir le même comportement")
    void testMultipleCards() {
        // Arrange
        Card card1 = new Card("Territory", country);
        Card card2 = new Card("Mission", country);

        // Act & Assert
        assertEquals("France, Territory", card1.getName());
        assertEquals("France, Mission", card2.getName());
        assertEquals("Territory", card1.getType());
        assertEquals("Mission", card2.getType());
        assertSame(country, card1.getCountry());
        assertSame(country, card2.getCountry());
    }

    @Test
    @DisplayName("Test avec différents pays et types")
    void testWithDifferentCountriesAndTypes() {
        // Arrange
        Country germany = new Country("Germany");
        Country italy = new Country("Italy");

        Card missionCard = new Card("Mission", germany);
        Card wildCard = new Card("Wild", italy);

        // Act & Assert
        assertEquals("Germany, Mission", missionCard.getName());
        assertEquals("Italy, Wild", wildCard.getName());
        assertEquals("Mission", missionCard.getType());
        assertEquals("Wild", wildCard.getType());
        assertEquals("Germany", missionCard.getCountry().getName());
        assertEquals("Italy", wildCard.getCountry().getName());
    }

    @Test
    @DisplayName("Les getters devraient toujours retourner les mêmes valeurs")
    void testGettersConsistency() {
        // Act - Appels multiples
        String name1 = card.getName();
        String name2 = card.getName();
        String type1 = card.getType();
        String type2 = card.getType();
        Country country1 = card.getCountry();
        Country country2 = card.getCountry();

        // Assert - Vérifier la consistance
        assertEquals(name1, name2, "getName() devrait être consistant");
        assertEquals(type1, type2, "getType() devrait être consistant");
        assertSame(country1, country2, "getCountry() devrait retourner la même instance");
    }

    @Test
    @DisplayName("Test avec des types de carte complexes")
    void testWithComplexCardTypes() {
        // Arrange
        Card complexCard = new Card("Special Mission Card", country);

        // Act & Assert
        assertEquals("France, Special Mission Card", complexCard.getName());
        assertEquals("Special Mission Card", complexCard.getType());
    }

    @Test
    @DisplayName("L'objet Card devrait être immuable")
    void testImmutability() {
        // Arrange - Création d'une carte
        Card originalCard = new Card("Territory", country);

        // Act - Stocker les valeurs originales
        String originalName = originalCard.getName();
        String originalType = originalCard.getType();
        Country originalCountry = originalCard.getCountry();

        // Simuler un "passage du temps" - réutiliser les getters
        String newName = originalCard.getName();
        String newType = originalCard.getType();
        Country newCountry = originalCard.getCountry();

        // Assert - Vérifier que rien n'a changé
        assertEquals(originalName, newName, "Le nom ne devrait pas changer");
        assertEquals(originalType, newType, "Le type ne devrait pas changer");
        assertSame(originalCountry, newCountry, "Le pays ne devrait pas changer");
    }
}