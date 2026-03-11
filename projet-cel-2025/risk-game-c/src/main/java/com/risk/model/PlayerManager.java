package com.risk.model;

import com.risk.common.Action;
import com.risk.common.SleepTime;
import com.risk.common.Tool;
import com.risk.view.PhaseView;
import com.risk.view.PlayersWorldDominationView;

import java.io.Serializable;
import java.util.*;

import static java.lang.Thread.sleep;

public class PlayerManager  extends Observable implements Serializable {



    private ArrayList<Player> players;
    private Player currentPlayer;
    private final Model gameModel;
    private int playerCounter;


    public PlayerManager(Model gameModel){
        this.gameModel = gameModel;
        players = new ArrayList<>();
        playerCounter = 0;
    }


    /**
     * check if the next player is the compute player
     * @return true if next player is human
     */
    public boolean isNextPlayerHuman() {

        Player nextPlayer;
        int nextId = 0;

        while (true) {
            int currentId = currentPlayer.getId();
            //can be achieved by players rather than getNumOfPlayer()
            int numPlayer = players.size();
            //wraps around the bounds of ID
            nextId = (currentId % numPlayer + numPlayer) % numPlayer + 1;
            nextPlayer = players.get(nextId - 1);

            if (!nextPlayer.isGg()) break;
        }

        if (nextPlayer.getStrategy().getName().equalsIgnoreCase("human")) {
            Phase.getInstance().setActionResult(Action.SHOW_NEXT_PHASE_BUTTON);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Set current player to the next one according in round robin fashion
     * If a new round starts from the next player, send ROUND_ROBIN STATE to view
     * Considerations:
     * 1. When allocate armies at start up phaseNumber, when the last player finishes army allocation, this method tells view
     * round robin starts;
     * 2. In round robin, when the last player finishes fortification phaseNumber, this method tells view round robin starts
     * again, meanwhile change current player to the first player.
     */
    public void nextPlayer()  {
        int nextId = 0;

        while (true) {
            int currentId = currentPlayer.getId();
            //can be achieved by players rather than getNumOfPlayer()
            int numPlayer = players.size();
            //wraps around the bounds of ID
            nextId = (currentId % numPlayer + numPlayer) % numPlayer + 1;
            currentPlayer = players.get(nextId - 1);

            if (!currentPlayer.isGg()) break;
        }

        Phase.getInstance().setCurrentPlayer(currentPlayer);
        Phase.getInstance().update();

        isComputerPlayer();
    }

    /**
     * Check if the current player is the computer player
     */
    public void isComputerPlayer()  {
        if (!currentPlayer.getStrategy().getName().equalsIgnoreCase("human")) {
            System.out.println("");
            System.out.println(">>>>>>>>>>>Player "+currentPlayer.getName()+" is Playing<<<<<<<<<<");
            if (Phase.getInstance().getCurrentPhase().equalsIgnoreCase("Start Up Phase")) {
                autoLocatedArmy();
            } else if (Model.isTournamentMode) {
                currentPlayer.execute();
                Model.currentTurn++;
                int check = Model.currentTurn / (players.size());
                System.out.println("Current turn :" + check);
                if (check >= Model.maxTurn) {
                    return;
                }
                if (Phase.getInstance().getActionResult() == Action.WIN) {
                    return;
                }
                nextPlayer();
            } else {
                WorkerThread thread=new WorkerThread(currentPlayer,gameModel);
                thread.start();
            }
        }
    }

    /**
     * allocate one army in a specific country
     * @param country Country reference
     */
    public void allocateArmy(Country country){

        if(Model.disable) {
            Phase.getInstance().setInvalidInfo("Start Up Phase ended!");
            Phase.getInstance().update();
            return;
        }
        if(!currentPlayer.getCountriesOwned().contains(country)){
            Phase.getInstance().setInvalidInfo("Invalid country!");
            Phase.getInstance().update();
            return;
        }

        country.addArmies(1);
        country.getOwner().subArmies(1);

        Phase.getInstance().setActionResult(Action.ALLOCATE_ARMY);
        Phase.getInstance().update();

        //startUpPhase
        if(Model.phaseNumber == 0){
            //all the armies are allocated
            if(country.getOwner().getArmies() == 0){
                isLastPlayer();
            }
        }
        //rPhase
        else {
            if(country.getOwner().getArmies() == 0){
                Model.disable = true;
                Phase.getInstance().setActionResult(Action.SHOW_NEXT_PHASE_BUTTON);
                Phase.getInstance().update();
                Model.phaseNumber = 2;
            }
        }

    }

    /**
     * Automatically allocate armies for the computer player
     */
    public void autoLocatedArmy()  {

        System.out.println(currentPlayer.getName() + " enter autoLocated initiate armies");
        HashMap<String,Integer> allocatedCountry = new HashMap<>();
        while(currentPlayer.getArmies() > 0) {
            Country country = currentPlayer.getCountriesOwned().get((int)(Math.random() * currentPlayer.getCountriesOwned().size()));
            country.addArmies(1);
            if(allocatedCountry.containsKey(country.getName())){
                allocatedCountry.put(country.getName(),allocatedCountry.get(country.getName())+1);
            }else{
                allocatedCountry.put(country.getName(),1);
            }
            currentPlayer.subArmies(1);
        }

        Phase.getInstance().setActionResult(Action.ALLOCATE_ARMY);
        Phase.getInstance().update();

        try{
            sleep(SleepTime.getSleepTime());
        } catch (InterruptedException e) {
            System.out.println(e);
        }

        Tool.printBasicInfo(currentPlayer, "After allocated armies");
        System.out.println(allocatedCountry);

        isLastPlayer();
    }

    /**
     * Check if current player is the last player, and go to the different movement
     */
    public void isLastPlayer() {
        if(!currentPlayer.equals(players.get(players.size() - 1))){
            nextPlayer();
        } else {
            Model.disable = true;
            Phase.getInstance().setActionResult(Action.SHOW_NEXT_PHASE_BUTTON);
            Phase.getInstance().update();
            Model.phaseNumber = 1;

            if (!currentPlayer.getStrategy().getName().equalsIgnoreCase("human")) {
                Phase.getInstance().setCurrentPhase("Reinforcement Phase");
                nextPlayer();
            }
        }
    }

    /**
     * Reinforcement phaseNumber
     * Set new current player
     * Add armies to the player
     * CardModel exchange for armies
     */
    public void reinforcement(){
        PhaseView.getInstance().hide();
        CardModel.getInstance().setCurrentPlayer(currentPlayer);
        if (currentPlayer.getStrategy().getName().equalsIgnoreCase("human")) {
            CardModel.getInstance().update();
        }
    }

    /**
     * attack phaseNumber method
     * @param attacker Country who start attack
     * @param attackerDiceNum how many dice that attacker use
     * @param attacked Country who defend himself
     * @param attackedDiceNum how many dice that defender use
     * @param isAllOut is all-out or not
     */
    public void attack(Country attacker, String attackerDiceNum, Country attacked, String attackedDiceNum, boolean isAllOut){

        currentPlayer.attack(attacker, attackerDiceNum, attacked, attackedDiceNum, isAllOut);
    }

    public void moveAfterConquer(String num) {
        currentPlayer.moveArmy(num);
    }

    /**
     * Method for fortification operation
     * @param source country move armies from
     * @param target country move armies to
     * @param armyNumber number of moved armies
     */
    public void fortification(Country source, Country target, String armyNumber){

        int moveNumber;
        if(source == null || target == null){
            Phase.getInstance().setActionResult(Action.INVALID_MOVE);
            Phase.getInstance().setInvalidInfo("must choose a valid country!");
            Phase.getInstance().update();
            return;
        }

        try{
            moveNumber = Integer.parseInt(armyNumber);
        } catch (Exception ex){
            Phase.getInstance().setActionResult(Action.INVALID_MOVE);
            Phase.getInstance().setInvalidInfo("please enter an integer!!");
            Phase.getInstance().update();
            return;
        }


        if(moveNumber <= 0){
            Phase.getInstance().setActionResult(Action.INVALID_MOVE);
            Phase.getInstance().setInvalidInfo("please enter an positive integer!");
            Phase.getInstance().update();
            return;
        }

        Phase.getInstance().setCurrentPhase("Fortification Phase");
        Phase.getInstance().update();
        currentPlayer.fortification(source,target,Integer.parseInt(armyNumber));
    }

    /**
     * Check if the number of players is valid
     * @param enteredPlayerNum number of players
     */
    public void checkPlayersNum(String enteredPlayerNum) {
        players.clear();
        playerCounter = Integer.parseInt(enteredPlayerNum);

        if(playerCounter > gameModel.getCountries().size() || playerCounter <= 1 || playerCounter > 6){
            gameModel.getNumPlayerMenu().setValidationResult(false, "invalid players number!");
            gameModel.getNumPlayerMenu().update();
            return;
        }
        gameModel.getNumPlayerMenu().setValidationResult(true,"");
        gameModel.getNumPlayerMenu().update();
    }


    /**
     * create Player object for every Player, and add the observer
     * Players are allocated a number of initial armies
     * notify CountryView (country info
     * notify PlayerView  (current player)
     * notify View (state and additional info)
     * @param playerType list of player type, including "aggressive", "benevolent", "human", "random", "cheater"
     */
    public void initiatePlayers(List<String> playerType)  {
        players.clear();
        int initialArmies = getInitialArmies(playerCounter);

        playerCounter = playerType.size();

        for (int i = 0; i < playerCounter; i++){

            String strategy = playerType.get(i);
            Player newPlayer = new Player("Player" + String.valueOf(i), gameModel.getCountries().size(), strategy);
            newPlayer.setArmies(initialArmies);
            newPlayer.setTotalStrength(initialArmies);
            //assign each player a different color
            newPlayer.setColor();
            players.add(newPlayer);
        }

        ArrayList<String> shuffle = new ArrayList<>();

        //assign countries to all the players justly
        for (String key:gameModel.getCountries().keySet()) {
            shuffle.add(key);
        }
        for(int i = 0; i < shuffle.size(); i ++){
            gameModel.getCountries().get(shuffle.get(i)).setPlayer(players.get(i % players.size()));
            players.get(i % players.size()).addCountry(gameModel.getCountries().get(shuffle.get(i)));
        }

        //notify view to unpdate information
        for (String key:gameModel.getCountries().keySet()) {
            gameModel.getCountries().get(key).callObservers();
        }
        //current player notify
        currentPlayer = players.get(0);
        Phase.getInstance().setCurrentPhase("Start Up Phase");
        Phase.getInstance().setCurrentPlayer(currentPlayer);
        Phase.getInstance().update();

        //give state to view
        PlayersWorldDomination.getInstance().setPlayers(players);
        PlayersWorldDomination.getInstance().setTotalNumCountries(gameModel.getCountries().size());
        PlayersWorldDomination.getInstance().addObserver(PlayersWorldDominationView.getInstance());
        PlayersWorldDomination.getInstance().update();

    }

    /**
     * calculate initial armies
     * @param numOfPlayers is the number of Players
     * @return initial armies
     */
    private int getInitialArmies(int numOfPlayers) {

        switch (numOfPlayers){
            case 2:
                return 40;
            case 3:
                return 35;
            case 4:
                return 30;
            case 5:
                return 25;
            case 6:
                return 20;
        }
        return 0;
    }
    /**
     * button event for cardView quit button
     */
    public void quitCards(){

        if(currentPlayer.getTotalCards() >= 5){
            CardModel.getInstance().setInvalidInfo(3);
            CardModel.getInstance().update();
            return;
        }

        CardModel.getInstance().hide();
        Model.disable = false;
        currentPlayer.reinforcement();
    }
    /**
     * Add a random card
     */
    public void addRandomCard() {
        Random random = new Random();
        int num = random.nextInt(3);
        String newCard = Model.cards[num];
        currentPlayer.addRandomCard(newCard);
    }

    /**
     * Verify if the Attack possible
     */
    public void isAttackPossible() {
        if (!currentPlayer.isAttackPossible()){
            Phase.getInstance().setActionResult(Action.ATTACK_IMPOSSIBLE);
            Phase.getInstance().setInvalidInfo("Attack Impossible. You Can Enter Next Phase Now.");
            Phase.getInstance().update();

        }
    }
    /**
     * get current player
     * @return current player
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * set current player
     * @param  p player
     */
    public void setCurrentPlayer(Player p) {
        currentPlayer = p;
    }
    public ArrayList<Player> getPlayers() {return players;}
}
