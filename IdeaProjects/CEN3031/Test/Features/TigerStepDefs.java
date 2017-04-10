import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class TigerStepDefs {
    Tile t1 = new Tile(TileType.JJ);
    Board b = new Board();
    Player p = new Player("user1");
    BuildOptions bo = new BuildOptions();
    boolean selectVolcano = false;




    @And("^User selects occupied hex, containing either meeples, totoros, or tigers$")
    public void userSelectsOccupiedHexContainingEitherMeeplesTotorosOrTigers() throws Throwable {

    }

    @And("^User selects hex that is not at least level three$")
    public void userSelectsHexThatIsNotAtLeastLevelThree() throws Throwable {
     assertTrue("Tile level less than 3",t1.getTilelevel()<3);
    }

    @And("^User selects hex without an adjacent settlement$")
    public void userSelectsHexWithoutAnAdjacentSettlement() throws Throwable {
       assertFalse("No adjacent settlement" , bo.isBuildSettlementValid(b.getAdjHex(b.rootHex,0), 20));

    }


    @And("^User selects an empty hex$")
    public void userSelectsAnEmptyHex() throws Throwable {


    }

    @And("^User selects a hex of level three or higher$")
    public void userSelectsAHexOfLevelThreeOrHigher() throws Throwable {
        t1.setLevel(3);
        assertTrue("Tile level less than 3",t1.getTilelevel()>=3);

    }

    @And("^User selects a hex with an adjacent settlement$")
    public void userSelectsAHexWithAnAdjacentSettlement() throws Throwable {

    }

    @When("^User builds Tiger Playground$")
    public void userBuildsTigerPlayground() throws Throwable {
        bo.buildTigerSanctuary(b,t1.getTileHex(0),p);
    }

    @Then("^Output tiger playground build error message and end game with current user as loser$")
    public void outputTigerPlaygroundBuildErrorMessageAndEndGameWithCurrentUserAsLoser() throws Throwable {
        b.placeTile(t1,b.rootHex,0);
        bo.buildTigerSanctuary(b,t1.getTileHex(0),p);
    }

    @Then("^Update user tiger count, the game board, and properties with affected hexes$")
    public void updateUserTigerCountTheGameBoardAndPropertiesWithAffectedHexes() throws Throwable {
        bo.BFSForTerrain(b,b.rootHex, Terrain.JUNGLE);
    }
}
