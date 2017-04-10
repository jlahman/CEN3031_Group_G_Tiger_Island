import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Harrison on 3/25/2017.
 */
public class SettlementTest {
    private Settlement settlement0;
    private int settlementID;
    private Hex hex0;

    @Before
    public void setUp() throws Exception {
        hex0 = new Hex();
        settlement0 = new Settlement(hex0, 0);
        settlement0.removeHex(hex0);
    }

    @Test
    public void settlementID() throws Exception {
        hex0 = new Hex();
        settlement0 = new Settlement(hex0, 0);
        Assert.assertEquals(settlement0.getSettlementID(), 0);
    }

    @Test
    public void setSettlementIDTest() throws Exception {
        hex0 = new Hex();
        settlement0 = new Settlement(hex0, 0);

        settlement0.setSettlementID(1);
        Assert.assertEquals(settlement0.getSettlementID(), 1);
        Assert.assertEquals(hex0.getSettlementID(), 1);
    }

    @Test
    public void setSettlementIDWrongTest() throws Exception {
        hex0 = new Hex();
        settlement0 = new Settlement(hex0, 0);

        settlement0.setSettlementID(2);
        Assert.assertNotEquals(settlement0.getSettlementID(), 1);
        Assert.assertNotEquals(hex0.getSettlementID(), 1);
    }

    @Test
    public void addHexToSettlement() throws Exception {
        Hex dummyHex = new Hex(Terrain.JUNGLE);
        settlement0.addHex(dummyHex);
        Assert.assertEquals(settlement0.hexesInSettlement.indexOf(dummyHex), 0);
    }

    @Test
    public void settlementSize() throws Exception {
        Hex dummyHex0 = new Hex(Terrain.JUNGLE);
        Hex dummyHex1 = new Hex(Terrain.GRASSLANDS);
        Hex dummyHex2 = new Hex(Terrain.ROCKY);
        settlement0.addHex(dummyHex0);
        settlement0.addHex(dummyHex1);
        settlement0.addHex(dummyHex2);

        Assert.assertEquals(settlement0.settlementSize(), 3);
        Assert.assertEquals(settlement0.settlementSize(), settlement0.hexesInSettlement.size());
    }

    @Test
    public void wrongSettlementSize() throws Exception {
        Hex dummyHex0 = new Hex(Terrain.JUNGLE);
        Hex dummyHex1 = new Hex(Terrain.GRASSLANDS);
        Hex dummyHex2 = new Hex(Terrain.ROCKY);
        settlement0.addHex(dummyHex0);
        settlement0.addHex(dummyHex1);
        settlement0.addHex(dummyHex2);

        Assert.assertEquals(settlement0.settlementSize(), 3);
        Assert.assertEquals(settlement0.settlementSize(), settlement0.hexesInSettlement.size());
    }

    @Test
    public void getOwner() throws Exception {
        Hex dummyHex = new Hex(Terrain.JUNGLE);
        Player dummyPlayer = new Player("");
        dummyHex.setOwner(dummyPlayer);

        settlement0.addHex(dummyHex);
        Assert.assertEquals(settlement0.getOwner(), dummyPlayer);
    }

    @Test
    public void getOwnerFail() throws Exception {
        Hex dummyHex0 = new Hex(Terrain.JUNGLE);
        Player dummyPlayer0 = new Player("");
        dummyHex0.setOwner(dummyPlayer0);

        Hex dummyHex1 = new Hex(Terrain.JUNGLE);
        Player dummyPlayer1 = new Player("");
        dummyHex1.setOwner(dummyPlayer1);

        settlement0.addHex(dummyHex0);
        Assert.assertNotEquals(settlement0.getOwner(), dummyPlayer1);
    }

}