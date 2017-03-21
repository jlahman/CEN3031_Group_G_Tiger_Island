import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by kevin on 3/20/2017.
 */
public class DeckTest {
    Tile tile1;
    Tile tile2;
    Deck myDeck = new Deck();
    @Test
    public void shuffle() throws Exception {
        tile1 = myDeck.showTileInDeck(1);
        myDeck.shuffle();
        tile2 = myDeck.showTileInDeck(1);
        Assert.assertNotEquals(tile1,tile2);
    }

    @Test
    public void drawTile() throws Exception {
        tile1 = myDeck.drawTile();
        assertNotNull(tile1);
    }

    @Test
    public void tilesLeft() throws Exception {
        assertEquals(48, myDeck.tilesLeft());
        myDeck.drawTile();
        assertEquals(47, myDeck.tilesLeft());
    }

    @Test
    public void printDeck() throws Exception {
        assertNotNull(myDeck);
    }

    @Test
    public void showTileInDeck() throws Exception {
        assertNotNull(myDeck.showTileInDeck(0));
    }

}