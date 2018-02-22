import com.google.gson.Gson;
import okhttp3.*;
import org.apache.http.client.utils.URIBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;


public class CloseWithQuery {
    public static int numOfClosedAlerts = 0;
    public static void main(String[] args) {
        if (args == null || args.length < 2) {
            System.out.println("Please provide apiKey and query. Example usage : \n " +
                    "java -jar closeAlertwithQuery-all-1.0.jar YOUR_API_KEY QUERY");
            return;
        }
        String apiKey = args[0]; //get apiKey from command line
        String query = args[1]; //get query from command line
        listAlerts(apiKey,query);
        System.exit(1);
    }

    public static void listAlerts(String apiKey,String query)
    {
        ApiResponse apiResponse = new ApiResponse();
        try {
            URI uri = new URIBuilder("https://api.opsgenie.com")
                    .setPath("/v2/alerts")
                    .addParameter("searchIdentifierType","id")
                    .addParameter("query", query)
                    .addParameter("limit", "20") // get 20 alerts in every iteration
                    .build();

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(uri.toString())
                    .get()
                    .addHeader("authorization", "GenieKey "+ apiKey)
                    .addHeader("content-type", "application/json")
                    .build();

            do {
                Response response = client.newCall(request).execute();
                String jsondata = response.body().string();

                apiResponse = new Gson().fromJson(jsondata, ApiResponse.class);

                List<AlertMeta> alertMetaList = apiResponse.getData();

                for (AlertMeta data : alertMetaList)
                {
                    String alertId = data.getId();
                    close(apiKey,alertId);
                }
                Thread.sleep(10000); //wait for 10 seconds after 20 alerts closed
                System.out.println("---------");
            }while (apiResponse.getPaging().getNext()!=null);

        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Script has finished. "+ numOfClosedAlerts +" alert(s) has been closed.");
    }

    public static void close(String apiKey,String alertId)
    {
        try {
            OkHttpClient client = new OkHttpClient();
            URI uri = new URIBuilder("https://api.opsgenie.com")
                    .setPath("/v2/alerts/"+ alertId + "/close")
                    .addParameter("identifierType","id")
                    .build();

            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "{}");
            Request request = new Request.Builder()
                    .url(uri.toString())
                    .post(body)
                    .addHeader("authorization", "GenieKey " + apiKey)
                    .addHeader("content-type", "application/json")
                    .build();

            Response response = client.newCall(request).execute();


            if(!response.isSuccessful()) {
                System.out.println("Alert could not closed, skipping, reason : Client Error");
            } else {
                System.out.println("Alert with id [" + alertId + "] will be closed.");
                numOfClosedAlerts++;
            }

        }
        catch (URISyntaxException e) {
            System.out.println("Alert could not closed, skipping, reason : " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
