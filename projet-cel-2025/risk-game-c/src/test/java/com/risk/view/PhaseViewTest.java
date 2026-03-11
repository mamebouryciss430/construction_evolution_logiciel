package com.risk.view;

import com.risk.controller.MapController;
import com.risk.model.Phase;
import com.risk.model.Player;
import com.risk.strategy.HumanStrategy;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.jupiter.api.Assertions.*;

public class PhaseViewTest extends ApplicationTest {

    private PhaseView phaseView;

    private Label phaseLabel;
    private Button nextPhaseButton;
    private Label currentPlayerNameLabel;
    private Label currentPlayerTypeLabel;
    private Label armiesInHandLabel;
    private Label countryALabel;
    private Label countryANameLabel;
    private Label countryBLabel;
    private Label countryBNameLabel;
    private Label numArmiesMovedLabel;
    private TextField numArmiesMovedTextField;
    private Label invalidMoveLabel;
    private Button skipFortification;
    private Button saveGameButton;
    private Button loadGameButton;

    Label attackerDiceLabel;
    Button attackerDiceOneButton;
    Button attackerDiceTwoButton;
    Button attackerDiceThreeButton;
    Label defenderDiceLabel;
    Button defenderDiceOneButton;
    Button defenderDiceTwoButton;
    Label allOutLabel;
    Button allOutEnableButton;
    Button allOutDisableButton;
    Button attackButton;

    private MapController mapController;

    @Override
    public void start(Stage stage) {
        phaseLabel = new Label();
        nextPhaseButton = new Button();
        currentPlayerNameLabel = new Label();
        currentPlayerTypeLabel = new Label();
        armiesInHandLabel = new Label();
        countryALabel = new Label();
        countryANameLabel = new Label();
        countryBLabel = new Label();
        countryBNameLabel = new Label();
        numArmiesMovedLabel = new Label();
        numArmiesMovedTextField = new TextField();
        invalidMoveLabel = new Label();
        skipFortification = new Button();
        saveGameButton = new Button();
        loadGameButton = new Button();

        attackerDiceLabel = new Label();
        attackerDiceOneButton = new Button();
        attackerDiceTwoButton = new Button();
        attackerDiceThreeButton = new Button();
        defenderDiceLabel = new Label();
        defenderDiceOneButton = new Button();
        defenderDiceTwoButton = new Button();
        allOutLabel = new Label();
        allOutEnableButton = new Button();
        allOutDisableButton = new Button();
        attackButton = new Button();

        mapController = new MapController();

        stage.setScene(new Scene(new StackPane(), 800, 600));
        stage.show();
    }

    @BeforeEach
    void setupUI() {
        Platform.runLater(() -> {
            phaseView = PhaseView.getInstance();
            GeneralComponents gc = new GeneralComponents(mapController);
            gc.addPhaseComponents(phaseLabel, nextPhaseButton)
               .addPlayerComponents(currentPlayerNameLabel, currentPlayerTypeLabel, armiesInHandLabel)
               .addCountryComponents(countryALabel, countryANameLabel, countryBLabel, countryBNameLabel)
               .addFortificationComponents(numArmiesMovedLabel, numArmiesMovedTextField, skipFortification)
               .addSaveLoadButtons(saveGameButton, loadGameButton, invalidMoveLabel);

            phaseView.init(gc);
            AttackComponents ac = new AttackComponents();
            ac.addAttackerDiceComponents(attackerDiceLabel, attackerDiceOneButton, attackerDiceTwoButton, attackerDiceThreeButton)
               .addDefenderDiceComponents(defenderDiceLabel, defenderDiceOneButton, defenderDiceTwoButton)
               .addAllOutComponents(allOutLabel, allOutEnableButton, allOutDisableButton)
               .addAttackButtonComponents(attackButton);
            phaseView.initAttackComponents(ac);
        });

        // Fin des changements JavaFX
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    void testPhaseUpdateChangesPhaseLabel() {
        Phase phase = Phase.getInstance();

        Player p = new Player("MBC", 1, "human");
        p.setStrategy(new HumanStrategy(p));

        phase.setCurrentPlayer(p);
        phase.setCurrentPhase("Attack Phase");

        Platform.runLater(() -> phaseView.update(phase, null));

        // Exécution du runLater()
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals("Attack Phase", phaseLabel.getText());
    }
}
