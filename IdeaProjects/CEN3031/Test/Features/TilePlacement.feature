Feature: TilePlacement

  Scenario: Tile placement creates a new island
    Given There are existing tiles on the board
    And The most recent tile placed has no adjacent tiles
    When User places such a tile
    Then Output tile placement error and end game such that current player loses

  Scenario: Tile placement creates a new island
    Given There are existing tiles on the board
    And The most recent tile placed has adjacent tiles
    When User places such a tile
    Then Update the game board with placed tile

  Scenario:  Tile placement adds to elevation illegally due to illegal volcano placement
    Given There are existing tiles on the board
    And The volcano hex on most recent tile placed does not cover a volcano hex on board
    When User places such a tile
    Then Output tile placement error and end game such that current player loses

  Scenario: Tile placement adds to elevation illegally due to illegally covering entire tile
    Given There are existing tiles on the board
    And The most recent tile placed covers exactly one tile
    When User places such a tile
    Then Output tile placement error and end game such that current player loses

  Scenario: Tile placement adds to elevation illegally due to introduction of elevation gap
    Given There are existing tiles on the board
    And The most recent tile placed has at least one hex that does not have a hex underneath it
    When User places such a tile
    Then Output tile placement error and end game such that current player loses

