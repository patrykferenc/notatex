Feature: Test login functionality

  Background:
    #Given Application page is open

  Scenario:
    When User fills login field
    And User fills password field
    And User clicks "log in" button
    Then User is on home page