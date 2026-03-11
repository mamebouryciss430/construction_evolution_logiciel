package org.atlanmod.risk;


import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collections;

import java.util.List;
import java.util.Random;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class RiskModel {

	private static final Logger LOGGER = Logger.getLogger(RiskModel.class.getName());
	private boolean isAI;
	private boolean canTurnInCards;
	private boolean canReinforce;
	private boolean canAttack;
	private boolean canFortify;
	private boolean deployPhase;
	private boolean deployed;
	private boolean isInt;

	private boolean add;


	private int i;


	private int repeat;
	private int playerCount;
	private int playerIndex;
	private int deployTurn;


	private int armies;


	private String countryASelection;


	private ArrayList<String> list;
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

	private ArrayList<Player> players;


	private Random rng;


	private Board board;
	private Deck deck;

	private Player currentPlayer;
	private Country countryA;
	private Country countryB;
	
	/**
	 * This is the constructor for the org.atlanmod.risk.RiskModel object.
	 **/
	protected RiskModel() {
	
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}

	/**
	 * This method handles exiting the game.
	 **/
	protected void quitGame() {
		System.exit(0);
	}
	
	/**
	 * Sets the number of players.
	 * @param playerCount is an integer input by the player in the org.atlanmod.risk.PlayerCountDialog GUI.
	 **/
	protected void setPlayerCount(int playerCount) {
		this.playerCount = playerCount;
	}
	
	/**
	 * Gets the number of players.
	 * @return the number of players in the org.atlanmod.risk.Risk game.
	 **/
	protected int getPlayerCount() {
		return playerCount;
	}

	private String[] readFileToArray(String fileName) throws IOException {
		StringBuilder sb = new StringBuilder();
		try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
			String line;
			while ((line = reader.readLine()) != null) sb.append(line);
		}
		LOGGER.log(Level.INFO, "Input from {0}: {1}", new Object[]{fileName, sb});
		return sb.toString().split("\t");
	}
	private void createPlayers(List<String> names, List<String> types) {
		players = new ArrayList<>();
		for (int k = 0; k < names.size(); k++) {
			boolean aI = "AI".equals(types.get(i));
			players.add(new Player(names.get(i), 50 - (names.size() * 5), i, aI));
		}
	}
	private void initializeGameFlags() {
		deployTurn = -1;
		deployPhase = true;
		deployed = true;
		canTurnInCards = false;
		canReinforce = true;
		canAttack = false;
		canFortify = false;
	}

	/**
	 * Sets up the org.atlanmod.risk.Risk game.
	 * @param playerNames is an ArrayList of the player names.
	 * @param playerTypes is an ArrayList of the player teams.
	 * @return true if the game was successfully initialized
	 **/

	public boolean initializeGame(List<String> playerNames, List<String> playerTypes) throws FileNotFoundException {
		board = new Board();
		try {
			String[] countries = readFileToArray("countries.txt");
			String[] adjacencies = readFileToArray("adjacencies.txt");
			String[] continents = readFileToArray("continents.txt");

			boolean isLoaded = board.loadBoard(countries, adjacencies, continents);
			deck = new Deck(board.getCountries());
			createPlayers(playerNames, playerTypes);
			initializeGameFlags();
			LOGGER.info("Game initialized successfully");
			return isLoaded;
		} catch (FileNotFoundException e) {
			String msg = "Failed to initialize game: required file not found - " + e.getMessage();
			LOGGER.log(Level.SEVERE, msg, e);
			throw new FileNotFoundException(msg);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "IOException: {0}", e.getMessage());
		}
		return false;
	}

	/**
	 * Starts the game and prints out welcome messages.
	 **/
	protected void startGame() {
	
		LOGGER.info("Welcome to RISK, the classic WORLD DOMINATION game!\nEach player rolls a DICE in order to determine the order of turns...");

		Collections.shuffle(players);
		
		LOGGER.info("Here is the order of turns :");
		for (i = 0; i < players.size(); i++) {
			LOGGER.log(Level.INFO,
					"{0} : {1} ",
					new Object[]{(i+1),players.get(i).getName()});

		}
		LOGGER.info("How to begin: Claim territories by selecting them from the list and clicking the 'Place Reinforcements' button.");
		nextPlayer();
	}
	
	/**
	 * Handles turning in org.atlanmod.risk.Risk cards.
	 * @param cardsToRemove is an integer array of the indexes of cards to be removed.
	 **/
	protected void turnInCards(int[] cardsToRemove) {
		if (!canTurnInCards && !isAI) {
			LOGGER.warning("You can't turn in cards right now.");
			return;
		}

		if (cardsToRemove.length != 3) {
			LOGGER.warning("You must trade in three cards of the same type or one of each three types.");
			return;
		}

		if (ownsCountryOnCards(cardsToRemove)) {
			currentPlayer.incrementArmies(2);
			fireCardsUpdate();
		}

		int turnInCount = currentPlayer.getTurnInCount();
		int additionalArmies = turnInCount <= 5 ? 2 + 2 * turnInCount : 15;
		currentPlayer.incrementArmies(additionalArmies);
		fireCardsUpdate();

		currentPlayer.removeCards(cardsToRemove);
		fireCardsUpdate();
	}
	private boolean ownsCountryOnCards(int[] cardsToRemove) {
		for (int cardIndex : cardsToRemove) {
			if (currentPlayer.getHand().get(cardIndex).getCountry().getOccupant().equals(currentPlayer)) {
				return true;
			}
		}
		return false;
	}

	// Helper method to fire property change if not AI
	private void fireCardsUpdate() {
		if (!isAI) {
			pcs.firePropertyChange("cards", null, null);
		}
	}
	private void fireCountryAUpdate() {
		if (!isAI) {
			pcs.firePropertyChange("countryA", null, countryA);
		}
	}


	/**
	 * Handles placing reinforcements.
	 * @param countryAName is a String of the country in which the reinforcements will be placed
	 **/
	protected void reinforce(String countryAName) {
		countryA = board.getCountryByName(countryAName);

		if (!canReinforce && !isAI) {
			LOGGER.warning("Commander, we are unable to send reinforcements right now.");
			return;
		}

		if (countryOccupiedByAnotherPlayer()) return;

		if (allCountriesClaimed()) {
			reinforceClaimedCountry();
			return;
		}

		if (!countryA.hasPlayer()) {
			claimAndDeployCountry();
			return;
		}

		LOGGER.warning("Error: Can't determine reinforce method");
	}

