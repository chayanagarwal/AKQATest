Feature: feature to test bunnings cart functionality

  @cartTest
  Scenario Outline: Validate bunnings cart functionality
    Given browser is open
    And user is on bunnings <Website>
    When user enters text in search box
    And puts click & collect filter
    And Adds item to cart
    And goes to cart
    Then the item put into cart is present properly

    Examples: 
      | Website                     |
      | "https://www.bunnings.com.au" |
