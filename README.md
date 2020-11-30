# Bulk close Opsgenie alerts by using search queries 

Script runs the given query and retrieves 20 alerts in every iteration and waits for 10 seconds before passing to the next iteration. 


### Compiling format on command line (should first go to the directory where the jar file exists)

java -jar closeAlertwithQuery-all-1.0.jar "YOUR_API_KEY" "YOUR_SEARCH_QUERY"


IMPORTANT : 

 - Make sure you give arguments(both apiKey and query) into quotation marks. 
 - Since there is no re-open alert functionality please double check if your query is valid  and make sure it lists the correct set of alerts on alerts page. (Select "All" as a search filter from left menu and change to "All Time" on seaarch bar.)

