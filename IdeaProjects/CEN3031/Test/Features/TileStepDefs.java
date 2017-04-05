import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.java8.En;

/**
 * Created by kevin on 3/25/2017.
 */
public class TileStepDefs implements En {

    @Given("^No tiles have been placed on the board$")
    public void noTilesHaveBeenPlacedOnTheBoard() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Given("^There are existing tiles on the board$")
    public void thereAreExistingPreviousTiles() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @And("^The most recent tile placed has no adjacent tiles$")
    public void tile_placed_with_no_adjacent_tiles() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @And("^The most recent tile placed has adjacent tiles$")
    public void tilePlacedHasAdjacentTiles() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @And("^The volcano hex on most recent tile placed does not cover a volcano hex on board$")
    public void theVolcanoHexOnMostRecentTilePlacedDoesNotCoverAVolcanoHexOnBoard() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @And("^The most recent tile placed covers exactly one tile$")
    public void tileCoversExactlyOneTile() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @And("^The most recent tile placed has at least one hex that does not have a hex underneath it$")
    public void theMostRecentTilePlacedHasAtLeastOneHexThatDoesNotHaveAHexUnderneathIt() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^User places such a tile$")
    public void userPlacesSuchATile() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^Output tile placement error and end game such that current player loses$")
    public void outputTilePlacementErrorAndEndGameSuchThatCurrentPlayerLoses() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^Update the game board with placed tile$")
    public void updateTheGameBoardWithPlacedTile() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}