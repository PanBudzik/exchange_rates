package org.example;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class JSONExchangeRates {
    public static void main(String[] args) throws Exception {
        URL url = new URL("https://api.exchangerate.host/latest?base=USD");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        if(connection.getResponseCode()!=200){
            System.out.println("No 200 response quit");//request succeeded
        }

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(connection.getInputStream())
        );

        String str;
        StringBuffer stringBuffer = new StringBuffer();

        while ((str=reader.readLine())!=null){
            stringBuffer.append(str);
        }

        String jsonStr = stringBuffer.toString();
        System.out.println(jsonStr);

        JSONObject jsonObject = new JSONObject(jsonStr);
        JSONObject rates = jsonObject.getJSONObject("rates");//all
        String dateStr = jsonObject.getString("date");
        System.out.println("Rates date: "+dateStr);
        System.out.println("Base: "+jsonObject.getString("base"));//send by GET in URL

        //neat way to write json
        Map<String,Object> objMap = rates.toMap();
        objMap.forEach((key,value)-> System.out.println(key+" : "+value));

        reader.close();
    }
}
