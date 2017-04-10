import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by madashi on 3/23/17.
 */
public class HexTest {
    Hex h;
    @Before
    public void setUp() throws Exception {
     h = new Hex();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void setLevelTest() throws Exception {
        h = new Hex();
        h.setLevel(2);
        assertEquals(2,h.getLevel());
    }

    @Test
    public void hasTotoroTest() throws Exception {
        h = new Hex();
        h.setTotoro(1);
        if(h.getTotoro() > 0)
            assertTrue(h.hasTotoro());
        else
            assertFalse(h.hasTotoro());
    }

    @Test
    public void setMeepleTest() throws Exception {
        h = new Hex();
        h.setMeeple(7);
        assertEquals(7, h.getMeeple());
    }

    @Test
    public void addMeepleTest() throws Exception {
        h = new Hex();
        h.setLevel(4);
        h.addMeeple(new Player(""));
        assertEquals(4, h.getMeeple());
    }

    @Test
    public void addTotoroTest() throws Exception {
        h = new Hex();
        h.addTotoro(new Player(""));
        assertEquals(1, h.getTotoro());
    }

    @Test
    public void addTigerTest() throws Exception {
        h = new Hex();
        h.addTiger(new Player(""));
        assertEquals(1, h.getTiger());
    }

    @Test
    public void setWrongTotoroTest() throws Exception {
        h = new Hex();
        h.setTotoro(4);
        assertFalse(h.getTotoro()==4);
    }

    @Test
    public void setTotoroTest() throws Exception {
        h = new Hex();
        h.setTotoro(1);
        assertTrue(h.getTotoro()==1);
    }

    @Test
    public void setWrongTigerTest() throws Exception {
        h = new Hex();
        h.setTiger(4);
        assertFalse(h.getTiger()==4);
    }

    @Test
    public void setTigerTest() throws Exception {
        h = new Hex();
        h.setTiger(1);
        assertTrue(h.getTiger()==1);
    }

    @Test
    public void hasTigerTest() throws Exception {
        h = new Hex();
        h.setTiger(1);
        assertTrue(h.hasTiger());
    }

    @Test
    public void setSettlementIDTest() throws Exception {
        h = new Hex();
        h.setSettlementID(23);
        assertEquals(23, h.getSettlementID());
    }
}