bsc java test code 

author Stanislav Kresina stanislav@kresina.net

how to run
init local git repository
git init

pull code from github
git pull https://github.com/kresinas-centrum-cz/bsc.git

build application with maven
mvn package

run with java
java -jar target\JavaTest-0.0.1-SNAPSHOT-jar-with-dependencies.jar
eventually with input file
java -jar target\JavaTest-0.0.1-SNAPSHOT-jar-with-dependencies.jar c:\testInput.txt

assumptions:
1. testInput.txt file if presented will be parsed line by line and each line will be validated separately, for invalid input line 
Incorrect input, please use format XXX nnnn e.g. CZK 500000 (type 'quit' to exit) 
will be displayed, still valid lines will be read. 

2. exchange rates are defined in conversioins.properties file, for JUnit tests to be run successfully, the CZK conversion rate must not be modified :-( new conversion rates could be added.

3. if incorrectly formated input is given, user will get  
Incorrect input, please use format XXX nnnn e.g. CZK 500000 (type 'quit' to exit)
message, this is not reason to quit. 

4. when run, the report.log file will be created, this should have detailed logs for debugging purposes

5. once per minute record will be outputed to the console e.g.
HKD 100,00 (USD 12,89)
CZK 5005,00 (USD 196,49)
RMB 2000,00 (USD 319,50)
USD 500,00 

6. type quit to exit program

7. enjoy :-)