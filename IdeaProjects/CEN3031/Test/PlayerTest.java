import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {
    private Player p;
    private String name;

    @Before
    public void setUp() throws Exception {
        p = new Player(name);
    }

    /*
    @After
    public void tearDown() throws Exception {

    }
    */

    @Test
    public void setSetMeepleCountTest() throws Exception {
        p.setMeepleCount(19);
        assertEquals(p.getMeepleCount(),19);
    }

    @Test
    public void setTotoroCountTest() throws Exception {
        p.setTotoroCount(19);
        assertEquals(p.getTotoroCount(),19);
    }

    @Test
    public void setTigerCountTest() throws Exception {
        p.setTigerCount(19);
        assertEquals(p.getTigerCount(),19);
    }

    @Test
    public void setWrongMeepleCountTest() throws Exception {
        p.setMeepleCount(-1);
        assertFalse(p.getMeepleCount()==-1);
    }

    @Test
    public void setWrongTotoroCountTest() throws Exception {
        p.setTotoroCount(-1);
        assertFalse(p.getTotoroCount()==-1);
    }

    @Test
    public void setWrongTigerCountTest() throws Exception {
        p.setTigerCount(-1);
        assertFalse(p.getTigerCount()==-1);
    }

    @Test
    public void increaseScoreTest() throws Exception {
        p.increaseScore(50);
        p.increaseScore(2);
        assertEquals(52,p.getScore());
    }
}
