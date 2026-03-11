package org.atlanmod.risk;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;


class ContinentTest {

    private Continent continent;
    private ArrayList<Country> memberCountries;

    @BeforeEach
    void setUp() {
        // Given
        memberCountries = new ArrayList<>();
        memberCountries.add(new Country("France"));
        memberCountries.add(new Country("Allemagne"));
        memberCountries.add(new Country("Italie"));

        continent = new Continent("Europe", 5, memberCountries);
    }

    @Test
    void testContinentCreation() {
        // Then
        assertNotNull(continent);
        assertEquals("Europe", continent.getName());
        assertEquals(5, continent.getBonusArmies());
    }

    @Test
    void testGetName() {
        // When & Then
        assertEquals("Europe", continent.getName());
    }

    @Test
    void testGetBonusArmies() {
        // When & Then
        assertEquals(5, continent.getBonusArmies());
    }

    @Test
    void testGetMemberCountries() {
        // When
        List<Country> countries = continent.getMemberCountries();

        // Then
        assertNotNull(countries);
        assertEquals(3, countries.size());
        assertTrue(containsCountryNamed(countries, "France"));
        assertTrue(containsCountryNamed(countries, "Allemagne"));
    }

    @Test
    void testGetMemberCountries_ReturnsSameInstance() {
        // When
        List<Country> countries = continent.getMemberCountries();

        // Then - devrait retourner la même instance (problème d'encapsulation)
        assertSame(memberCountries, countries);
    }

    @Test
    void testContinentCreation_EmptyCountries() {
        // Given
        ArrayList<Country> emptyCountries = new ArrayList<>();

        // When
        Continent emptyContinent = new Continent("Antarctique", 2, emptyCountries);

        // Then
        assertNotNull(emptyContinent);
        assertEquals(0, emptyContinent.getMemberCountries().size());
    }

    @Test
    void testContinentCreation_NullName() {
        // When & Then
        Continent continentWithNullName = new Continent(null, 3, memberCountries);

        // Then - Vérifier le comportement avec null
        assertNull(continentWithNullName.getName());
    }

    // Méthode utilitaire
    private boolean containsCountryNamed(List<Country> countries, String name) {
        return countries.stream().anyMatch(country -> country.getName().equals(name));
    }
}