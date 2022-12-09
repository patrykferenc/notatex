Feature: Render .tex note format in app

  Background:
    Given Application page is open
    And User is logged in
    And User has at least one note in note list

  Scenario:
    When User selects note
    #And User clicks "download button"
    Then User receives .tex file in pdf format
