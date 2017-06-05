package apiproject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

//Using org.json library for JSON parsing 
import org.json.*;

/**
 *
 * @author Eric Ruppert, Maio
 */
public class APIProject {

    private static String API_KEY;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //Groups GitHub is public. Removing API key for obvious reasons.
        try {
            URL api_url = APIProject.class.getResource("wundergroundkey.ign");
            API_KEY = new Scanner(new File(api_url.getPath())).next();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        //Credit to Weather Channel API as required per terms of service
        System.out.println("Weather Channel API");

        //The question we're trying to answer
        System.out.println("Is it going to rain tomorrow?");

        //The url for API access with my API key and the embedded GET request
        String url = "http://api.wunderground.com/api/" + API_KEY + "/forecast/q/US/PA/State_College.json";
        String responseText = "{}";
        try {
            //Process a GET request from the server and store the response in responseText
            URLConnection connection = new URL(url).openConnection();
            InputStream response = connection.getInputStream();
            try (Scanner apiIn = new Scanner(response)) {
                responseText = apiIn.useDelimiter("\\A").next();

            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        //Convert string to JSONObject
        JSONObject obj = new JSONObject(responseText);

        //Pull tomorrow's Possibility of Percipitation (pop)
        String percipitationChance
                = obj.getJSONObject("forecast").
                getJSONObject("txt_forecast").getJSONArray("forecastday").
                getJSONObject(2).getString("pop");
        System.out.println("Chance of percipitation tomorrow is " + percipitationChance + "%");

    }

}
