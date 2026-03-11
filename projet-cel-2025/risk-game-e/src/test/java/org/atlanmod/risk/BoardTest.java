package org.atlanmod.risk;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


import java.util.List;  // ✅ Ajouter cet import

class BoardTest {

    private Board board;
    private String[] countriesArray;
    private String[] adjacenciesArray;
    private String[] continentsArray;

    @BeforeEach
    void setUp() {
        board = new Board();

        // Données de test pour un mini-monde Risk
        countriesArray = new String[]{"France", "Allemagne", "Italie", "Espagne", "Angleterre"};

        adjacenciesArray = new String[]{
                "France,Allemagne,Italie,Angleterre",
                "Allemagne,France,Italie",
                "Italie,France,Allemagne,Espagne",
                "Espagne,Italie",
                "Angleterre,France"
        };

        continentsArray = new String[]{
                "Europe,5,France,Allemagne,Italie,Espagne,Angleterre",
                "Mediterranee,3,Italie,Espagne"
        };
    }

    @Test
    void testBoardInitialization() {
        assertNotNull(board);
    }

    @Test
    void testLoadBoard_Success() {
        // When
        boolean result = board.loadBoard(countriesArray, adjacenciesArray, continentsArray);

        // Then
        assertTrue(result);
    }

    @Test
    void testLoadBoard_CountriesLoaded() {
        // Given
        board.loadBoard(countriesArray, adjacenciesArray, continentsArray);

        // When
        List<Country> countries = board.getCountries();  // ✅ Changé ArrayList → List

        // Then
        assertNotNull(countries);
        assertEquals(5, countries.size());
        assertTrue(containsCountryNamed(countries, "France"));
        assertTrue(containsCountryNamed(countries, "Allemagne"));
    }

    @Test
    void testLoadBoard_ContinentsLoaded() {
        // Given
        board.loadBoard(countriesArray, adjacenciesArray, continentsArray);

        // When
        List<Continent> continents = board.getContinents();  // ✅ Changé ArrayList → List

        // Then
        assertNotNull(continents);
        assertEquals(2, continents.size());
    }

    @Test
    void testGetCountryByName_ExistingCountry() {
        // Given
        board.loadBoard(countriesArray, adjacenciesArray, continentsArray);

        // When
        Country france = board.getCountryByName("France");

        // Then
        assertNotNull(france);
        assertEquals("France", france.getName());
    }

    @Test
    void testGetCountryByName_NonExistingCountry() {
        // Given
        board.loadBoard(countriesArray, adjacenciesArray, continentsArray);

        // When
        Country unknown = board.getCountryByName("PaysInexistant");

        // Then
        assertNull(unknown);
    }

    @Test
    void testGetContinentByName_ExistingContinent() {
        // Given
        board.loadBoard(countriesArray, adjacenciesArray, continentsArray);

        // When
        Continent europe = board.getContinentByName("Europe");

        // Then
        assertNotNull(europe);
        assertEquals("Europe", europe.getName());
    }

    @Test
    void testGetContinentByName_NonExistingContinent() {
        // Given
        board.loadBoard(countriesArray, adjacenciesArray, continentsArray);

        // When
        Continent unknown = board.getContinentByName("ContinentInexistant");

        // Then
        assertNull(unknown);
    }

    @Test
    void testGetBonusArmies_ValidContinent() {
        // Given
        board.loadBoard(countriesArray, adjacenciesArray, continentsArray);

        // When
        int bonus = board.getBonusArmies("Europe");

        // Then
        assertEquals(5, bonus);
    }

    @Test
    void testGetMemberCountries() {
        // Given
        board.loadBoard(countriesArray, adjacenciesArray, continentsArray);

        // When
        List<Country> europeMembers = board.getMemberCountries("Europe");  // ✅ Changé
        List<Country> mediterraneeMembers = board.getMemberCountries("Mediterranee");  // ✅ Changé

        // Then
        assertEquals(5, europeMembers.size());
        assertEquals(2, mediterraneeMembers.size());
    }

