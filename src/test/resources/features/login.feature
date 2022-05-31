
Feature: The application should be running


  Scenario: simple search
    Given it starts
    Then I should see the results

  @smoke
    Scenario: second
      Then I should see the results
