# account-fund-transfer
```
Showcase Fund Transfer
```
### Environment
- Java 8
- Maven 3.5

### Command-line run
```
> mvn clean package
> java -jar target/<jarname>.jar
> Copy Account numbers from console
> run curl command:
for n in {1..5}; do curl -v -X POST http://localhost:8777/api/transfer-money  -H "Content-Type: application/json" -H "Accept: application/json"  -d '{"fromAccountNumber": "<from console>", "toAccountNumber": "<from console>", "amount": 20.00}';done;
```

## Apis
- POST /api/transfer-money

### TODO
- Missing Integration Test :(. Testing it via main method.
- RDBMS use to see locking in action via test
- Application.java is main class to start server and invoke end point
- FundTransferService is handling transfer between accounts.

### Sample Console Output:
```
>> Initialized Account{id=ef9feb6c-cd58-445f-bffd-71429861f55a, clientName='Client-1', balance=10000}
>> Initialized Account{id=f08bc071-52e7-44a3-adfb-d11666b43381, clientName='Client-2', balance=10000}
>> Withdraw : Account{id=ef9feb6c-cd58-445f-bffd-71429861f55a, clientName='Client-1', balance=9980}
>> Deposit: Account{id=f08bc071-52e7-44a3-adfb-d11666b43381, clientName='Client-2', balance=10020}
>> Withdraw : Account{id=ef9feb6c-cd58-445f-bffd-71429861f55a, clientName='Client-1', balance=9960}
>> Deposit: Account{id=f08bc071-52e7-44a3-adfb-d11666b43381, clientName='Client-2', balance=10040}
>> Withdraw : Account{id=ef9feb6c-cd58-445f-bffd-71429861f55a, clientName='Client-1', balance=9940}
>> Deposit: Account{id=f08bc071-52e7-44a3-adfb-d11666b43381, clientName='Client-2', balance=10060}
```