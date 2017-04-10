import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by madashi on 4/8/17.
 */
public class BuildOptionsTest {
    Board b;
    BuildOptions BO;
    Player p1;
    Player p2;

    @Before
    public void setup(){
        b = new Board();
        BO = new BuildOptions();
        p1 = new Player("Player 1");
        p2 = new Player("Player 2");

        b.placeTile(new Tile(TileType.JJ), b.rootHex, 0);
        b.placeTile(new Tile(TileType.GJ), b.getAdjHex(b.rootHex, 2), 1);


        b.settlementList.add(new Settlement(b.getAdjHex(b.rootHex, 0), 0));
        b.getAdjHex(b.rootHex, 0).setMeeple(1);
    }

    @Test
    public void buildSettlementOnLevel1Test() throws Exception {
        BO.buildSettlement(b, b.getAdjHex(b.rootHex, 1), p1);
        assertEquals(1, b.getAdjHex(b.rootHex, 1).getSettlementID());
        assertEquals(p1, b.getAdjHex(b.rootHex, 1).getOwner());
        assertEquals(b.settlementList.get(1).getOwner(), b.getAdjHex(b.rootHex, 1).getOwner());
    }

    @Test
    public void buildSettlementOnLevel2Test() throws Exception {
        b.getAdjHex(b.rootHex, 1).setLevel(2);
        BO.buildSettlement(b, b.getAdjHex(b.rootHex, 1), p1);
        assertEquals(-1, b.getAdjHex(b.rootHex, 1).getSettlementID());
        assertEquals(null, b.getAdjHex(b.rootHex, 1).getOwner());
        assertEquals(b.settlementList.size(), 1);
    }

    @Test
    public void buildSettlementOnLevel0Test() throws Exception {
        BO.buildSettlement(b, b.getAdjHex(b.rootHex, 5), p1);
        assertEquals(-1, b.getAdjHex(b.rootHex, 5).getSettlementID());
        assertEquals(null, b.getAdjHex(b.rootHex, 5).getOwner());
        assertEquals(b.settlementList.size(), 1);
    }

    @Test
    public void buildSettlementOnNullHexTest() throws Exception {
        BO.buildSettlement(b, b.hexArr[0][0], p1);
        assertEquals(-1, b.getAdjHex(b.rootHex, 5).getSettlementID());
        assertEquals(null, b.getAdjHex(b.rootHex, 5).getOwner());
        assertEquals(b.settlementList.size(), 1);
    }

    @Test
    public void expandSettlementOneHexTest() throws Exception {
        b.getAdjHex(b.rootHex, 0).setOwner(p1);
        BO.expandSettlement(b, 0, Terrain.JUNGLE, p1);
        assertEquals(2, b.settlementList.get(0).settlementSize());
    }

    @Test
    public void expandSettlementSeveralHexTest() throws Exception {
        b.getAdjHex(b.rootHex, 0).setOwner(p1);
        Tile t1 = new Tile(TileType.JJ);
        t1.setOrientation(1);
        Tile t2 = new Tile(TileType.JJ);
        t2.setOrientation(5);
        b.placeTile(t1, b.hexArr[b.rootHex.indexX][b.rootHex.indexY-2], 0);
        b.placeTile(t2, b.hexArr[b.rootHex.indexX+3][b.rootHex.indexY-1], 0);
        BO.expandSettlement(b, 0, Terrain.JUNGLE, p1);
        assertEquals(7, b.settlementList.get(0).settlementSize());
    }

    @Test
    public void expandSettlementNoTileToExpandToTest() throws Exception {
        b.getAdjHex(b.rootHex, 0).setOwner(p1);
        Tile t1 = new Tile(TileType.JJ);
        t1.setOrientation(1);
        Tile t2 = new Tile(TileType.JJ);
        t2.setOrientation(5);
        b.placeTile(t1, b.hexArr[b.rootHex.indexX][b.rootHex.indexY-2], 0);
        b.placeTile(t2, b.hexArr[b.rootHex.indexX+3][b.rootHex.indexY-1], 0);
        BO.expandSettlement(b, 0, Terrain.JUNGLE, p1);
        BO.expandSettlement(b, 0, Terrain.JUNGLE, p1);
        assertEquals(7, b.settlementList.get(0).settlementSize());
    }

    @Test
    public void expandSettlementNextToEnemyPlayerTest() throws Exception {
        b.getAdjHex(b.rootHex, 0).setOwner(p1);
        Tile t1 = new Tile(TileType.JJ);
        t1.setOrientation(1);
        Tile t2 = new Tile(TileType.JJ);
        t2.setOrientation(5);
        b.placeTile(t1, b.hexArr[b.rootHex.indexX][b.rootHex.indexY-2], 0);
        b.placeTile(t2, b.hexArr[b.rootHex.indexX+3][b.rootHex.indexY-1], 0);
        b.settlementList.add(new Settlement(t2.getTileHex(1), 1));
        t2.getTileHex(1).setMeeple(1);
        t2.getTileHex(1).setOwner(p2);
        BO.expandSettlement(b, 0, Terrain.JUNGLE, p1);
        assertEquals(4, b.settlementList.get(0).settlementSize());
    }

