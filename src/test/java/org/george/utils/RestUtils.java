package org.george.utils;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;

public class RestUtils {
    public static String path;


    public static void setBaseURI (String baseURI){
        RestAssured.baseURI = baseURI;
    }

    public static void setBasePath(String basePathTerm){
        RestAssured.basePath = basePathTerm;
    }

    public static void resetBaseURI (){
        RestAssured.baseURI = null;
    }

    public static void resetBasePath(){
        RestAssured.basePath = null;
    }

    public static void setContentType (ContentType Type){
        given().contentType(Type);
    }


    public static void  createSearchQueryPath(String searchTerm, String jsonPathTerm, String param, String paramValue) {
        path = searchTerm + "/" + jsonPathTerm + "?" + param + "=" + paramValue;
    }


    public static Response getResponse() {
        return get(path);
    }


    public static JsonPath getJsonPath (Response res) {
        String json = res.asString();
        return new JsonPath(json);
    }
}