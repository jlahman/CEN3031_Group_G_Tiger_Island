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
    public void shuffle() {
        tile1 = myDeck.showTileInDeck(1);
        myDeck.shuffle();
        tile2 = myDeck.showTileInDeck(1);
        Assert.assertNotEquals(tile1,tile2);
    }

    @Test
    public void drawTile() {
        tile1 = myDeck.drawTile();
        assertNotNull(tile1);
    }

    @Test
    public void thereAreTilesLeft() throws Exception {
        assertEquals(48, myDeck.tilesLeft());
        myDeck.drawTile();
        assertEquals(47, myDeck.tilesLeft());
    }
    @Test
    public void thereisOneTileLeft() throws  Exception{
        for(int i = 47; i >0; i--){
            myDeck.drawTile();
        }
        assertEquals(myDeck.tilesLeft(),1);
    }

    @Test
    public void noTilesLeftInDeck(){
        for(int i = 47; i >0; i--){
            myDeck.drawTile();
        }
        assertEquals(myDeck.tilesLeft()-1,0);
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