Feature: Users  can make transfer from one account into another

  Scenario Outline: User can make a money transfer between accounts
    Given account <sourceAccount> with balance <sourceBalance>
    And account <targetAccount> with balance <targetBalance>
    When user makes transfer of <transfer> with note <description>
    Then transfer is completed
    And source account has balance <sourceBalance> reduced by <transfer>
    And target account has balance <targetBalance> increased by <transfer>
  Examples:
  | sourceAccount | sourceBalance | targetAccount | targetBalance | transfer | description |
  | Foo           | 123.99        | bar           | 22.01         | 51.99    | Transfer 1  |
  | Weird Account | 20            | Lucky One     | 0.01          | 19.99    | Transfer 2  |
