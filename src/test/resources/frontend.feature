Feature: CMC Front-end tests
  Background:
    Given CMC Homepage is opened
    #This step is pre-req to all the scenarios

  Scenario: Test 1
    When user clicks View All to display all results
    Then 200 results are displayed
      # by default 100 results are displayed,
      # when we click on "View All" 200 results will be displayed.
      # So this test will fail. (Else we change the requirement).

  Scenario: Test 2
    When user randomly selects 5 to 10 cryptos and add to watchlist
    And In a new browser tab, cmc watchlist is opened
    Then selected items are displayed correctly in watchlist

  Scenario Outline: Test 3
    When any of the three full list items are selected in cryptocurrencies tab <group>
    And available filters are applied <market cap>, <price>, & <volume>
    #Five filters are available on, First three are chosen for implementation
    Then filtered results should be displayed
    Examples:
      | group                 | market cap            | price   | volume      |
      | All Cryptocurrencies  | 1000000 - 1000000000  | 1 - 100 | All         |
      | Coins Only            | 5000000 - 5000000000  | All     | 1 - 10      |
      | Tokens Only           | All                   | 2 - 5   | 100 - 1000  |