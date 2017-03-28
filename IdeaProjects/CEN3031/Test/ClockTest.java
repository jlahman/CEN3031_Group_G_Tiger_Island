import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Andrew Dang on 3/28/2017.
 */
public class ClockTest {
    Clock c;
    @Before
    public void setUp() throws Exception {
        c = new Clock();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void incrementTest() throws Exception {
        c = new Clock();
        c.increment();
        assertFalse(c.getTurnCounter());
    }

}