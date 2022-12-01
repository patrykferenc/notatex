Feature: Test note editing functionality

  Background:
    Given Application page is open
    And User is logged in
    And User has at least one note in note list

  Scenario:
    When User clicks "edit note" button
    Then Note is displayed in .tex format

  Scenario:
    When User clicks "edit note" button
    And User adds text to note
    And User clicks "save" button
    Then Edited note contains added text