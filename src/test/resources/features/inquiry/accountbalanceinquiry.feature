Feature: Account Balance Inquiry
  
  Scenario: Valid Account Number With Positive Balance
    Given I am a jambhala user with username "anakin"
    When have a valid account number "100000000066" with balance of 100000.00
    Then I should see the balance on my dashboard as IDR 100,000.00
    