// ---------- Private helpers ----------

	private boolean countryOccupiedByAnotherPlayer() {
		if (countryA.hasPlayer() && !currentPlayer.equals(countryA.getOccupant())) {
			LOGGER.log(Level.WARNING,"You do not occupy {0}", new Object[]{countryA.getName()});
			return true;
		}
		return false;
	}

	private boolean allCountriesClaimed() {
		return deployTurn >= 42;
	}

	private void reinforceClaimedCountry() {
		int armiesToDeploy = getArmiesToDeploy();
		if (armiesToDeploy <= 0) return;

		if (currentPlayer.getArmies() < armiesToDeploy) {
			LOGGER.log(Level.WARNING,
					"You do not have enough armies to reinforce {0} with {1} armies.\nReinforcements available: {2}",
					new Object[]{countryA.getName(), armiesToDeploy, currentPlayer.getArmies()});
			return;
		}

		currentPlayer.decrementArmies(armiesToDeploy);
		countryA.incrementArmies(armiesToDeploy);
		deployed = true;
		LOGGER.log(Level.INFO, "{0} has chosen to reinforce {1} with {2} armies",
				new Object[]{currentPlayer.getName(), countryA.getName(), armiesToDeploy});
		fireCountryAUpdate();

		if (currentPlayer.getArmies() == 0) {
			canAttack = true;
			canFortify = true;
		}
	}

	private int getArmiesToDeploy() {
		if (isAI) {
			rng = new Random();
			int army = rng.nextInt(currentPlayer.getArmies());
			return (currentPlayer.getArmies() > 0 && armies == 0) ? 1 : army;
		} else {
			try {
				return Integer.parseInt(JOptionPane.showInputDialog(
						"Commander, how many armies do you wish to send to reinforce " + countryA.getName() + "?"));
			} catch (NumberFormatException e) {
				LOGGER.warning("Commander, please take this seriously. We are at war.");
				return 0;
			}
		}
	}

	private void claimAndDeployCountry() {
		countryA.setOccupant(currentPlayer);
		currentPlayer.addCountry(countryA);
		countryA.incrementArmies(1);
		currentPlayer.decrementArmies(1);
		deployed = true;
		fireCountryAUpdate();

		if (!isAI) nextPlayer();
	}

	/**
	 * Handles the attack function.
	 * Attacking allows the player to engage in battles, with outcomes decided by RNG, with
	 * opposing players in order to lower the number of armies in a territory to 0 in order
	 * to occupy it.
	 * @param countryAName is a String of the point A country.
	 * @param countryBName is a String of the point B country.
	 **/
	protected void attack(String countryAName, String countryBName) {
		Country attackerCountry = board.getCountryByName(countryAName);
		Country defenderCountry = board.getCountryByName(countryBName);

		if (!canAttack && !isAI) {
			LOGGER.warning("Commander, our forces are not prepared to launch an attack right now.");
			return;
		}

		if (currentPlayer.equals(defenderCountry.getOccupant())) {
			LOGGER.warning("Commander, you cannot attack your own territories.");
			return;
		}

		if (!board.checkAdjacency(attackerCountry.getName(), defenderCountry.getName())) {
			LOGGER.log(Level.INFO,
					"Commander , {0} is not adjacent to {1} .",new Object [] {countryAName,countryBName}
			);
			return;
		}

		Dice dice = new Dice();

		int attackerDice = chooseDice(countryA, 3, true);
		int defenderDice = chooseDice(countryB, 2, false);

		int[] attackerRolls = dice.roll(attackerDice);
		int[] defenderRolls = dice.roll(defenderDice);

		int[] losses = resolveDiceRolls(attackerRolls, defenderRolls);
		applyCombatResults(countryA, countryB, losses[0], losses[1]);

		if (countryB.getArmies() < 1) {
			handleCountryConquest(countryA, countryB);
		}

		canReinforce = false;
	}

	private int chooseDice(Country country, int maxDice, boolean isAttacker) {
		if (country.getOccupant().getAI()) {
			if (isAttacker) {
				return country.getArmies() <= 3 ? 1 : rng.nextInt(2) + 1;
			} else {
				return 1;
			}
		} else {
			return getHumanDiceChoice(country, maxDice, isAttacker);
		}
	}

	private int getHumanDiceChoice(Country country, int maxDice, boolean isAttacker) {
		String role = isAttacker ? "attacking" : "defending";
		String errorMsg = isAttacker
				? "Roll 1, 2 or 3 dice. Must have at least one more army than dice."
				: "Roll 1 or 2 dice. Must have enough armies to roll 2.";

		while (true) {
			try {
				int dice = Integer.parseInt(JOptionPane.showInputDialog(
						country.getOccupant().getName() + ", you are " + role + " " + country.getName() +
								"! How many dice will you roll?"));
				if (dice < 1 || dice > maxDice || dice >= country.getArmies()) throw new IllegalArgumentException();
				return dice;
			} catch (Exception e) {
				LOGGER.log(Level.WARNING, errorMsg, e);
			}
		}
	}


	private int[] resolveDiceRolls(int[] attackerRolls, int[] defenderRolls) {
		int attackerLosses = 0;
		int defenderLosses = 0;

		if (attackerRolls[0] > defenderRolls[0]) defenderLosses++;
		else if (attackerRolls[0] < defenderRolls[0]) attackerLosses++;

		if (attackerRolls.length > 1 && defenderRolls.length > 1) {
			if (attackerRolls[1] > defenderRolls[1]) defenderLosses++;
			else if (attackerRolls[1] < defenderRolls[1]) attackerLosses++;
		}

		return new int[]{attackerLosses, defenderLosses};
	}

	private void applyCombatResults(Country countryA, Country countryB, int attackerLosses, int defenderLosses) {
		countryA.decrementArmies(attackerLosses);
		countryB.decrementArmies(defenderLosses);
		LOGGER.warning("<COMBAT REPORT>");
	}

	private void handleCountryConquest(Country countryA, Country countryB) {
		LOGGER.log(Level.INFO,"WORLD NEWS: {0} has conquered {1} !",new Object [] {countryA.getOccupant().getName(),countryB.getName()});
		countryB.getOccupant().removeCountry(countryB.getName());
		countryA.getOccupant().addCountry(countryB);

		if (countryB.getOccupant().getOwnedCountries().isEmpty()) {
			LOGGER.log(Level.INFO,"WORLD NEWS: {0} has surrendered !",new Object [] {countryA.getOccupant().getName()});
			players.remove(countryB.getOccupant().getIndex());
		}

		countryB.setOccupant(countryA.getOccupant());
		countryA.decrementArmies(1);
		countryB.incrementArmies(1);

		fireCountryAUpdate();
	}

	/**
	 * Handles the fortify function.
	 * Fortifying allows the player to move armies from one country to another occupied 
	 * country once per turn.
	 * @param countryAName is a String of the point A country.
	 * @param countryBName is a String of the point B country.
	 **/
	protected void fortify(String countryAName, String countryBName) {	
	
		countryA = board.getCountryByName(countryAName);
		countryB = board.getCountryByName(countryBName);
		
		if (canFortify == true || currentPlayer.getAI() == true) {
		
			if (currentPlayer.equals(countryA.getOccupant()) && currentPlayer.equals(countryB.getOccupant())) {
			// Check player owns countryA and countryB
				if (board.checkAdjacency(countryAName, countryBName) == true) {
				// Check if countryA and countryB are adjacent
					isInt = false;
					
					if (isAI == true ) {
					// If current player is AI
						rng = new Random();
						System.out.println(countryA.getArmies());
						armies = rng.nextInt(countryA.getArmies());
						if (countryA.getArmies() > 0 && armies == 0) {
							armies = 1;
						}
					} else {
					// If current player is Human
						try {
						// org.atlanmod.risk.Player inputs how many armies to move from country A to country B
							armies = Integer.parseInt(JOptionPane.showInputDialog("Commander, how many armies from " + countryAName + " do you wish to send to fortify " + countryBName + "?"));
							isInt = true;
							
						} catch (NumberFormatException e) {
							System.out.println("Commander, please take this seriously. We are at war.");
						}
					}
					// Decrements armies in country A and increments armies in country B
					if (isInt == true || currentPlayer.getAI() == true) {
						
						if (countryA.getArmies() >= armies) {
							System.out.println(currentPlayer.getName() + " has chosen to fortify " + countryBName + " with " + armies + " armies from " + countryAName + ".");
							
							countryA.decrementArmies(armies);
							countryB.incrementArmies(armies);
							fireCountryAUpdate();
							nextPlayer();
							
						} else {
							System.out.println("Commander, you do not have enough armies in " + countryAName + " to fortify " + countryBName + " with " + armies + " armies.\nNumber of armies in " + countryAName + ": " + countryA.getArmies());
						}
					}
				} else {
					System.out.println("Commander, " + countryAName + " is not adjacent to " + countryBName + ".");
				}
			} else {
				System.out.println("Commander, you do not occupy both " + countryAName + " and " + countryBName + ".");
			}
		} else {
			System.out.println("Commander, we can't relocate troops right now.");
		}
	}
	
	/**
	 * Handles turn transitions in both the deploy phase and game phase.
	 **/
	protected void nextPlayer() {
  int noArmiesCount;
	
		if (players.size() > 1) {
		// If at least one player remains
			if (deployed == true) {
			// Prevents players from skipping turns during the deploy phase
				// Prevents actions between turn transitions
				canTurnInCards = false;
				canReinforce = false;
				canAttack = false;
				canFortify = false;
				playerIndex++;
				
				if (playerIndex >= players.size()) {
				// Loops player index back to 0 when it exceeds the number of players
					playerIndex = 0;
				}
				currentPlayer = players.get(playerIndex);
				isAI = currentPlayer.getAI();
				noArmiesCount = 0;
				
				for(i = 0; i < players.size(); i++) {
				
					if (players.get(i).getArmies() == 0) {
					// Used to determine when to end the deploy phase
						noArmiesCount++;
					}
					if (deployPhase == true && noArmiesCount == players.size()) {
						deployPhase = false;
						deployed = true;
						System.out.println("\n=== The deploy phase has ended! ===\nWhat to do:\n1. Get new armies by turning in matching cards\n2. Attack and conquer neighbor territories.\n3. End your turn by fortifying a country with armies from another occupied country.\nGood luck, commander!");
					}
				}
				if (deployPhase == false) {
				// If game phase is active
					// Draw card
					System.out.println("\n===" + currentPlayer.getName().toUpperCase() +  "===");
					currentPlayer.addRiskCard(deck.draw());	
					
					if (currentPlayer.getOwnedCountries().size() < 12) {
					// Increment armies based on the number of territories occupied
						currentPlayer.incrementArmies(3);
						
					} else {
						currentPlayer.incrementArmies(currentPlayer.getOwnedCountries().size() / 3);
					}
					for (i = 0; i < board.getContinents().size(); i++) {
					// Check continent ownership for bonus armies
						if (currentPlayer.getOwnedCountries().containsAll(board.getContinents().get(i).getMemberCountries())) {
						// If the current player's list of owned territories contains all the territories within a continent
							currentPlayer.incrementArmies(board.getContinents().get(i).getBonusArmies());
							System.out.println(currentPlayer.getName() + " has received " + board.getContinents().get(i).getBonusArmies() + " bonus reinforcements from controlling " + board.getContinents().get(i).getName() + "!");
						}
					}
					System.out.println(currentPlayer.getName() + "'s turn is ready!\nReinforcements available: " + currentPlayer.getArmies());
					deployed = true;
					
					if (isAI == true) {
					// Current player is AI
						System.out.println("***turnAI-Game");
						turnAI();
						nextPlayer();
						
					} else {
					// Current player is human
						while(currentPlayer.mustTurnInCards()) {
						// While player has 5 or more cards
							System.out.println("Commander, your hand is full. Trade in cards for reinforcements to continue.");
						}
						canTurnInCards = true;
						canReinforce = true;
						

						pcs.firePropertyChange("cards", null, null);

						pcs.firePropertyChange("countryA", null, countryA);
					}
				} else if (deployPhase == true) {
				// If deploy phase is active
					deployTurn++;
					
					if (currentPlayer.getArmies() == 0) {
						nextPlayer();
					} else {
						deployed = false;
						System.out.println("\n===" + currentPlayer.getName().toUpperCase() +  "===\n" + currentPlayer.getName() + "'s turn is ready! (Deploy Phase)\nReinforcements available: " + currentPlayer.getArmies());
						
						pcs.firePropertyChange("countryA", null, countryA);
						
						if (isAI == true) {
						// Current player is AI
							System.out.println("**turnAI-Deploy");
							turnAI();
							nextPlayer();
						} else {
							canReinforce = true;
						}
					}
				}
			} else {
				System.out.println("Commander, you must place your reinforcements during the deploy phase.");
			}
		} else {
		
		}
	}
	
	/**
	 * Handles the AI's use of the game functions.
	 **/
	protected void turnAI() {
  ArrayList<Country> priorityTargets;
  ArrayList<Country> priorityCountries;
  int[] cards;
  int r2;
  int r1;
  int r;
  int j;
  int k;
  boolean add2;
  boolean add1;
	
		rng = new Random();
		
		if (deployPhase == false) {
		// If game phase is active
			// AI turnInCards
			//System.out.println("**AI turnInCards - start");
			cards = new int[3];
			for (i = 0; i < currentPlayer.getHand().size(); i++) {
				
				for (j = 0; j < currentPlayer.getHand().size(); j++) {
					
					for (k = 0; k < currentPlayer.getHand().size(); k++) {
						
						if (currentPlayer.getHandObject().canTurnInCards(i, j, k) == true) {
							cards[0] = i;
							cards[1] = j;
							cards[2] = k;
							turnInCards(cards);
							LOGGER.info("**AI attempted to turn in cards");
						}
					}
				}
			}
			//System.out.println("**AI turnInCards - end");
		}
		
		// AI reinforce
		priorityCountries = new ArrayList<Country>();
		
		if (deployTurn < 42) {
		// If unoccupied countries remain
			for (i = 0; i < board.getUnoccupied().size(); i++) {
				
				for (j = 0; j < board.getUnoccupied().get(i).getAdjacencies().size(); j++) {
					
					if (board.getUnoccupied().get(i).getAdjacencies().get(j).hasPlayer() == true) {
						
						if (board.getUnoccupied().get(i).getAdjacencies().get(j).getOccupant().equals(currentPlayer)) {
							add = true;
						}
					}
				}
				if (add == true) {
					priorityCountries.add(board.getUnoccupied().get(i));
				}
			}
			if (priorityCountries.size() > 0) {
				r = rng.nextInt(priorityCountries.size());
				reinforce(priorityCountries.get(r).getName());
			} else {
				r = rng.nextInt(board.getUnoccupied().size());
				reinforce(board.getUnoccupied().get(r).getName());
			}
		} else {
		// If all countries are occupied
			//System.out.println("**AI reinforce - start");
			for (i = 0; i < currentPlayer.getOwnedCountries().size(); i++) {
				add = false;
				
				for (j = 0; j < currentPlayer.getOwnedCountries().get(i).getAdjacencies().size(); j++) {
					
					if (!currentPlayer.getOwnedCountries().get(i).getAdjacencies().get(j).getOccupant().equals(currentPlayer)) {
						add = true;
					}
				}
				if (add == true) {
					priorityCountries.add(currentPlayer.getOwnedCountries().get(i));
				}
			}
			if (priorityCountries.size() > 0) {
			
				do {
				// 70% chance to repeat action
					r = rng.nextInt(priorityCountries.size());
					reinforce(priorityCountries.get(r).getName());
					repeat = rng.nextInt(9);
				} while (repeat >= 3 && currentPlayer.getArmies() > 0 && currentPlayer.getArmies() > 0);
			}
			//System.out.println("**AI reinforce - end");
			
			if(deployPhase == false) {
			// If game phase is active
			
				// AI attack
				//System.out.println("**AI attack - start");
				
					
				do {
				// 50% chance to repeat action
					priorityCountries = new ArrayList<Country>();
					
					for (i = 0; i < currentPlayer.getOwnedCountries().size(); i++) {
						add = false;
						
						for (j = 0; j < currentPlayer.getOwnedCountries().get(i).getAdjacencies().size(); j++) {
							
							if (currentPlayer.getOwnedCountries().get(i).getArmies() > 2 && !currentPlayer.getOwnedCountries().get(i).getAdjacencies().get(j).getOccupant().equals(currentPlayer)) {
							// If priority country has more than 2 armies and has an adjacent, enemy country
								add = true;
							}
						}
						if (add == true) {
							priorityCountries.add(currentPlayer.getOwnedCountries().get(i));
						}
					}
					//System.out.println("**AI attack - Created priorityCountries list");
					
					if (priorityCountries.size() > 0) {
						r1 = rng.nextInt(priorityCountries.size());
						priorityTargets = new ArrayList<Country>();
						
						for (i = 0; i < priorityCountries.get(r1).getAdjacencies().size(); i++) {
						
							if (!priorityCountries.get(r1).getAdjacencies().get(i).getOccupant().equals(currentPlayer.getName())) {
								priorityTargets.add(priorityCountries.get(r1).getAdjacencies().get(i));
							}
						}
						//System.out.println("**AI attack - Created priorityTargets list");
						
						if (priorityTargets.size() > 0) {
							r2 = rng.nextInt(priorityTargets.size());
							//System.out.println("**AI attack - Attacking...");
							attack(priorityCountries.get(r1).getName(), priorityTargets.get(r2).getName());
							//System.out.println("**AI attack - Successful attack");
							repeat = rng.nextInt(9);
						}
					}
				} while (repeat >= 5 && currentPlayer.getArmies() > 0);
				
				//System.out.println("**AI attack - end");
				
				// AI fortify
				//System.out.println("**AI fortify - start");
				priorityTargets = new ArrayList<Country>();
				
				for (i = 0; i < currentPlayer.getOwnedCountries().size(); i++) {
					add1 = false;
					add2 = false;
					
					for (j = 0; j < currentPlayer.getOwnedCountries().get(i).getAdjacencies().size(); j++) {
					// Checks if country has both adjacent friendly and enemy countries
						if (!currentPlayer.getOwnedCountries().get(i).getAdjacencies().get(j).getOccupant().equals(currentPlayer)) {
						// If priority country has an adjacent, enemy country
							add1 = true;
							
						} else if (currentPlayer.getOwnedCountries().get(i).getAdjacencies().get(j).getOccupant().equals(currentPlayer)) {
							add2 = true;
						}
					}
					if (add1 == true && add2 == true) {
						priorityTargets.add(currentPlayer.getOwnedCountries().get(i));
					}
				}
				//System.out.println("**AI fortify - Created priorityTargets list");
				
				if (priorityTargets.size() > 0) {
					priorityCountries = new ArrayList<Country>();
					r1 = rng.nextInt(priorityTargets.size());
					
					for (i = 0; i < priorityTargets.get(r1).getAdjacencies().size(); i++) {

						if (priorityTargets.get(r1).getAdjacencies().get(i).getOccupant().equals(currentPlayer) && priorityTargets.get(r1).getAdjacencies().get(i).getArmies() > 1 ) {
							priorityCountries.add(priorityTargets.get(r1).getAdjacencies().get(i));
						}
					}
					if (priorityCountries.size() > 0) {
						//System.out.println("**AI fortify - Created priorityCountries list");
						r2 = rng.nextInt(priorityCountries.size());
						//System.out.println("**AI fortify - Fortifying...");
						fortify(priorityCountries.get(r2).getName(), priorityTargets.get(r1).getName());
						//System.out.println("**AI fortify - Successful fortify");
					}
				}
				//System.out.println("**AI fortify - end");
			}
		}
	}
	
	/**
	 * Creates and returns the information for the cardsList in the org.atlanmod.risk.BoardView.
	 * @return a list of Strings to be displayed in the cardsList.
	 **/
	protected ArrayList<String> getCardsList() {
	
		list = new ArrayList<String>();
		
		for (i = 0; i < currentPlayer.getHand().size(); i++) {	
		
			list.add(currentPlayer.getHand().get(i).getName());
		}
		return list;
	}
	
	/**
	 * Creates and returns the information for the countryAList in the org.atlanmod.risk.BoardView.
	 * @return a list of Strings to be displayed in the countryAList.
	 **/
	protected ArrayList<String> getCountryAList() {
	
		list = new ArrayList<String>();
		
		if (deployTurn >= 42) {
		
			for (i = 0; i < currentPlayer.getOwnedCountries().size(); i++) {
				list.add(currentPlayer.getOwnedCountries().get(i).getArmies() + "-" + currentPlayer.getOwnedCountries().get(i).getName());
			}
		} else {
			for (i = 0; i < board.getUnoccupied().size(); i++) {
				list.add(board.getUnoccupied().get(i).getName());
			}
		}
		return list;
	}
	
	/** 
	 * Creates and returns the information for the countryBList in the org.atlanmod.risk.BoardView.
	 * @return a list of Strings to be displayed in the countryBList.
	 **/
	protected ArrayList<String> getCountryBList() {
	
		list = new ArrayList<String>();
		
		for (i = 0; i < board.getCountries().size(); i++) {
		
			if (board.checkAdjacency(countryASelection, board.getCountries().get(i).getName())) {
				list.add(board.getCountries().get(i).getArmies() + "-" + board.getCountries().get(i).getName());
			}
		}
		return list;
	}
	
	/**
	 * Receives information on which country is selected in countryAList.
	 * @param country String of the selected country
	 **/
	protected void setCountryASelection(String country) {
		countryASelection = country;

		pcs.firePropertyChange("countryB", null, countryB);
	}
	
	protected Board getBoard() {
		return board;
	}
}