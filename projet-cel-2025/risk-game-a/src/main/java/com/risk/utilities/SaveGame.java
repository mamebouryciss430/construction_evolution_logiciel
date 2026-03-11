package com.risk.utilities;

import java.awt.Color;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;

import com.risk.model.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.lang.reflect.Field;
import java.util.List;


public class SaveGame {


    public boolean writeTOJSONFile(GamePlayModel gamePlayModel, String fileName) throws IOException, ParseException {

        File file = new File(System.getProperty("user.dir") + "//mapfiles//" + fileName + ".json");

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {

            if (!file.exists()) {
                file.createNewFile();
            }

            JSONObject root = new JSONObject();

            root.put("selectedComboBoxIndex", gamePlayModel.getSelectedComboBoxIndex());
            root.put("selectedAttackComboBoxIndex", gamePlayModel.getSelectedAttackComboBoxIndex());
            root.put("selectedDefendComboBoxIndex", gamePlayModel.getSelectedDefendComboBoxIndex());
            root.put("countryOwned", gamePlayModel.getCountryOwned());
            root.put("armyToMoveFlag", gamePlayModel.getArmyToMoveFlag());
            root.put("cardToBeAssigned", gamePlayModel.getCardToBeAssigned());
            root.put("consoleText", gamePlayModel.getConsoleText().toString());
            root.put("gamePhase", gamePlayModel.getGamePhase());

            root.put("defeatedCountry", buildCountry(gamePlayModel.getDefeatedCountry()));
            root.put("deck", buildCardList(gamePlayModel.getCards()));
            root.put("players", buildPlayerList(gamePlayModel.getPlayers()));
            root.put("gameMapModel", buildGameMap(gamePlayModel));
            root.put("playerTurn", buildPlayer(gamePlayModel.getGameMap().getPlayerTurn()));

            bw.write(root.toJSONString());
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    //
    // ────────────────────────────────────────────────────────────────
    //   MÉTHODES PRIVÉES — DÉCOUPAGE PROPRE
    // ────────────────────────────────────────────────────────────────
    //

    private JSONObject buildGameMap(GamePlayModel model) {
        JSONObject map = new JSONObject();

        map.put("continentList", buildContinentList(model));
        map.put("countryList", buildCountryList(model));
        map.put("playerTurn", buildPlayer(model.getGameMap().getPlayerTurn()));
        map.put("playerIndex", model.getGameMap().getPlayerIndex());
        map.put("leftModelIndex", model.getGameMap().getLeftModelIndex());
        map.put("rightModelIndex", model.getGameMap().getRightModelIndex());
        map.put("listOfPlayers", buildPlayerList(model.getGameMap().getListOfPlayers()));

        return map;
    }

    //
    // ────────────────────────────────────────────────────────────────
    //   CONTINENTS
    // ────────────────────────────────────────────────────────────────
    //

    private JSONArray buildContinentList(GamePlayModel model) {
        JSONArray arr = new JSONArray();

        if (model.getGameMap().getContinents() == null) return arr;

        for (var continent : model.getGameMap().getContinents()) {
            JSONObject c = new JSONObject();
            c.put("continentName", continent.getContinentName());
            c.put("valueControl", continent.getValueControl());
            c.put("listOfCountries", buildCountryList(continent.getCoveredCountries()));
            arr.add(c);
        }
        return arr;
    }

    //
    // ────────────────────────────────────────────────────────────────
    //   COUNTRIES
    // ────────────────────────────────────────────────────────────────
    //

    private JSONArray buildCountryList(GamePlayModel model) {
        return buildCountryList(model.getGameMap().getCountries());
    }

    private JSONArray buildCountryList(List<CountryModel> countries) {
        JSONArray arr = new JSONArray();
        if (countries == null) return arr;

        for (CountryModel c : countries) {
            JSONObject json = buildCountry(c);
            json.put("linkedCountries", buildCountryList(c.getLinkedCountries()));
            arr.add(json);
        }
        return arr;
    }

    private JSONObject buildCountry(CountryModel c) {
        if (c == null) return null;

        JSONObject json = new JSONObject();
        json.put("countryName", c.getCountryName());
        json.put("xPosition", c.getXPosition());
        json.put("yPosition", c.getYPosition());
        json.put("continentName", c.getcontinentName());
        json.put("armies", c.getArmies());
        json.put("backgroundColor", colorToString(c.getBackgroundColor()));
        json.put("borderColor", colorToString(c.getBorderColor()));
        json.put("rulerName", c.getRulerName());
        return json;
    }

    private String colorToString(Color c) {
        return (c == null) ? null : Integer.toString(c.getRGB());
    }

    //
    // ────────────────────────────────────────────────────────────────
    //   PLAYERS
    // ────────────────────────────────────────────────────────────────
    //

    private JSONArray buildPlayerList(List<PlayerModel> players) {
        JSONArray arr = new JSONArray();
        if (players == null) return arr;

        for (PlayerModel p : players) {
            arr.add(buildPlayer(p));
        }
        return arr;
    }

    private JSONObject buildPlayer(PlayerModel p) {
        if (p == null) return null;

        JSONObject json = new JSONObject();
        json.put("namePlayer", p.getName());
        json.put("typePlayer", p.getTypePlayer());
        json.put("myTroop", p.getmyTroop());
        json.put("color", colorToString(p.getColor()));
        json.put("remainTroop", p.getremainTroop());
        json.put("showReinforcementCard", p.getShowReinforcementCard());
        json.put("ownedCountries", buildCountryList(p.getOwnedCountries()));
        json.put("ownedCards", buildCardList(p.getOwnedCards()));
        return json;
    }

    //
    // ────────────────────────────────────────────────────────────────
    //   CARDS
    // ────────────────────────────────────────────────────────────────
    //

    private JSONArray buildCardList(List<CardModel> cards) {
        JSONArray arr = new JSONArray();
        if (cards == null) return arr;

        for (CardModel c : cards) {
            JSONObject json = new JSONObject();
            json.put("cardId", c.getCardId());
            json.put("cardValue", c.getCardValue());
            arr.add(json);
        }
        return arr;
    }


    public GamePlayModel readFROMJSONFile(File file) throws IOException, ParseException {
        GamePlayModel gamePlayModel = new GamePlayModel();
        JSONParser parser = new JSONParser();
        Long lvalue;
        int value;
        Boolean flag;
        String content;
        Color color;

        try {
            // Read JSON file
            Object inputModel = parser.parse(new FileReader(file));
            JSONObject playModel = (JSONObject) inputModel;

            JSONObject getMapModel = (JSONObject) playModel.get("gameMapModel");

            JSONArray playerList = (JSONArray) getMapModel.get("listOfPlayers");
            for (Object o : playerList) {
                PlayerModel playerModel = new PlayerModel();
                JSONObject player = (JSONObject) o;

                if (player.get("namePlayer") != null) {
                    content = (String) player.get("namePlayer");
                    playerModel.setNamePlayer(content);
                } else {
                    playerModel.setNamePlayer("");
                }

                if (player.get("typePlayer") != null) {
                    content = (String) player.get("typePlayer");
                    playerModel.setTypePlayer(content);
                } else {
                    playerModel.setTypePlayer("");
                }

                lvalue = (Long) player.get("myTroop");
                value = lvalue.intValue();
                playerModel.setmyTroop(value);

                content = (String) player.get("color");
                if (content != null ) {
                    color = new Color(Integer.parseInt(content));
                    playerModel.setColor(color);
                }

                lvalue = (Long) player.get("remainTroop");
                value = lvalue.intValue();
                playerModel.setremainTroop(value);

                flag = (Boolean) player.get("showReinforcementCard");
                playerModel.setShowReinforcementCard(flag);

                // list of country
                JSONArray OwnedCountry = (JSONArray) player.get("ownedCountries");
                for (Object j : OwnedCountry) {
                    CountryModel countryModel = new CountryModel();
                    JSONObject country = (JSONObject) j;

                    if (country.get("countryName") != null) {
                        content = (String) country.get("countryName");
                        countryModel.setCountryName(content);
                    } else {
                        countryModel.setCountryName("");
                    }

                    lvalue = (Long) country.get("xPosition");
                    value = lvalue.intValue();
                    countryModel.setXPosition(value);

                    lvalue = (Long) country.get("yPosition");
                    value = lvalue.intValue();
                    countryModel.setYPosition(value);

                    if (country.get("continentName") != null) {
                        content = (String) country.get("continentName");
                        countryModel.setContinentName(content);
                    } else {
                        countryModel.setContinentName("");
                    }

                    lvalue = (Long) country.get("armies");
                    value = lvalue.intValue();
                    countryModel.setArmies(value);

                    content = (String) country.get("backgroundColor");
                    if (content != null ) {
                        color = new Color(Integer.parseInt(content));
                        countryModel.setBackgroundColor(color);
                    }

                    content = (String) country.get("borderColor");
                    if (content != null ) {
                        color = new Color(Integer.parseInt(content));
                        countryModel.setBorderColor(color);
                    }

                    if (country.get("rulerName") != null) {
                        content = (String) country.get("rulerName");
                        countryModel.setRulerName(content);
                    } else {
                        countryModel.setRulerName("");
                    }
                    playerModel.getOwnedCountries().add(countryModel);
                }

                // list of owned cards
                JSONArray OwnedCards = (JSONArray) player.get("ownedCards");
                for (Object j : OwnedCards) {
                    CardModel cardModel = new CardModel();
                    JSONObject card = (JSONObject) j;
                    lvalue = (Long) card.get("cardId");
                    value = lvalue.intValue();
                    cardModel.setCardId(value);

                    lvalue = (Long) card.get("cardValue");
                    value = lvalue.intValue();
                    cardModel.setCardValue(value);
                    playerModel.getOwnedCards().add(cardModel);
                }
                gamePlayModel.getGameMap().getListOfPlayers().add(playerModel);
            }

            JSONObject playerTurn = (JSONObject) getMapModel.get("playerTurn");
            PlayerModel playerModel1 = new PlayerModel();

            if (!playerTurn.get("namePlayer").equals(null)) {
                content = (String) playerTurn.get("namePlayer");
                playerModel1.setNamePlayer(content);
            } else {
                playerModel1.setNamePlayer("");
            }

            if (playerTurn.get("typePlayer") != null) {
                content = (String) playerTurn.get("typePlayer");
                playerModel1.setTypePlayer(content);
            } else {
                playerModel1.setTypePlayer("");
            }

            lvalue = (Long) playerTurn.get("myTroop");
            value = lvalue.intValue();
            playerModel1.setmyTroop(value);

            content = (String) playerTurn.get("color");
            if (content != null ) {
                color = new Color(Integer.parseInt(content));
                playerModel1.setColor(color);
            }

            lvalue = (Long) playerTurn.get("remainTroop");
            value = lvalue.intValue();
            playerModel1.setremainTroop(value);

            flag = (Boolean) playerTurn.get("showReinforcementCard");
            playerModel1.setShowReinforcementCard(flag);

            // list of country
            JSONArray OwnedCountry = (JSONArray) playerTurn.get("ownedCountries");
            for (Object j : OwnedCountry) {
                CountryModel countryModel = new CountryModel();
                JSONObject country = (JSONObject) j;

                if (country.get("countryName") != null) {
                    content = (String) country.get("countryName");
                    countryModel.setCountryName(content);
                } else {
                    countryModel.setCountryName("");
                }

                lvalue = (Long) country.get("xPosition");
                value = lvalue.intValue();
                countryModel.setXPosition(value);

                lvalue = (Long) country.get("yPosition");
                value = lvalue.intValue();
                countryModel.setYPosition(value);

                if (country.get("continentName") != null) {
                    content = (String) country.get("continentName");
                    countryModel.setContinentName(content);
                } else {
                    countryModel.setContinentName("");
                }

                lvalue = (Long) country.get("armies");
                value = lvalue.intValue();
                countryModel.setArmies(value);

                content = (String) country.get("backgroundColor");
                if (content != null ) {
                    color = new Color(Integer.parseInt(content));
                    countryModel.setBackgroundColor(color);
                }

                content = (String) country.get("borderColor");
                if (content != null ) {
                    color = new Color(Integer.parseInt(content));
                    countryModel.setBorderColor(color);
                }

                if (country.get("rulerName") != null) {
                    content = (String) country.get("rulerName");
                    countryModel.setRulerName(content);
                } else {
                    countryModel.setRulerName("");
                }
                playerModel1.getOwnedCountries().add(countryModel);
            }

            // list of owned cards
            JSONArray OwnedCards = (JSONArray) playerTurn.get("ownedCards");
            for (Object j : OwnedCards) {
                CardModel cardModel = new CardModel();
                JSONObject card = (JSONObject) j;
                lvalue = (Long) card.get("cardId");
                value = lvalue.intValue();
                cardModel.setCardId(value);

                lvalue = (Long) card.get("cardValue");
                value = lvalue.intValue();
                cardModel.setCardValue(value);
                playerModel1.getOwnedCards().add(cardModel);
            }
            gamePlayModel.getGameMap().setPlayerTurn(playerModel1);

            lvalue = (Long) getMapModel.get("playerIndex");
            value = lvalue.intValue();
            gamePlayModel.getGameMap().setPlayerIndex(value);

            lvalue = (Long) getMapModel.get("leftModelIndex");
            value = lvalue.intValue();
            gamePlayModel.getGameMap().setLeftModelIndex(value);

            lvalue = (Long) getMapModel.get("rightModelIndex");
            value = lvalue.intValue();
            gamePlayModel.getGameMap().setRightModelIndex(value);

            JSONArray continentJSON = (JSONArray) getMapModel.get("continentList");
            int index1 = 0;
            for (Object o : continentJSON) {
                ContinentsModel continentModel = new ContinentsModel();
                JSONObject continent = (JSONObject) o;

                if (continent.get("continentName") != null) {
                    content = (String) continent.get("continentName");
                    continentModel.setContinentName(content);
                } else {
                    continentModel.setContinentName("");
                }

                lvalue = (Long) continent.get("valueControl");
                value = lvalue.intValue();
                continentModel.setValueControl(value);

                // list of country
                JSONArray linkedCountry = (JSONArray) continent.get("listOfCountries");
                for (Object j : linkedCountry) {
                    CountryModel countryModel1 = new CountryModel();
                    JSONObject country = (JSONObject) j;

                    if (country.get("countryName") != null) {
                        content = (String) country.get("countryName");
                        countryModel1.setCountryName(content);
                    } else {
                        countryModel1.setCountryName("");
                    }

                    lvalue = (Long) country.get("xPosition");
                    value = lvalue.intValue();
                    countryModel1.setXPosition(value);

                    lvalue = (Long) country.get("yPosition");
                    value = lvalue.intValue();
                    countryModel1.setYPosition(value);

                    if (country.get("continentName") != null) {
                        content = (String) country.get("continentName");
                        countryModel1.setContinentName(content);
                    } else {
                        countryModel1.setContinentName("");
                    }

                    lvalue = (Long) country.get("armies");
                    value = lvalue.intValue();
                    countryModel1.setArmies(value);

                    content = (String) country.get("backgroundColor");
                    if (content != null) {
                        color = new Color(Integer.parseInt(content));
                        countryModel1.setBackgroundColor(color);
                    }

                    content = (String) country.get("borderColor");
                    if (content != null ) {
                        color = new Color(Integer.parseInt(content));
                        countryModel1.setBorderColor(color);
                    }

                    if (country.get("rulerName") != null) {
                        content = (String) country.get("rulerName");
                        countryModel1.setRulerName(content);
                    } else {
                        countryModel1.setRulerName("");
                    }
                    continentModel.getCoveredCountries().add(countryModel1);
                }
                gamePlayModel.getGameMap().getContinents().add(continentModel);
            }

            JSONArray countryJSON = (JSONArray) getMapModel.get("countryList");
            for (Object o : countryJSON) {
                CountryModel countryModel = new CountryModel();
                JSONObject country = (JSONObject) o;

                if (country.get("countryName") != null) {
                    content = (String) country.get("countryName");
                    countryModel.setCountryName(content);
                } else {
                    countryModel.setCountryName("");
                }

                lvalue = (Long) country.get("xPosition");
                value = lvalue.intValue();
                countryModel.setXPosition(value);

                lvalue = (Long) country.get("yPosition");
                value = lvalue.intValue();
                countryModel.setYPosition(value);

                if (country.get("continentName") != null) {
                    content = (String) country.get("continentName");
                    countryModel.setContinentName(content);
                } else {
                    countryModel.setContinentName("");
                }

                lvalue = (Long) country.get("armies");
                value = lvalue.intValue();
                countryModel.setArmies(value);

                content = (String) country.get("backgroundColor");
                if (content != null ) {
                    color = new Color(Integer.parseInt(content));
                    countryModel.setBackgroundColor(color);
                }

                content = (String) country.get("borderColor");
                if (content != null ) {
                    color = new Color(Integer.parseInt(content));
                    countryModel.setBorderColor(color);
                }

                if (country.get("rulerName") != null) {
                    content = (String) country.get("rulerName");
                    countryModel.setRulerName(content);
                } else {
                    countryModel.setRulerName("");
                }
                JSONArray links = (JSONArray) country.get("linkedCountries");
                for (Object j : links) {
                    CountryModel countries = new CountryModel();
                    JSONObject countryListed = (JSONObject) j;

                    if (countryListed.get("countryName") != null) {
                        content = (String) countryListed.get("countryName");
                        countries.setCountryName(content);
                    } else {
                        countries.setCountryName("");
                    }

                    lvalue = (Long) countryListed.get("xPosition");
                    value = lvalue.intValue();
                    countries.setXPosition(value);

                    lvalue = (Long) countryListed.get("yPosition");
                    value = lvalue.intValue();
                    countries.setYPosition(value);

                    if (countryListed.get("continentName") != null) {
                        content = (String) countryListed.get("continentName");
                        countries.setContinentName(content);
                    } else {
                        countries.setContinentName("");
                    }

                    lvalue = (Long) countryListed.get("armies");
                    value = lvalue.intValue();
                    countries.setArmies(value);

                    content = (String) countryListed.get("backgroundColor");
                    if (content != null ) {
                        color = new Color(Integer.parseInt(content));
                        countries.setBackgroundColor(color);
                    }

                    content = (String) countryListed.get("borderColor");
                    if (content != null ) {
                        color = new Color(Integer.parseInt(content));
                        countries.setBorderColor(color);
                    }

                    if (countryListed.get("rulerName") != null) {
                        content = (String) countryListed.get("rulerName");
                        countries.setRulerName(content);
                    } else {
                        countries.setRulerName("");
                    }
                    countryModel.getLinkedCountries().add(countries);
                }
                gamePlayModel.getGameMap().getCountries().add(countryModel);
            }

            JSONArray playersJSON = (JSONArray) playModel.get("players");
            for (Object o : playersJSON) {
                PlayerModel playerModel2 = new PlayerModel();
                JSONObject player = (JSONObject) o;

                if (player.get("namePlayer") != null) {
                    content = (String) player.get("namePlayer");
                    playerModel2.setNamePlayer(content);
                } else {
                    playerModel2.setNamePlayer("");
                }

                if (player.get("typePlayer") != null) {
                    content = (String) player.get("typePlayer");
                    playerModel2.setTypePlayer(content);
                } else {
                    playerModel2.setTypePlayer("");
                }

                lvalue = (Long) player.get("myTroop");
                value = lvalue.intValue();
                playerModel2.setmyTroop(value);

                content = (String) player.get("color");
                if (content != null ) {
                    color = new Color(Integer.parseInt(content));
                    playerModel2.setColor(color);
                }

                lvalue = (Long) player.get("remainTroop");
                value = lvalue.intValue();
                playerModel2.setremainTroop(value);

                flag = (Boolean) player.get("showReinforcementCard");
                playerModel2.setShowReinforcementCard(flag);

                // list of country
                JSONArray OwnedCountries = (JSONArray) player.get("ownedCountries");
                for (Object j : OwnedCountries) {
                    CountryModel countryModel2 = new CountryModel();
                    JSONObject country = (JSONObject) j;

                    if (country.get("countryName") != null) {
                        content = (String) country.get("countryName");
                        countryModel2.setCountryName(content);
                    } else {
                        countryModel2.setCountryName("");
                    }

                    lvalue = (Long) country.get("xPosition");
                    value = lvalue.intValue();
                    countryModel2.setXPosition(value);

                    lvalue = (Long) country.get("yPosition");
                    value = lvalue.intValue();
                    countryModel2.setYPosition(value);

                    if (country.get("continentName") != null) {
                        content = (String) country.get("continentName");
                        countryModel2.setContinentName(content);
                    } else {
                        countryModel2.setContinentName("");
                    }

                    lvalue = (Long) country.get("armies");
                    value = lvalue.intValue();
                    countryModel2.setArmies(value);

                    content = (String) country.get("backgroundColor");
                    if (content != null ) {
                        color = new Color(Integer.parseInt(content));
                        countryModel2.setBackgroundColor(color);
                    }

                    content = (String) country.get("borderColor");
                    if (content != null ) {
                        color = new Color(Integer.parseInt(content));
                        countryModel2.setBorderColor(color);
                    }

                    if (country.get("rulerName") != null) {
                        content = (String) country.get("rulerName");
                        countryModel2.setRulerName(content);
                    } else {
                        countryModel2.setRulerName("");
                    }
                    playerModel2.getOwnedCountries().add(countryModel2);
                }

                // list of owned cards
                JSONArray OwnedCard = (JSONArray) player.get("ownedCards");
                for (Object j : OwnedCard) {
                    CardModel cardModel = new CardModel();
                    JSONObject card = (JSONObject) j;
                    lvalue = (Long) card.get("cardId");
                    value = lvalue.intValue();
                    cardModel.setCardId(value);

                    lvalue = (Long) card.get("cardValue");
                    value = lvalue.intValue();
                    cardModel.setCardValue(value);
                    playerModel2.getOwnedCards().add(cardModel);
                }
                ;
                gamePlayModel.getPlayers().add(playerModel2);
            }

            JSONArray deckJSON = (JSONArray) playModel.get("deck");
            for (Object o : deckJSON) {
                CardModel cardModel = new CardModel();
                JSONObject card = (JSONObject) o;
                lvalue = (Long) card.get("cardId");
                value = lvalue.intValue();
                cardModel.setCardId(value);

                lvalue = (Long) card.get("cardValue");
                value = lvalue.intValue();
                cardModel.setCardValue(value);
                gamePlayModel.getCards().add(cardModel);
            }

            lvalue = (Long) playModel.get("selectedComboBoxIndex");
            value = lvalue.intValue();
            gamePlayModel.setSelectedComboBoxIndexRead(value);

            lvalue = (Long) playModel.get("selectedAttackComboBoxIndex");
            value = lvalue.intValue();
            gamePlayModel.setSelectedAttackComboBoxIndexRead(value);

            lvalue = (Long) playModel.get("selectedDefendComboBoxIndex");
            value = lvalue.intValue();
            gamePlayModel.setSelectedDefendComboBoxIndexRead(value);

            flag = (Boolean) playModel.get("countryOwned");
            gamePlayModel.setCountryOwned(flag);

            flag = (Boolean) playModel.get("armyToMoveFlag");
            gamePlayModel.setArmyToMoveTextRead(flag);

            flag = (Boolean) playModel.get("cardToBeAssigned");
            gamePlayModel.setCardToBeAssigned(flag);

            StringBuilder sb = new StringBuilder();
            if (playModel.get("consoleText") != null) {
                content = (String) playModel.get("consoleText");
                sb.append(content);
                gamePlayModel.setConsoleText(sb);
            } else {
                gamePlayModel.setConsoleText(sb);
            }

            if (playModel.get("gamePhase") != null) {
                content = (String) playModel.get("gamePhase");
                gamePlayModel.setGamePhase(content);
            } else {
                gamePlayModel.setGamePhase(null);
            }

            if (playModel.get("defeatedCountry") != null) {
                JSONObject jsonObj = (JSONObject) playModel.get("defeatedCountry");

                if (jsonObj.get("countryName") != null) {
                    content = (String) jsonObj.get("countryName");
                    gamePlayModel.getDefeatedCountry().setCountryName(content);
                } else {
                    gamePlayModel.getDefeatedCountry().setCountryName("");
                }

                lvalue = (Long) jsonObj.get("xPosition");
                value = lvalue.intValue();
                gamePlayModel.getDefeatedCountry().setXPosition(value);

                lvalue = (Long) jsonObj.get("yPosition");
                value = lvalue.intValue();
                gamePlayModel.getDefeatedCountry().setYPosition(value);

                if (jsonObj.get("continentName") != null) {
                    content = (String) jsonObj.get("continentName");
                    gamePlayModel.getDefeatedCountry().setContinentName(content);
                } else {
                    gamePlayModel.getDefeatedCountry().setContinentName("");
                }

                lvalue = (Long) jsonObj.get("armies");
                value = lvalue.intValue();
                gamePlayModel.getDefeatedCountry().setArmies(value);

                content = (String) jsonObj.get("backgroundColor");
                if (content != null ) {
                    color = new Color(Integer.parseInt(content));
                    gamePlayModel.getDefeatedCountry().setBackgroundColor(color);
                }

                content = (String) jsonObj.get("borderColor");
                if (content != null ) {
                    color = new Color(Integer.parseInt(content));
                    gamePlayModel.getDefeatedCountry().setBorderColor(color);
                }

                if (jsonObj.get("rulerName") != null) {
                    content = (String) jsonObj.get("rulerName");
                    gamePlayModel.getDefeatedCountry().setRulerName(content);
                } else {
                    gamePlayModel.getDefeatedCountry().setRulerName("");
                }

            } else {
                gamePlayModel.setDefeatedCountry(null);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return gamePlayModel;

    }

    public static Color stringToColor(final String value) {
        if (value == null) {
            return Color.black;
        }
        try {
            // get color by hex or octal value
            return Color.decode(value);
        } catch (NumberFormatException nfe) {
            // if we can't decode lets try to get it by name
            try {
                // try to get a color by name using reflection
                final Field f = Color.class.getField(value);

                return (Color) f.get(null);
            } catch (Exception ce) {
                // if we can't get any color return black
                return Color.black;
            }
        }
    }
}
