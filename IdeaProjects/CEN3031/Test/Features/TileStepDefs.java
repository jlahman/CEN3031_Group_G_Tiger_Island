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
        @Given("^Existing tiles$")
        public void game_exists() throws  Throwable{
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        }
        @And("^Tile placed has no adjacent tiles$")
        public void tile_placed_with_no_adjacent_tiles() throws Throwable{
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        }

    @Given("^There are existing previous tiles$")
    public void thereAreExistingPreviousTiles() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^Output tile placement error and end game such that current player loses$")
    public void outputTilePlacementErrorAndEndGameSuchThatCurrentPlayerLoses() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^Volcano hex does not cover a volcano hex$")
    public void volcanoHexDoesNotCoverAVolcanoHex() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @And("^Tile covers exactly one tile$")
    public void tileCoversExactlyOneTile() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @And("^A hex of the placed tile does not have a hex of a tile directly underneath$")
    public void aHexOfThePlacedTileDoesNotHaveAHexOfATileDirectlyUnderneath() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}

