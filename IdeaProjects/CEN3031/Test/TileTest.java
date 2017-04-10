import jdk.jfr.events.ExceptionThrownEvent;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Andrew Dang on 3/28/2017.
 */
public class TileTest {
    Tile t;
    @Before
    public void setUp() throws Exception {
        t = new Tile(TileType.JJ);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void setLevelTest() throws Exception {
       t = new Tile(TileType.JJ);
       t.setLevel(2);
       assertEquals(2,t.getTilelevel());
    }

    @Test
    public void setOrientationTest() throws Exception {
        t = new Tile(TileType.GG);
        t.setOrientation(4);
        assertEquals(4,t.getOrientation());
    }

    @Test
    public void setWrongLevelTest() throws Exception {
        t = new Tile(TileType.LL);
        t.setLevel(-1);
        assertFalse(t.getTilelevel() == -1);
    }

    @Test
    public void setWrongOrientationTest() throws Exception {
        t = new Tile(TileType.GL);
        t.setOrientation(6);
        assertFalse(t.getOrientation() == 6);
    }

    @Test
    public void getTerrain1Test() throws Exception {
        t = new Tile(TileType.GJ);
        assertEquals(t.getTerrainOne(TileType.GJ), Terrain.GRASSLANDS);
    }

    @Test
    public void getTerrain2Test() throws Exception {
        t = new Tile (TileType.GJ);
        assertEquals(t.getTerrainTwo(TileType.GJ), Terrain.JUNGLE);
    }

    @Test
    public void getTerrain1TestBeta() throws Exception {
        t = new Tile(TileType.RL);
        assertEquals(t.getTerrainOne(TileType.RL), Terrain.ROCKY);
    }

    @Test
    public void getTerrain2TestBeta() throws Exception {
        t = new Tile(TileType.RL);
        assertEquals(t.getTerrainTwo(TileType.RL), Terrain.LAKE);
    }

    @Test
    public void getTerrain1TestGamma() throws Exception {
        t = new Tile(TileType.RR);
        assertEquals(t.getTerrainOne(TileType.RR), Terrain.ROCKY);
    }

    @Test
    public void getTerrain2TestGamma() throws Exception {
        t = new Tile(TileType.RR);
        assertEquals(t.getTerrainTwo(TileType.RR), Terrain.ROCKY);
    }
}