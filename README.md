# fxcurrenyprocessor
SpringBoot MVC application with MongoDB-JPA to process Fx deals

1-download the application in to a local directory
2-in the command prompt navigate to the parent directory  \currencyprocessor
3- run this command to start the fxprocessor       mvn spring-boot:run         
4- process on start up continuosly looks for a file to process in the dir \currencyprocessor\files\input
5- the web interface can now be accessed using the browser URL http://localhost:8080/
6- UI has options to upload a file to process
7- once uploaded the search section can be used to view loaded data by entering the filename.
8- At present this UI has a limitation that only maximum 50 records can be displayed. Pagination is not complete.
9- Appropriate exception handling has been incorporated
10- Unit test can be invoked by navigatiing to the parent directory  \currencyprocessor and running the command  mvn clean install
11- A sample test file has been provided to test this application and is located under  \currencyprocessor\files\test\fxdeals_2017_05_27-06-03-02.csv
12- this test file has 100000 records
