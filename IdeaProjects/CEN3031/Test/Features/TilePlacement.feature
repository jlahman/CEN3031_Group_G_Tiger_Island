Feature: TilePlacement

  Scenario: Tile placement creates a new island
    Given There are existing previous tiles
    When Tile placed has no adjacent tiles
    Then Output tile placement error and end game such that current player loses

  Scenario:  Tile placement adds to elevation illegally
    Given Existing tiles
    When Volcano hex does not cover a volcano hex
    And Tile covers exactly one tile
    And A hex of the placed tile does not have a hex of a tile directly underneath
    Then Output tile placement error and end game such that current player loses

