Feature: Totoro

  Scenario: Player illegally places totoro due to insufficient totoro
    Given Current user placed a tile
    And It is the current user's turn
    And User has no totoros
    When User builds totoro sanctuary
    Then Output totoro placement error message and end game such that current player loses

  Scenario: Player illegally places totoro due to insufficient settlement size
    Given Current user placed a tile
    And It is the current user's turn
    And There is no settlement size of at least five, adjacently to the terrain hex selected
    When User builds totoro sanctuary
    Then Output totoro placement error message and end game such that current player loses

   Scenario: Player illegally places totoro on existing settlement of other player
     Given Current user placed a tile
     And It is the current user's turn
     And Player selects hex of another player's settlement
     When User builds totoro sanctuary
     Then Output totoro placement error message and end game such that current player loses

  Scenario: Player illegally places totoro sanctuary on existing totoro sanctuary
     Given Current user placed a tile
     And It is the current user's turn
     And User selects a hex with existing totoro sanctuary
     When User builds totoro sanctuary
     Then Output totoro placement error message and end game such that current player loses

   Scenario: Player legally places totoro sanctuary
     Given Current user placed a tile
     And It is the current user's turn
     And Player selects a hex belonging to him/herself
     And There is a settlement size of at least five, adjacently to the terrain hex selected
     When User builds totoro sanctuary
     Then Update player's totoro count, the game board, and settlements