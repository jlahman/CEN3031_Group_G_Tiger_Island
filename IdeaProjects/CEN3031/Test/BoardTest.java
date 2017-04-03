import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by madashi on 3/23/17.
 */
public class BoardTest {
    Board b;
    @Before
    public void setUp() throws Exception {
        b = new Board();
    }

    @Test
    public void placeTileOnEmptyBoardTest() throws Exception {
        b = new Board();
        Tile t1 = new Tile(TileType.JJ);
        b.placeTile(t1, b.rootHex, 0);
        assertEquals(t1.getTileHex(0), b.rootHex);
        assertEquals(t1.getTileHex(1), b.getAdjHex(b.rootHex, 0));
        assertEquals(t1.getTileHex(2), b.getAdjHex(b.rootHex, 1));
    }

    @Test
    public void placeTileOnBoardOnLevel0Test() throws Exception {
        b = new Board();
        Tile t1 = new Tile(TileType.JJ);
        Tile t2 = new Tile(TileType.GG);
        b.placeTile(t1, b.rootHex, 0);
        b.placeTile(t2, b.getAdjHex(t1.getTileHex(2), 3), 1);
        assertEquals(t2.getTileHex(1), b.getAdjHex(b.rootHex, 2));
        assertEquals(t2.getTileHex(0), b.getAdjHex(b.getAdjHex(b.rootHex, 2), 3));
        assertEquals(t2.getTileHex(2), b.getAdjHex(b.getAdjHex(b.rootHex, 2), 2));

    }

    @Test
    public void placeTileOnBoardOnLevel0RotatedTest() throws Exception {
        b = new Board();
        Tile t1 = new Tile(TileType.JJ);
        Tile t2 = new Tile(TileType.GG);
        t2.setOrientation(1);
        b.placeTile(t1, b.rootHex, 0);
       // b.placeTile(t2, b.rootHex.indexX + 1, b.rootHex.indexY, 1);
        b.placeTile(t2,  b.getAdjHex(t1.getTileHex(2), 2), 1);
        assertEquals(t2.getTileHex(0), b.getAdjHex(b.rootHex, 2));
        assertEquals(t2.getTileHex(1), b.getAdjHex(b.getAdjHex(b.rootHex, 1), 2));
        assertEquals(t2.getTileHex(2), b.getAdjHex(b.getAdjHex(b.rootHex, 2), 2));

    }

    @Test
    public void placeTileOnEmptyBoardWithCorrectLevelTest() throws Exception{
        b = new Board();
        Tile t1 = new Tile(TileType.JJ);
        b.placeTile(t1, b.rootHex, 0);
        assertEquals(1, b.rootHex.getLevel());
        assertEquals(1, t1.getTileHex(1).getLevel());
        assertEquals(1, t1.getTileHex(2).getLevel());
    }

    @Test
    public void placeTileOnLevel1BoardWithCorrectLevelTest() throws Exception{
        b = new Board();
        Tile t1 = new Tile(TileType.JJ);
        Tile t2 = new Tile(TileType.JJ);
        Tile t3 = new Tile(TileType.JJ);
        b.placeTile(t1, b.rootHex, 0);
        b.placeTile(t2, b.getAdjHex(b.rootHex, 2), 1);
        t3.setOrientation(1);
        b.placeTile(t3, b.rootHex,0);
        assertEquals(2, b.rootHex.getLevel());
        assertEquals(1, b.getAdjHex(b.rootHex, 0).getLevel());
        assertEquals(2, b.getAdjHex(b.rootHex, 1).getLevel());
    }

    @Test
    public void placementInValidIfLevelsDifferentTest() throws Exception{
        b = new Board();
        Tile t1 = new Tile(TileType.JJ);
        Tile t2 = new Tile(TileType.JJ);
        Tile t3 = new Tile(TileType.JJ);
        Tile t4 = new Tile(TileType.JJ);
        b.placeTile(t1, b.rootHex, 0);
        b.placeTile(t2, b.getAdjHex(b.rootHex, 2), 1);
        t3.setOrientation(1);
        b.placeTile(t3, b.rootHex,0);
        b.placeTile(t4, b.rootHex,0);
        assertFalse(b.isPlacementValid(t4, b.rootHex, 0));
        assertEquals(2, b.rootHex.getLevel());
        assertEquals(1, b.getAdjHex(b.rootHex, 0).getLevel());
        assertEquals(2, b.getAdjHex(b.rootHex, 1).getLevel());
    }

