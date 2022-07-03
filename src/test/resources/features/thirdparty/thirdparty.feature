Feature: As a Maybank customer, I want to transfer money from my Maybank account to another Maybank account so I can send money to my friends or families

  Scenario: Valid Payee with sufficient balance
    Given I am a Maybank2u user with credentials "anakin" and "ihateyou"
    And I have a valid account number "66666666" with balance of 10001.00
    When I transfer 10000.00 to account number "999999" that belongs to "kenobi"
    Then I should receive a message saying Your fund transfer of 10000.00 to "kenobi" is successful
    And my available balance for account number "66666666" is now 1.00
    And my transaction history for account number "66666666" reads like below
      | date       | transactiondetails   | fromAccount | toAccount | amount | referenceNumber |
      | 05-07-2022 | third party transfer | 66666666    | 99999999  | 10000  | 000000001       |


