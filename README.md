# sss02
Example Assignment – Super Simple Stock Market

#Requirements

* Provide working source code that will:-
  * For a given stock, 
    * calculate the dividend yield
    * calculate the P/E Ratio
    * Record a trade, with timestamp, quantity of shares, buy or sell indicator and price
    * Calculate Stock Price based on trades recorded in past 15 minutes
  * Calculate the GBCE All Share Index using the geometric mean of prices for all stocks

#Implementation
##General
* The implementation is written in Java language using Maven as project project management tool and Spring as framework.
* The implementation has pieces common with a previous implementation done by **Jaidermes Nebrijo Duarte** (https://github.com/jainebri/Super-Simple-Stocks), some simple pieces of code and XML.
* Care has been tried to be taken to avoid too much similarities.

##Design
* Requirements say that the dividend yield and P/E ratio should be calculated before any trades are recorded.
* That sounds that the real case was that some trades are recorded and then these calculations are done. This is how the solution is designed and implemented. The same applies to Stock Price calculation (15 min), that is, it is calculated after some trades are recorded (as is stated in Requirements).
* Singleton-based, there is only one instance object in run-time of which methods are called in tests. Also there is only one instance object that holds the stocks and trades in run-time.

#Test cases
Before every test case some trades are recorded, and after every test case trades are removed from memory.

#How to use
This is a Maven project, so you can build the project and run the test with e.g.
* mvn test
* mvn clean install



