




import org.junit.jupiter.api.Test;
import riskboardgame.Card;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TestCard {

	Card test = new Card("testDetail", "testType");

    @Test
    public void testGetDetail() {
    	assertEquals("testDetail", test.getDetail());
    }
    @Test
    public void testShuffle() {
    	assertEquals("testType", test.getType());
    }

	

}