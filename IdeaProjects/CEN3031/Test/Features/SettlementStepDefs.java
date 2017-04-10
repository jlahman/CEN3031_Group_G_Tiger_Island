import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class SettlementStepDefs {

    Tile t1 = new Tile(TileType.JJ);
    Board b = new Board();
    Player p = new Player("user1");
    BuildOptions bo = new BuildOptions();
    boolean selectVolcano = false;
    //}

    @Given("^Current user placed a tile$")
    public void currentUserPlacedATile() throws Throwable {
        b.placeTile(t1, b.rootHex, 0);
        assertEquals(t1.getTileHex(0), b.rootHex);
        assertEquals(t1.getTileHex(1), b.getAdjHex(b.rootHex, 0));
        assertEquals(t1.getTileHex(2), b.getAdjHex(b.rootHex, 1));


    }

    @And("^It is the current user's turn$")
    public void itIsTheCurrentUserSTurn() throws Throwable {
        // Confirmed in other given/test
        throw new PendingException();
    }

    @And("^User does not have at least one meeple$")
    public void userDoesNotHaveAtLeastOneMeeple() throws Throwable {
        int x = 20;
        p.setMeepleCount(0);
        assertEquals(0, p.getMeepleCount());
       // assertEquals();
        //throw new PendingException();
    }

    @And("^User has at least one meeple$")
    public void userHasAtLeastOneMeeple() throws Throwable {

        assertTrue("user Has At Least One Meeple", p.getMeepleCount()>0);
    }

    @And("^User has enough meeples to expand$")
    public void userHasEnoughMeeplesToExpand() throws Throwable {
        assertTrue("Meeple number is greater or equal to tile level to expand",p.getMeepleCount()>= t1.getTilelevel());
    }

    @And("^User has at least one settlement$")
    public void userHasAtLeastOneSettlement() throws Throwable {
        if(b.settlementList.size()==0){
            bo.buildSettlement(b,b.rootHex,p);
            Settlement settlement0 = new Settlement(b.rootHex, 0);
            b.settlementList.add(0,settlement0);
        }
        assertTrue("There is at least one settlement", b.settlementList.size()>0);
    }

    @And("^Adjacent terrain to be expanded to does not match the current hex's terrain$")
    public void adjacentTerrainToBeExpandedToDoesNotMatchTheCurrentHexSTerrain() throws Throwable {
        Tile t2 = new Tile(TileType.GG);
        b.placeTile(t1, b.rootHex, 0);
        b.placeTile(t2, b.getAdjHex(t1.getTileHex(2), 3), 1);
        b.isPlacementValid(t1,t1.getTileHex(0),2);
    }

    @And("^User does not have enough meeples to expand$")
    public void userDoesNotHaveEnoughMeeplesToExpand() throws Throwable {
        if(p.getMeepleCount()>=t1.getTilelevel()){
            p.setMeepleCount(t1.getTilelevel()-1);
        }
        assertTrue("Meeple number is greater or equal to tile level to expand",p.getMeepleCount()< t1.getTilelevel());

    }

    @And("^Adjacent terrain to be expanded to matches the current hex's terrain$")
    public void adjacentTerrainToBeExpandedToMatchesTheCurrentHexSTerrain() throws Throwable {
        Tile t2 = new Tile(TileType.JJ);
        b.placeTile(t1, b.rootHex, 0);
        b.placeTile(t2, b.getAdjHex(t1.getTileHex(2), 3), 1);
        b.isPlacementValid(t1,t1.getTileHex(0),2);
    }

    @When("^User creates settlement$")
    public void userCreatesSettlement() throws Throwable {
        bo.buildSettlement(b,b.rootHex,p);
        //throw new PendingException();
    }

    @When("^User expands settlement$")
    public void userExpandsSettlement() throws Throwable {
        Settlement settlement1Test = b.settlementList.get(0);
   // bo.expandSettlement(b, settlement1Test.getSettlementID(),t1.getTerrainOne(t1.getTileType()),p);
    }

    @And("^User selects volcanic hex$")
    public void userSelectsVolcanicHex() throws Throwable {
         selectVolcano = true;
    }

    @And("^User selects a non-volcanic hex$")
    public void userSelectsANonVolcanicHex() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        selectVolcano = false;
    }

    @Then("^Output settlement creation error message and end game such that current player loses$")
    public void outputSettlementCreationErrorMessageAndEndGameSuchThatCurrentPlayerLoses() throws Throwable {
        Settlement settlement0 = new Settlement(b.rootHex, 0);
        b.settlementList.add(0,settlement0);
        String terrainName = "JUNGLE";
        if(selectVolcano == true)
        {
            terrainName = "VOLCANO";
        }
        assertFalse(bo.isExpandSettlementValid(b, settlement0.getSettlementID(),  Terrain.valueOf(terrainName), p.getMeepleCount()));
    }

    @Then("^Create a new settlement of size one and place one meeple on the hex$")
    public void createANewSettlementOfSizeOneAndPlaceOneMeepleOnTheHex() throws Throwable {

        bo.buildSettlement(b,t1.getTileHex(0),p);
    }

    @Then("^Output settlement expansion error message and end game such that current player loses$")
    public void outputSettlementExpansionErrorMessageAndEndGameSuchThatCurrentPlayerLoses() throws Throwable {
        // Write code here that turns the phrase above into concrete actions

    }

    @Then("^Expand settlement and update settlement size, settlement list, and hexes in settlement$")
    public void expandSettlementAndUpdateSettlementSizeSettlementListAndHexesInSettlement() throws Throwable {

    }

}