    @Test
    public void expandSettlementWithoutEnoughMeepleTest() throws Exception {
        b.getAdjHex(b.rootHex, 0).setOwner(p1);
        Tile t1 = new Tile(TileType.JJ);
        t1.setOrientation(1);
        Tile t2 = new Tile(TileType.JJ);
        t2.setOrientation(5);
        b.placeTile(t1, b.hexArr[b.rootHex.indexX][b.rootHex.indexY-2], 0);
        b.placeTile(t2, b.hexArr[b.rootHex.indexX+3][b.rootHex.indexY-1], 0);
        b.settlementList.add(new Settlement(t2.getTileHex(1), 1));
        t2.getTileHex(1).setMeeple(1);
        p1.setMeepleCount(2);
        BO.expandSettlement(b, 0, Terrain.JUNGLE, p1);
        assertEquals(1, b.settlementList.get(0).settlementSize());
    }


    @Test
    public void buildTotoroSanctuaryTest() throws Exception {
        b.getAdjHex(b.rootHex, 0).setOwner(p1);
        Tile t1 = new Tile(TileType.JJ);
        t1.setOrientation(1);
        Tile t2 = new Tile(TileType.JJ);
        t2.setOrientation(5);
        b.placeTile(t1, b.hexArr[b.rootHex.indexX][b.rootHex.indexY-2], 0);
        b.placeTile(t2, b.hexArr[b.rootHex.indexX+3][b.rootHex.indexY-1], 0);
        BO.expandSettlement(b, 0, Terrain.JUNGLE, p1);
        assertEquals(7, b.settlementList.get(0).settlementSize());
        BO.buildTotoroSanctuary(b, b.getAdjHex(b.rootHex, 2),  p1);
        assertTrue(b.getAdjHex(b.rootHex, 2).hasTotoro());
    }

    @Test
    public void buildTotoroSanctuaryWithLessThan5SizeTest() throws Exception {
        b.getAdjHex(b.rootHex, 0).setOwner(p1);
        BO.buildTotoroSanctuary(b, b.getAdjHex(b.rootHex, 1),  p1);
        assertFalse(b.getAdjHex(b.rootHex, 1).hasTotoro());
    }

    @Test
    public void buildTigerSanctuaryNotOnLevel3Test() throws Exception {
        b.getAdjHex(b.rootHex, 0).setOwner(p1);
        BO.buildTigerSanctuary(b, b.getAdjHex(b.rootHex, 1), p1);
        assertFalse(b.getAdjHex(b.rootHex, 1).hasTiger());
    }

    @Test
    public void buildTigerSanctuaryOnLevel3Test() throws Exception {
        b.getAdjHex(b.rootHex, 0).setOwner(p1);
        b.getAdjHex(b.rootHex, 1).setLevel(3);
        BO.buildTigerSanctuary(b, b.getAdjHex(b.rootHex, 1), p1);
        assertTrue(b.getAdjHex(b.rootHex, 1).hasTiger());
    }

    @Test
    public void combineTwoFriendlySettlementsTest() throws Exception {
        b.getAdjHex(b.rootHex, 0).setOwner(p1);

        Tile t1 = new Tile(TileType.JJ);
        t1.setOrientation(1);
        Tile t2 = new Tile(TileType.JJ);
        t2.setOrientation(5);
        b.placeTile(t1, b.hexArr[b.rootHex.indexX][b.rootHex.indexY-2], 0);
        b.placeTile(t2, b.hexArr[b.rootHex.indexX+3][b.rootHex.indexY-1], 0);
        b.settlementList.add(new Settlement(t2.getTileHex(1), 1));
        t2.getTileHex(1).setMeeple(1);
        t2.getTileHex(1).setOwner(p1);
        BO.expandSettlement(b, 0, Terrain.JUNGLE,  p1);
        //assertEquals(5, b.settlementList.get(0).settlementSize());
        assertEquals(1, b.settlementList.size());
    }
    @Test
    public void combineTwoUnfriendlySettlementsTest() throws Exception {
        b.getAdjHex(b.rootHex, 0).setOwner(p1);

        Tile t1 = new Tile(TileType.JJ);
        t1.setOrientation(1);
        Tile t2 = new Tile(TileType.JJ);
        t2.setOrientation(5);
        b.placeTile(t1, b.hexArr[b.rootHex.indexX][b.rootHex.indexY-2], 0);
        b.placeTile(t2, b.hexArr[b.rootHex.indexX+3][b.rootHex.indexY-1], 0);
        b.settlementList.add(new Settlement(t2.getTileHex(1), 1));
        t2.getTileHex(1).setMeeple(1);
        t2.getTileHex(1).setOwner(p2);
        BO.expandSettlement(b, 0, Terrain.JUNGLE,  p1);
        //assertEquals(5, b.settlementList.get(0).settlementSize());
        assertEquals(2, b.settlementList.size());
    }

    @Test
    public void combineThreeFriendlySettlementsTest() throws Exception {
        b.getAdjHex(b.rootHex, 0).setOwner(p1);

        Tile t1 = new Tile(TileType.JJ);
        t1.setOrientation(1);
        Tile t2 = new Tile(TileType.JJ);
        t2.setOrientation(5);
        b.placeTile(t1, b.hexArr[b.rootHex.indexX][b.rootHex.indexY-2], 0);
        b.placeTile(t2, b.hexArr[b.rootHex.indexX+3][b.rootHex.indexY-1], 0);
        b.settlementList.add(new Settlement(t2.getTileHex(1), 1));
        t2.getTileHex(1).setMeeple(1);
        t2.getTileHex(1).setOwner(p1);
        b.settlementList.add(new Settlement(t1.getTileHex(1), 2));
        t1.getTileHex(1).setMeeple(1);
        t1.getTileHex(1).setOwner(p1);
        BO.expandSettlement(b, 0, Terrain.JUNGLE,  p1);
        //assertEquals(5, b.settlementList.get(0).settlementSize());
        assertEquals(1, b.settlementList.size());
    }
}