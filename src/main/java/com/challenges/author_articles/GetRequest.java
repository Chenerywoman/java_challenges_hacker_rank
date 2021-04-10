package com.challenges.author_articles;

import java.io.*;
import java.util.*;
import java.net.URL;
import java.net.MalformedURLException;
import javax.net.ssl.HttpsURLConnection;
import java.net.URLEncoder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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

    public static String getResponse(String author, String page){

        try {

            // set parameters in a HashMap
            Map<String, String> parameters = new HashMap<>();
            parameters.put("author", author);
            parameters.put("page", page);

            // transform Map into String of Required format
            StringBuilder params = new StringBuilder();

            for (Map.Entry<String, String> entry : parameters.entrySet()){
                params.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                params.append("=");
                params.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
                params.append("&");
            }

            // format the params
            String paramsString = params.toString();
            String formattedParams = paramsString.substring(0, paramsString.length() -1);

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

            // read response of the request and place in content string
            StringBuilder content = new StringBuilder();

            Reader streamReader = new InputStreamReader(con.getInputStream());
            BufferedReader in = new BufferedReader(streamReader);
            String inputLine;
            while ((inputLine = in.readLine()) !=null){
                content.append(inputLine);
            }
            in.close();

            // close the connection
            con.disconnect();

            // convert content from StringBuffer to String
            String response = content.toString();
            return response;

        } catch (MalformedURLException exception){

            exception.printStackTrace();
            return null;
        }  catch (IOException exception){

            exception.printStackTrace();
            return null;
        }
    }

    public static List<String> extractTitles(JSONArray JsonArray){

        List<String> titles = new ArrayList<>();

        // loop through JSON array
        for (int i = 0; i < JsonArray.size(); i++){

            JSONObject object = (JSONObject)JsonArray.get(i);

            if (object.get("title") != null){
                titles.add(object.get("title").toString());
            } else if (object.get("story_title") != null){
                titles.add(object.get("story_title").toString());
            }
        }

        return titles;
    }

    public static List<String> getArticleTitles(String author) {

        List<String> titles = new ArrayList<>();
        String number = "1";

        String response = Result.getResponse(author, number);

        try {
            // parse String to JSON object & extract data as JSONArray
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(response);
            JSONObject JsonObject = (JSONObject)obj;
            JSONArray JsonArray = (JSONArray)JsonObject.get("data");
            int total_pages = Integer.parseInt(JsonObject.get("total_pages").toString());

            List<String> firstTitles = extractTitles(JsonArray);
            titles.addAll(firstTitles);

            if (total_pages > 1){
                for (int j = 2; j < total_pages + 1; j++ ){

                    String responseTwo = Result.getResponse(author, String.valueOf(j));
                    JSONObject JsonObjectTwo = (JSONObject)parser.parse(responseTwo);
                    JSONArray JsonArrayTwo = (JSONArray)JsonObjectTwo.get("data");
                    System.out.println(JsonArrayTwo);

                    for (int k = 0; k < JsonArrayTwo.size(); k++){

                        JSONObject object = (JSONObject)JsonArrayTwo.get(k);

                        if (object.get("title") != null){
                            titles.add(object.get("title").toString());
                        } else if (object.get("story_title") != null){
                            titles.add(object.get("story_title").toString());
                        }

                    }

                }

            }
        } catch (ParseException exception){
            exception.printStackTrace();
        }

        //System.out.println(titles);
        return  titles;
    }
}