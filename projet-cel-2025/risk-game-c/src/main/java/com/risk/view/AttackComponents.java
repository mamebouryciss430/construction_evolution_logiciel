package com.risk.view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class AttackComponents {


    // attack phase relative components
    public Label attackerDiceLabel;
    public Button attackerDiceOneButton;
    public Button attackerDiceTwoButton;
    public Button attackerDiceThreeButton;
    public Label defenderDiceLabel;
    public Button defenderDiceOneButton;
    public Button defenderDiceTwoButton;
    public Label allOutLabel;
    public Button allOutEnableButton;
    public Button allOutDisableButton;
    public Button attackButton;

    public AttackComponents addAttackerDiceComponents(Label label, Button d1, Button d2, Button d3) {
        this.attackerDiceLabel = label;
        this.attackerDiceOneButton = d1;
        this.attackerDiceTwoButton = d2;
        this.attackerDiceThreeButton = d3;
        return this;
    }

    public AttackComponents addDefenderDiceComponents(Label label, Button d1, Button d2) {
        this.defenderDiceLabel = label;
        this.defenderDiceOneButton = d1;
        this.defenderDiceTwoButton = d2;
        return this;
    }

    public AttackComponents addAllOutComponents(Label label, Button enable, Button disable) {
        this.allOutLabel = label;
        this.allOutEnableButton = enable;
        this.allOutDisableButton = disable;
        return this;
    }

    public AttackComponents addAttackButtonComponents(Button attackButton) {
        this.attackButton = attackButton;
        return this;
    }
}
