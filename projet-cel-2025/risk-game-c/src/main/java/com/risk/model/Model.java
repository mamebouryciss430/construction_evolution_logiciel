package com.risk.model;

import com.risk.common.*;
import com.risk.exception.InvalidMapException;
import com.risk.view.*;

import java.io.*;
import java.util.*;

/**
 * Define Observable class
 * ...
 */

public class Model extends Observable implements Serializable {

    public int cardsValueNonStatic;
    public int phaseNumberNonStatic;
    public boolean disableNonStatic;
    public final static String[] cards = {"infantry","cavalry","artillery"};


    //card
    public static int cardsValue = 5;


    //winner
    public static String winner = "draw";

    // data
    private PlayerManager playerManager;
    public MapModel mapModel;

    //decided whether country view should respond to the event
    public static boolean disable = false;

    //indicate current phaseNumber; startUp0; rPhase1; aPhase2; fPhase3
    public static int phaseNumber = 0;

    private MenuManager menuManager;
    public static double maxTurn = Double.POSITIVE_INFINITY;
    public static int currentTurn;
    public static boolean isTournamentMode = false;

    /**
     * ctor for Model
     */
    public Model(){
        playerManager = new PlayerManager(this);
        mapModel = new MapModel();
        menuManager = new MenuManager(this);
    }

    /**
     * ctor for Model
     * @param model model
     */
    public Model(Model model){
        setContinents(model.getContinents());
    }

    /**
     * Get fileInfoMenu
     * @return fileInfoMenu the fileInfo menu
     */
    public FileInfoMenu getFileInfoMenu() {
        return menuManager.getFileInfoMenu();
    }

    /**
     * Get numPlayerMenu
     * @return numPlayerMenu the numPlayer menu
     */
    public NumPlayerMenu getNumPlayerMenu() {
        return menuManager.getNumPlayerMenu();
    }

    /**
     * set fileInfoMenu, for the test
     * @param fileInfoMenu the observable
     */
    public void setFileInfoMenu(FileInfoMenu fileInfoMenu) {
        menuManager.setFileInfoMenu(fileInfoMenu);
    }

    /**
     * set numPlayerMenu, for the test
     * @param numPlayerMenu the observable
     */
    public void setNumPlayerMenu(NumPlayerMenu numPlayerMenu) {
        menuManager.setNumPlayerMenu(numPlayerMenu);
    }

    /**
     * reset model object before reload map file
     */
    public void reset(){
        playerManager = new PlayerManager(this);
        mapModel = new MapModel();
        menuManager.setValidFile(true);
    }

    /**
     * for reset values when tournament mode
     */
    public void resetValue(){
        cardsValue = 5;
        disable = false;
        phaseNumber = 0;
        maxTurn = Double.POSITIVE_INFINITY;
        menuManager.setValidFile(true);
        currentTurn = 0 ;
        winner = "draw";
    }

    /**
     * Method for start up phase operation
     * @param countryViewHashMap the countries observer
     */

    public void startUp(HashMap<Integer,CountryView> countryViewHashMap){

        int id = 1;
        HashMap<String, Country> countries = getCountries();
        for (String key: countries.keySet()) {
            countries.get(key).addObserver(countryViewHashMap.get(id));
            countries.get(key).callObservers();
            id ++;
        }
        //send next state message
        Message message = new Message(STATE.PLAYER_NUMBER,null);
        notify(message);

        Phase.getInstance().addObserver(PhaseView.getInstance());
        Phase.getInstance().setCurrentPlayer(getCurrentPlayer());
        Phase.getInstance().setCurrentPhase("Start Up Phase");
        Phase.getInstance().update();

        CardModel.getInstance().addObserver(CardView.getInstance());

    }

    /**
     *  notify the view that model state has changed
     * @param message The message to send to the view, may include some important information
     */
    private void notify(Message message) {
        setChanged();
        notifyObservers(message);
    }