    @Test
    public void placementValidIfLevelsSameTest() throws Exception{
        b = new Board();
        Tile t1 = new Tile(TileType.JJ);
        Tile t2 = new Tile(TileType.JJ);
        Tile t3 = new Tile(TileType.JJ);
        b.placeTile(t1, b.rootHex, 0);
        b.placeTile(t2, b.getAdjHex(b.rootHex, 2), 1);
        t3.setOrientation(1);
        assertTrue(b.isPlacementValid(t3, b.rootHex, 0));
    }

    @Test
    public void placementInvalidIfNotOnVolcanoTst() throws Exception{
        b = new Board();
        Tile t1 = new Tile(TileType.JJ);
        Tile t2 = new Tile(TileType.JJ);
        Tile t3 = new Tile(TileType.JJ);
        b.placeTile(t1, b.rootHex, 0);
        b.placeTile(t2, b.getAdjHex(b.rootHex, 2), 1);
        t3.setOrientation(2);
        assertFalse(b.isPlacementValid(t3, b.rootHex, 2));
    }

    @Test
    public void placementInvalidIfNukingTotoroTest() throws Exception{
        b = new Board();
        Tile t1 = new Tile(TileType.JJ);
        Tile t2 = new Tile(TileType.JJ);
        Tile t3 = new Tile(TileType.JJ);
        b.placeTile(t1, b.rootHex, 0);
        b.placeTile(t2, b.getAdjHex(b.rootHex, 2), 1);
        t3.setOrientation(1);
        assertTrue(b.isPlacementValid(t3, b.rootHex, 0));
        b.getAdjHex(b.rootHex,1).setTotoro(1);
        assertFalse(b.isPlacementValid(t3, b.rootHex, 0));
    }

    @Test
    public void placementInvalidIfNukingSizeOneSettlementTest() throws Exception{
        b = new Board();
        Tile t1 = new Tile(TileType.JJ);
        Tile t2 = new Tile(TileType.JJ);
        Tile t3 = new Tile(TileType.JJ);
        b.placeTile(t1, b.rootHex, 0);
        b.placeTile(t2, b.getAdjHex(b.rootHex, 2), 1);
        t3.setOrientation(1);
        assertTrue(b.isPlacementValid(t3, b.rootHex, 0));
        b.settlementList.add(new Settlement(t2.getTileHex(1),0));
        t2.getTileHex(1).setSettlementID(0);
        assertFalse(b.isPlacementValid(t3, b.rootHex, 0));

    }
    @Test
    public void placementValidIfNukingSizeNotOneSettlementTest() throws Exception{
        b = new Board();
        Tile t1 = new Tile(TileType.JJ);
        Tile t2 = new Tile(TileType.JJ);
        Tile t3 = new Tile(TileType.JJ);
        b.placeTile(t1, b.rootHex, 0);
        b.placeTile(t2, b.getAdjHex(b.rootHex, 2), 1);
        t3.setOrientation(1);
        assertTrue(b.isPlacementValid(t3, b.rootHex, 0));
        b.settlementList.add(new Settlement(t2.getTileHex(1),0));
        b.settlementList.get(0).addHex(t2.getTileHex(2));
        assertTrue(b.isPlacementValid(t3, b.rootHex, 0));

    }

    @Test
    public void placementInvalidIfNukingSizeTwoOneSettlementTest() throws Exception{
        b = new Board();
        Tile t1 = new Tile(TileType.JJ);
        Tile t2 = new Tile(TileType.JJ);
        Tile t3 = new Tile(TileType.JJ);
        b.placeTile(t1, b.rootHex, 0);
        b.placeTile(t2, b.getAdjHex(b.rootHex, 2), 1);
        t3.setOrientation(1);
        assertTrue(b.isPlacementValid(t3, b.rootHex, 0));
        b.settlementList.add(new Settlement(t2.getTileHex(1),0));
        b.settlementList.get(0).addHex(t1.getTileHex(2));
        assertFalse(b.isPlacementValid(t3, b.rootHex, 0));

    }

    @Test
    public void BoardCreationTest() throws Exception {
        assertTrue(b instanceof Board);
    }
/*
@After
    public void tearDown() throws Exception {
    }
 */

}


