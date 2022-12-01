Feature: Test note adding functionality

  Background:
    Given Application page is open
    And User is logged in
    And User has at least one note in note list

  Scenario:
    When User clicks "delete note" button
    And User clicks "confirm" button
    Then Note is not in note list