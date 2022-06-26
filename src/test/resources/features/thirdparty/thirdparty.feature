Feature: Third Party Funds Transfer

  Scenario: Valid Payee with Sufficient Balance
    Given user is a Maybank2u user with credentials "anakin" and "ihateyou"
    And user has a valid account number "66666666" with balance of 10001.00
    When user transfers 10000 to account number "999999"
    Then the customer should receive a message saying Your fund transfer of 10000 to "kenobi" is successful

