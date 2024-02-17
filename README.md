This is a simple spring boot application, which can be used to calculate and display the potential tax for a user of a betting site.

How to run and test the application
1. Run .\mvnw spring-boot:run
   The repo has a maven wrapper and this command will run a spring boot application. By default the server will run on port 8080, if that port is already occupied and server does not start you can append
   '-Dspring-boot.run.arguments=--server.port=8085' to the above command to run it on a different port.
2. Import the postman collection and environment
   In case the default port application was changed, change the port environment variable under Localhost -> port
3. Run the requests from postman

Assumptions

In order to be able to implement the task I made some assumptions as those things were not clear to me from the task. Normally I would ask about those things and clarify them before starting development. Not everything makes sense currently, but I didn't find another explanation where everything would make sense. 
In case some of the assumptions are wrong feel free to correct them and I can fix the code.

1. General or winnings tax type is based on trader country, each country has different regulation and rules
2. Traders are registered with single country on the service and stored in DB
3. Country tax rules and regulations are generally stable and do not change often
4. My explanation of response fields:
   * possibleReturnAmount - amount the trader would receive in case of winning, calculated using odd * betAmount 
   * possibleReturnAmountBefTax - same as possibleReturnAmount
   * possibleReturnAmountAfterTax - amount the trader would receive after winning and subtracting the tax. Tax calculation logic is described bellow.
   * taxRate - rate for rate based for tax calculation based on trader country
   * taxAmount - amount for amount based for tax calculation based on trader country
5. The tax used for calculating possibleReturnAmountAfterTax is the lower tax between tax rate based calculation and tax amount calculation. Using tax amount in all cases does not sense when using low bet amounts as it would be more than winnings and the return after tax would be negative therefore we choose the lower tax.

Potential improvements

1. Using persistent data storage - I used in-memory H2 database as it requires no additional set up and starts up together with the service. The data is lost on restart so this is not usable for production environment.
2. Cache on trader retrieval - operation will be executed multiple times and the trader country should not change often