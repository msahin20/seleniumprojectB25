
Feature: The application should be running

  @smoke
  Scenario: simple search
    Given it starts
    Then I should see the results
