# MiniBank - Practical 14

## Features
- Account creation
- Deposits and withdrawals
- Fund transfers
- Account statements and listing
- Account persistence
- End-of-day reporting
- Concurrent transaction demonstration
- JUnit 5 test cases

## Technologies
- Java
- Collections and Generics
- Serialization
- Exception Handling
- Multithreading
- JUnit 5

## Compile
javac -d out (Get-ChildItem src -Recurse -Filter *.java | ForEach-Object FullName)

## Run
java -jar MiniBank.jar

## Testing
JUnit tests are located in the test directory.
Maven is required to execute the tests using pom.xml.

## Data Files
- data/accounts.dat
- data/end-of-day-report.txt
