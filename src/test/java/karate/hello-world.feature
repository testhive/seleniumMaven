Feature: karate 'hello world' example
  Scenario: create and retrieve a cat
    Given url 'https://petstore.swagger.io/v2/pet/findByStatus?status=sold'
    When request
    And method get
    Then status 200
