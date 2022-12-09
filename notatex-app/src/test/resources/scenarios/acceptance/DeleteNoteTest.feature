Feature: Test note deletion functionality

  Background:
    Given Application page is open
    And User is logged in
    And User has at least one note in note list

  Scenario:
    When User selects note
    And User clicks "delete button"
    Then Note is not in note list
