Feature: As a Maybank customer, I want to transfer money from my Maybank account to another Maybank account so I can send money to my friends or families

  Scenario: Valid Payee
    Given user is a Maybank2u user with credentials "anakin" and "ihateyou"
    And user has a valid account number "66666666"
    And account "6666666" has balance of 10001
    When user transfers 10000 to account number "999999"
    Then the customer should receive a message saying Your fund transfer of 10000 to "kenobi" is successful

