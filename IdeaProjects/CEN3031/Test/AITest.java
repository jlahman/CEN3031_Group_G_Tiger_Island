import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by mquac on 4/9/2017.
 */
public class AITest {

    AI a;

    @Before
    public void setUp() throws Exception {
        a = new AI();
    }

    @Test
    public void setGameIDTest() throws Exception {
        a.setGameID("gameName");
        assertEquals("gameName",a.getGameID());
    }

    @Test
    public void playMove() throws Exception {
        
    }


    /*    @After
    public void tearDown() throws Exception {

    }*/

}