package org.test.pwgui;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

public class JSON_Reader {

    // ArrayList variable to hold the credentials
    private ArrayList credential_arrList;

    // Constructor
    public JSON_Reader() {
    }

    public String readMaster(String fileName) {
        Object obj;
        try {
            obj = new JSONParser().parse(new FileReader(fileName));
        } catch (ParseException | IOException e) {
            System.out.println(e);
            return "null";
        }

        JSONObject jso = (JSONObject) obj;

        return (String) jso.get("m_username");
    }

    public String read_pass(){
        String filename = "master_creds.json";
        Object obj;
        try {
            obj = new JSONParser().parse(new FileReader(filename));
        } catch (ParseException | IOException e) {
            System.out.println(e);
            return "null";
        }
        JSONObject jso = (JSONObject) obj;
        return (String) jso.get("m_password");
    }


    public ArrayList read(String fileName) throws Exception {

        // Initialize ArrayList to hold exported JSON credentials
        credential_arrList = new ArrayList();

        // Initializes JSONParser and parses JSON data from fileName
        // into variable of type Object if no ParseException or IOException
        // is thrown. Otherwise, sets obj to null.
        Object obj;
//        try {
//            obj = new JSONParser().parse(new FileReader(fileName));
//        } catch (ParseException | IOException e) {
//            System.out.println("wsz" + e);
//            obj = null;
//        }

        obj = new JSONParser().parse(new FileReader(fileName));

        // Converts obj to type JSONObject at variable jso if obj is not null
        if (obj != null) {
            JSONObject jso = (JSONObject) obj;

            // Obtains JSONArray located at field "credentials" of JSON
            JSONArray jsonArray = ((JSONArray) jso.get("credentials"));

            // Declare 'creds' String array to hold the different fields
            // for each JSON
            String[] creds;

            // For loop to iterate through all elements in the JSONArray
            for (int i = 0; i < jsonArray.size(); i++) {

                // Initialize creds String array
                creds = new String[3];

                // Initialize jso variable to JSONObject at current index i
                jso = (JSONObject) jsonArray.get(i);

                // Gets "website" field and places it in index 0 of creds array
                creds[0] = (String) jso.get("website");

                // Sets remaining creds indexes as follows
                // creds[1] = Username
                // creds[2] = Password
                creds[1] = (String) jso.get("username");
                creds[2] = (String) jso.get("password");

                // Adds creds String arr to credential_arrList
                credential_arrList.add(creds);
            }

            // Returns the ArrayList with credentials
            return credential_arrList;
        } else {
            // Else, function returns null
            return (ArrayList) obj;
        }
    }

    public JSONArray getJSONArray(String fileName) {
        // Initialize ArrayList to hold exported JSON credentials
        credential_arrList = new ArrayList();

        // Initializes JSONParser and parses JSON data from fileName
        // into variable of type Object if no ParseException or IOException
        // is thrown. Otherwise, sets obj to null.
        Object obj;
        try {
            obj = new JSONParser().parse(new FileReader(fileName));
        } catch (ParseException | IOException e) {
            System.out.println(e);
            obj = null;
        }

        // Converts obj to type JSONObject at variable jso if obj is not null
        if (obj != null) {
            JSONObject jso = (JSONObject) obj;

            // Obtains JSONArray located at field "credentials" of JSON
            JSONArray jsonArray = ((JSONArray) jso.get("credentials"));
            return jsonArray;
        }
        return null;
    }

    public static void main(String[] args) throws Exception{
        // Creates org.test.pwgui.JSON_Reader object
        JSON_Reader jr = new JSON_Reader();

    }
}