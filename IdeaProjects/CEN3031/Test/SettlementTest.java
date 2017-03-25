import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Harrison on 3/25/2017.
 */
public class SettlementTest {
    private Settlement s;

    /*@Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }*/

    @Test
    public void addHexToSettlement() throws Exception{
        s = new Settlement();
        Hex h = new Hex(Terrain.JUNGLE);
        s.addHex(h);
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