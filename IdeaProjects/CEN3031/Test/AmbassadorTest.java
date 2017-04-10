import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by madashi on 4/10/17.
 */
public class AmbassadorTest {
    Ambassador a = new Ambassador();
    @Test
    public void listen() throws Exception {
        a.setAI1(new AI());
        a.setAI2(new AI());
        a.actOnMessage("WAIT FOR THE TOURNAMENT TO BEGIN 6");
        a.actOnMessage("NEW MATCH BEGINNING NOW YOUR OPPONENT IS PLAYER 1");
        a.actOnMessage("MAKE YOUR MOVE IN GAME B WITHIN 1.5 SECONDS: MOVE 0 PLACE GRASS+LAKE");
        a.actOnMessage("GAME B MOVE 0 PLAYER 1 PLACED LAKE+LAKE AT 0 2 -2 5 FOUNDED SETTLEMENT AT 0 -1 1");
        a.actOnMessage("GAME B MOVE 3 PLAYER 1 PLACED LAKE+LAKE AT 2 -4 2 1 EXPANDED SETTLEMENT AT 0 -1 1 LAKE");
    }

}