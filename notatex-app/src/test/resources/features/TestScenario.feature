Feature: Test if cucumber framework is working

  Background:

    Given Google page is open

  Scenario:
    When User searches for "Saul goodman 3d"
    Then Google returns results for "Saul goodman 3d"