    /**
     * set continents list of model
     * @param continents  The continents to set
     */
    public void setContinents(List<Continent> continents) { mapModel.setContinents((ArrayList<Continent>) continents); }
    public HashMap<String, Country> getCountries() { return mapModel.getCountries();}
    public ArrayList<Continent> getContinents() { return mapModel.getContinents();}
    public void initiateContinents(String continentsList){mapModel.initiateContinents(continentsList);}
    void initiateCountries(String countriesList){mapModel.initiateCountries(countriesList);}

    public Player getCurrentPlayer() {return playerManager.getCurrentPlayer();}
    public void setCurrentPlayer(Player p) {playerManager.setCurrentPlayer(p);}
    public void nextPlayer(){ playerManager.nextPlayer();}
    public ArrayList<Player> getPlayers() {return playerManager.getPlayers();}
    public boolean isNextPlayerHuman() {return playerManager.isNextPlayerHuman();}
    public void isComputerPlayer()  {playerManager.isComputerPlayer();}
    public void allocateArmy(Country country){ playerManager.allocateArmy(country);}
    public void initiatePlayers(List<String> playerType)  {playerManager.initiatePlayers(playerType);}
    public void checkPlayersNum(String enteredPlayerNum) {playerManager.checkPlayersNum(enteredPlayerNum);}
    public void quitCards(){ playerManager.quitCards();}
    public void addRandomCard() {playerManager.addRandomCard();}
    public void isAttackPossible() {playerManager.isAttackPossible();}
    public void reinforcement(){playerManager.reinforcement();}
    public void attack(Country attacker, String attackerDiceNum, Country attacked, String attackedDiceNum, boolean isAllOut){
        playerManager.attack(attacker, attackerDiceNum, attacked, attackedDiceNum, isAllOut);
    }
    public void fortification(Country source, Country target, String armyNumber){
        playerManager.fortification(source, target, armyNumber);
    }
    public void moveAfterConquer(String num) {playerManager.moveAfterConquer(num);}

    public void readFile(String filePath) throws IOException {menuManager.readFile(filePath);}
    public boolean isValidFile() {return menuManager.isValidFile();}
    public void editorReadFile(String filePath) throws IOException, InvalidMapException {menuManager.editorReadFile(filePath);}
    public void setMenuViews(FileInfoMenuView fileInfoMenuView, NumPlayerMenuView numPlayerMenuView) {menuManager.setMenuViews(fileInfoMenuView, numPlayerMenuView);}


    /**
     * copy static data member value from nonstatic data member
     * used after deserialization
     */
    public void staticToNonStatic(){
        cardsValueNonStatic = Model.cardsValue;
        phaseNumberNonStatic = Model.phaseNumber;
        disableNonStatic = Model.disable;
    }
    /**
     * copy non static data member value from static data member
     * used before serialization
     */
    public void nonStaticToStatic(){
        Model.phaseNumber = phaseNumberNonStatic;
        Model.disable = disableNonStatic;
        Model.cardsValue = cardsValueNonStatic;
    }

    /**
     * save the whole game to be loaded later
     * @param fileName the name of file save to
     * @return true if the game is saved successfully; otherwise return false
     */
    public boolean save(String fileName){
        try {

            staticToNonStatic();

            File f = new File(fileName);
            FileWriter fileWriter = new FileWriter(f);
            fileWriter.write("nothing here");
            fileWriter.close();

            fileName = fileName.substring(0, fileName.length() - 3);
            fileName += "_";

            FileOutputStream fileStream = new FileOutputStream(fileName + "model.ser");
            ObjectOutputStream os = new ObjectOutputStream(fileStream);
            os.writeObject(this);

            fileStream = new FileOutputStream(fileName + "phase.ser");
            os = new ObjectOutputStream(fileStream);
            os.writeObject(Phase.getInstance());

        } catch (FileNotFoundException ex){
            return false;
        } catch (IOException ex){
            return false;
        }
        return true;
    }

    public Model load(String fileName){
        Model loadModel = new Model();
        try {
            FileInputStream fileStream = new FileInputStream(fileName);
            ObjectInputStream os = new ObjectInputStream(fileStream);
            loadModel = (Model) os.readObject();

        } catch (IOException ex){
            ex.printStackTrace();
        } catch (ClassNotFoundException ex){
            ex.getStackTrace();
        }
        return loadModel;
    }
}
