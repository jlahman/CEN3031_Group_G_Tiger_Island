import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Harrison on 3/25/2017.
 */
public class SettlementTest {
    private Settlement settlement0;
    private int settlementID;
    private Hex hex0;

    @Before
    public void setUp() throws Exception {
        hex0 = new Hex();
        settlement0 = new Settlement(hex0, 0);
        assertEquals(settlement0.getSettlementID(), 0);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void addHexToSettlement() throws Exception {
        Hex dummyHex = new Hex(Terrain.JUNGLE);
        settlement0.addHex(dummyHex);
        assertEquals(settlement0.hexesInSettlement.indexOf(dummyHex), 0);
    }

    @Test
    public void settlementSize() throws Exception {
        Hex dummyHex0 = new Hex(Terrain.JUNGLE);
        Hex dummyHex1 = new Hex(Terrain.GRASSLANDS);
        Hex dummyHex2 = new Hex(Terrain.ROCKY);
        settlement0.addHex(dummyHex0);
        settlement0.addHex(dummyHex1);
        settlement0.addHex(dummyHex2);

        assertEquals(settlement0.settlementSize(), 3);
        assertEquals(settlement0.settlementSize(), settlement0.hexesInSettlement.size());
    }

    @Test
    public void getOwner() throws Exception {

    }

}