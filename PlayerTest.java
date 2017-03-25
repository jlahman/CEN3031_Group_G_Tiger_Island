import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {
    Player p;

    @Before
    public void setUp() throws Exception {
        p = new Player();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void setMeepleCount() throws Exception {
        p = new Player();
        p.setMeepleCount(19);
        assertEquals(p.getMeepleCount(),19);
    }

    @Test
    public void setTotoroCount() throws Exception {
        p = new Player();
        p.setTotoroCount(19);
        assertEquals(p.getTotoroCount(),19);
    }

    @Test
    public void setTigerCount() throws Exception {
        p = new Player();
        p.setTigerCount(19);
        assertEquals(p.getTigerCount(),19);
    }
}
