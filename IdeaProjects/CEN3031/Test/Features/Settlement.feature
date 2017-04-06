Feature: Settlement

  Scenario: Player illegally creates settlement due to insufficient amount of meeples
    Given Current user placed a tile
    And It is the current user's turn
    And User does not have at least one meeple
    When User creates settlement
    Then Output settlement creation error message and end game such that current player loses