# dbtest
This solution is written as a Spring Boot application with general core spring and scheduling tasks.
Unit test cases have 96% of the code coverage.

This solution comprised of 2 parts:
1. Scheduler- as of now, for the testing purposes, this is running every 5 seconds and can be scheduled for running daily
2. TradeProcessor: Backend service the client need to autowire to use the processTrade() method with TradeDetails populated as below:
  String tradeId;
  Long version;
  String counterPartyId;
  String bookId;
  LocalDate maturityDate;
  LocalDate createdDate;
  String expired;
  
  Validations applied:
  Maturity date should not be passed date
     * 1. If trade already exists in trade store,
     *  1.1 if with lower version, throw exception
     *  1.2 if with same version, override the current trade details
     *  1.4 if with greater version, save the new trade details
     * 2. If trade does not exists, save the trade in trade store
