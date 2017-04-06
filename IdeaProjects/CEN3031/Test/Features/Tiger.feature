Feature: Tiger Playground

  Scenario: Player illegally builds Tiger Playground due to attempt to build on volcanic hex
    Given Current user placed a tile
    And User selects volcanic hex
    When User builds Tiger Playground
    Then Output tiger playground build error message and end game with current user as loser

  Scenario: Player illegally builds Tiger Playground due to attempt to build on occupied hex
    Given Current user placed a tile
    And User selects occupied hex, containing either meeples, totoros, or tigers
    When User builds Tiger Playground
    Then Output tiger playground build error message and end game with current user as loser

  Scenario: Player illegally builds Tiger Playground due to attempt to build on insufficient level hex
    Given Current user placed a tile
    And User selects hex that is not at least level three
    When User builds Tiger Playground
    Then Output tiger playground build error message and end game with current user as loser

  Scenario: Player illegally builds Tiger Playground due to attempt to build on hex without an adjacent settlement
    Given Current user placed a tile
    And User selects hex without an adjacent settlement
    When User builds Tiger Playground
    Then Output tiger playground build error message and end game with current user as loser

  Scenario: Player legally builds Tiger Playground
    Given Current user placed a tile
    And User selects a non-volcanic hex
    And User selects an empty hex
    And User selects a hex of level three or higher
    And User selects a hex with an adjacent settlement
    When User builds Tiger Playground
    Then Update user tiger count, the game board, and properties with affected hexes