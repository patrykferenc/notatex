Feature: Test note adding functionality

  Background:
    Given Application page is open
    And User is logged in
    And Note count is known

  Scenario:
    When User clicks "create button"
    And User fills "title field" with "tytul"
    And User fills "body field" with "zawartosc"
    And User clicks "save button"
    Then Note is in note list
