Feature: Intrabank Funds Transfer

  Scenario: Valid Payee with sufficient balance
    Given I am a jambhala user with credentials "anakin" and "ihateyou"
    And I have a valid account number "1000000066" with balance of 10001.00
    And my wife "padme" has a valid account number "1000000099"
    When I transfer 10000.00 from "1000000066" to "1000000099"
    Then I should receive a message saying "Your third party transfer of 10000.00 to padme is successful."
    And my transaction history for account number "1000000066" reads like below
      | date        | transactionType      | fromAccount | toAccount  | amount   | referenceNumber |
      | TODAYS_DATE | third party transfer | 1000000066  | 1000000099 | 10000.00 | 1               |

  Scenario: Invalid Payee
    When the payee account number "1000000089" is an invalid rimaubank account number
    Then I should receive a message saying "Payee not found"