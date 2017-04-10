Feature: Settlement
  User can either create (found) settlements or expand existing settlements

  Scenario: Player illegally creates settlement due to insufficient amount of meeples
    Given Current user placed a tile
    And User does not have at least one meeple
    When User creates settlement
    Then Output settlement creation error message and end game such that current player loses

  Scenario: Player illegally creates settlement on volcano
    Given Current user placed a tile
    And User has at least one meeple
    And User selects volcanic hex
    When User creates settlement
    Then Output settlement creation error message and end game such that current player loses

  Scenario: Player legally creates (founds) a settlement
    Given Current user placed a tile
    And User has at least one meeple
    And User selects a non-volcanic hex
    When User creates settlement
    Then Create a new settlement of size one and place one meeple on the hex

  Scenario: Player illegally expands on terrain
    Given Current user placed a tile
    And User has enough meeples to expand
    And User has at least one settlement
    And Adjacent terrain to be expanded to does not match the current hex's terrain
    When User expands settlement
    Then Output settlement expansion error message and end game such that current player loses

  Scenario: Player illegally expands due to insufficient meeples
    Given Current user placed a tile
    And User has at least one settlement
    And User does not have enough meeples to expand
    When User expands settlement
    Then Output settlement expansion error message and end game such that current player loses

  Scenario: Player legally expands settlement
    Given Current user placed a tile
    And User has at least one settlement
    And User has enough meeples to expand
    And Adjacent terrain to be expanded to matches the current hex's terrain
    When User expands settlement
    Then Expand settlement and update settlement size, settlement list, and hexes in settlement