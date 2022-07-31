Feature: As a Maybank customer, I want to transfer money from my Maybank account to another Maybank account so I can send money to my friends or families

  Scenario: Valid Payee with sufficient balance
    Given I am a Maybank2u user with credentials "anakin" and "ihateyou"
    And I have a valid account number "1000000066" with balance of 10001.00
    And my wife "padme" has a valid account number "1000000099"
    When I transfer 10000.00 from "1000000066" to "1000000099"
    Then I should receive a message saying Your fund transfer of 10000.00 to "padme" is successful
    And my transaction history for account number "1000000066" reads like below
      | transactionDate | transactionDetails   | fromAccount | toAccount  | amount   | referenceNumber |
      | 05-07-2022      | third party transfer | 1000000066  | 1000000099 | 10000.00 | 000000001       |


