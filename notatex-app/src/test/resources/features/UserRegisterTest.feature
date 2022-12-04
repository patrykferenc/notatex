Feature: Test user register functionality

  Background:
    #Given Application page is open
    And New account parameters are not occupied

  Scenario:
    When User clicks "register" button
    And User fills login field
    And User fills password field
    And User fills confirm password field
    And User clicks "register" button
    Then User can log in to created account