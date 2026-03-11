package org.atlanmod.risk;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiceTest {

    @Test
    void testRollwithOneDie() {
        Dice dice = new Dice();
        int[] result = dice.roll(1);
        assertEquals(1,result.length,"il ne doit retourner qu'un seul element");
        assertDieValueInRange(result[0]);

    }

    @Test
    void testRollwithTwoDice() {
        Dice dice = new Dice();
        int[] result = dice.roll(2);
        assertEquals(2,result.length,"il doit retourner deux elements");
        assertDieValueInRange(result[0]);
        assertDieAllValueInrange(result);
    }

    @Test
    void testRollwithThreeDice() {
        Dice dice = new Dice();
        int[] result = dice.roll(3);
        assertEquals(3,result.length,"il doit retourner deux elements");

        assertDieAllValueInrange(result);
    }



    public void assertDieValueInRange(int value)
    {
        assertTrue(value>=1 && value<=6,"la valeur doit se trouver entre 1 et 6" +value);

    }
    public void assertDieAllValueInrange( int[] dice)
    {
        for(int value : dice)
        {
            assertDieValueInRange(value);
        }
    }
}