    @Test
    void testGetAdjacencies() {
        // Given
        board.loadBoard(countriesArray, adjacenciesArray, continentsArray);

        // When
        List<Country> frenchAdjacencies = board.getAdjacencies("France");  // ✅ Changé

        // Then
        assertNotNull(frenchAdjacencies);
        assertEquals(3, frenchAdjacencies.size()); // Allemagne, Italie, Angleterre
        assertTrue(containsCountryNamed(frenchAdjacencies, "Allemagne"));
        assertTrue(containsCountryNamed(frenchAdjacencies, "Italie"));
    }

    @Test
    void testCheckAdjacency_AdjacentCountries() {
        // Given
        board.loadBoard(countriesArray, adjacenciesArray, continentsArray);

        // When
        boolean result = board.checkAdjacency("France", "Allemagne");

        // Then
        assertTrue(result);
    }

    @Test
    void testCheckAdjacency_NonAdjacentCountries() {
        // Given
        board.loadBoard(countriesArray, adjacenciesArray, continentsArray);

        // When
        boolean result = board.checkAdjacency("France", "Espagne");

        // Then
        assertFalse(result);
    }

    @Test
    void testSetAndGetOccupant() {
        // Given
        board.loadBoard(countriesArray, adjacenciesArray, continentsArray);
        Player player = new Player("Joueur1", 10, 1, false);

        // When
        board.setOccupant("France", player);
        Player occupant = board.getOccupant("France");

        // Then
        assertEquals(player, occupant);
    }

    @Test
    void testSetAndGetNumberArmies() {
        // Given
        board.loadBoard(countriesArray, adjacenciesArray, continentsArray);

        // When
        board.setNumberArmies("France", 7);
        int armies = board.getNumberArmies("France");

        // Then
        assertEquals(7, armies);
    }

    @Test
    void testGetUnoccupied_AllUnoccupiedInitially() {
        // Given
        board.loadBoard(countriesArray, adjacenciesArray, continentsArray);

        // When
        List<Country> unoccupied = board.getUnoccupied();  // ✅ Changé

        // Then
        assertEquals(5, unoccupied.size()); // Tous les pays sont non occupés initialement
    }

    @Test
    void testGetUnoccupied_SomeOccupied() {
        // Given
        board.loadBoard(countriesArray, adjacenciesArray, continentsArray);
        Player player = new Player("Joueur1", 10, 1, false);
        board.setOccupant("France", player);
        board.setOccupant("Allemagne", player);

        // When
        List<Country> unoccupied = board.getUnoccupied();  // ✅ Changé

        // Then
        assertEquals(3, unoccupied.size()); // 2 pays occupés, 3 non occupés
        assertFalse(containsCountryNamed(unoccupied, "France"));
        assertFalse(containsCountryNamed(unoccupied, "Allemagne"));
    }

    @Test
    void testGetCountries_ReturnsAllCountries() {

        board.loadBoard(countriesArray, adjacenciesArray, continentsArray);

        List<Country> countries = board.getCountries();  // ✅ Changé

        assertEquals(5, countries.size());
        assertTrue(containsCountryNamed(countries, "France"));
        assertTrue(containsCountryNamed(countries, "Espagne"));
    }

    @Test
    void testGetContinents_ReturnsAllContinents() {

        board.loadBoard(countriesArray, adjacenciesArray, continentsArray);

        List<Continent> continents = board.getContinents();

        // Then
        assertEquals(2, continents.size());
        assertTrue(containsContinentNamed(continents, "Europe"));
        assertTrue(containsContinentNamed(continents, "Mediterranee"));
    }

    @Test
    void testLoadBoard_EmptyArrays() {
        // Given
        String[] emptyCountries = new String[]{};
        String[] emptyAdjacencies = new String[]{};
        String[] emptyContinents = new String[]{};

        boolean result = board.loadBoard(emptyCountries, emptyAdjacencies, emptyContinents);

        assertTrue(result);
        assertEquals(0, board.getCountries().size());
        assertEquals(0, board.getContinents().size());
    }

    @Test
    void testLoadBoard_NullArrays() {
        // When & Then
        assertThrows(NullPointerException.class, () -> board.loadBoard(null, adjacenciesArray, continentsArray));
    }

    private boolean containsCountryNamed(List<Country> countries, String name) {
        return countries.stream().anyMatch(country -> country.getName().equals(name));
    }

    private boolean containsContinentNamed(List<Continent> continents, String name) {
        return continents.stream().anyMatch(continent -> continent.getName().equals(name));
    }
}