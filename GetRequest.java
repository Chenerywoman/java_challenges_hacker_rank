import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import com.google.gson.Gson;

import java.net.URL;
import java.net.MalformedURLException;
import java.net.HttpURLConnection;
import javax.net.ssl.HttpsURLConnection;
import java.net.URLEncoder;

class Result {

    /*
     * Complete the 'getArticleTitles' function below.
     *
     * The function is expected to return a STRING_ARRAY.
     * The function accepts STRING author as parameter.
     *
     * URL for cut and paste:
     * https://jsonmock.hackerrank.com/api/articles?author=<authorName>&page=<num>
     *
     */

    public static List<String> getArticleTitles(String author) {

        // set parameters in a HashMap
        Map<String, String> parameters = new HashMap<>();
        parameters.put("author", author);
        parameters.put("page", "1");

        try {

            // transform Map into String of Required format
            StringBuilder params = new StringBuilder();
            for (Map.Entry<String, String> entry : parameters.entrySet()){
                params.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                params.append("=");
                params.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
                params.append("&");
            }

            String paramsString = params.toString();
            String formattedParams = paramsString.substring(0, paramsString.length() -1);
            System.out.println(formattedParams);

            String urlString = "https://jsonmock.hackerrank.com/api/articles?";
            String urlWithParams = urlString.concat(formattedParams);

            URL url = new URL(urlWithParams);

            // set connection, request method and headers
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-type", "application/json");

            // set connection and read timeouts
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);

            // get the response code
            int status = con.getResponseCode();
            System.out.println(status);

            Reader streamReader = null;
            StringBuffer content = new StringBuffer();

            streamReader = new InputStreamReader(con.getInputStream());
            // read response of the request and place in content string
            BufferedReader in = new BufferedReader(streamReader);
            String inputLine;
            while ((inputLine = in.readLine()) !=null){
                content.append(inputLine);
            }
            in.close();

            System.out.println(content.toString());
            // close the connection
            con.disconnect();
        } catch (MalformedURLException exception){

            exception.printStackTrace();

        } catch (IOException exception){

            exception.printStackTrace();
        }

        List<String> titles = new ArrayList<String>();

        return  titles;
    }
}

