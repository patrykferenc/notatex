Feature: Test note editing functionality

  Background:
    Given Application page is open
    And User is logged in
    And User has at least one note in note list

  Scenario:
    When User selects note
    And User clicks "edit button"
    And User edits title
    And User edits body
    And User clicks "save button"
    And User clicks "back button"
    Then Edits are visible in note view

