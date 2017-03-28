import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Harrison on 3/25/2017.
 */
public class SettlementTest {
    private Settlement settlement0;
    private Hex hex0;
    private int settlementID;

    @Before
    public void setUp() throws Exception {
        settlement0 = new Settlement(hex0, 0);
        assertEquals(settlement0.getSettlementID(), 0);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void addHexToSettlement() throws Exception{
        settlement0 = new Settlement(hex0, 0);
        assertEquals(settlement0.getSettlementID(), 0);
        Hex h = new Hex(Terrain.JUNGLE);
        settlement0.addHex(h);
    }

    /*@Test
    public void settlementSize() throws Exception {

    }

    @Test
    public void addHex() throws Exception {

    }

    @Test
    public void getOwner() throws Exception {

    }*/

}