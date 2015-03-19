package robsmith.transit;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by robsmith on 3/14/2015.
 */
public class NetworkHelper {
    public static JSONObject getContents(URL url) {
        System.out.println(url);
        JSONParser parser = new JSONParser();

        try {
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(urlConnection.getInputStream()));
            String urlString = "";
            String current;
            while ((current = in.readLine()) != null) {
                urlString += current;
            }
            //System.out.println(urlString);

            Object obj = parser.parse(urlString);

            JSONObject jsonObject = (JSONObject) obj;

            return jsonObject;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
