Feature: Test note adding functionality

  Background:
    Given Application page is open
    And User is logged in

  Scenario:
    When User clicks "add new note" button
    And User selects note to add
    And User clicks "confirm" button
    Then Note is in note list