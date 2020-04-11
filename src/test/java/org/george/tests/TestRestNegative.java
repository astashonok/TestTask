package org.george.tests;


import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.george.CsvReader;
import org.george.entities.SuperHero;
import org.george.utils.RestUtils;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

import static io.restassured.RestAssured.given;

public class TestRestNegative extends BaseTestRest {

    private static String superheroes = "/superheroes";


    @ParameterizedTest
    @MethodSource("data")
    void testNegativeCreate(SuperHero hero) {
        Response response = given().contentType(ContentType.JSON).
                accept(ContentType.ANY).
                body(hero).
                when().
                post(superheroes).
                then().
                extract().response();
        Assert.assertEquals(hero.toString() + "response: " + response.getBody().prettyPrint(), HttpStatus.SC_BAD_REQUEST, response.getStatusCode());
        Assert.assertEquals(hero.toString() + "response: " + response.getBody().prettyPrint(), HttpStatus.SC_BAD_REQUEST, response.getStatusCode());
              //  statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    public static List<SuperHero> data() {
        return CsvReader.getData("negativedata.csv");
    }
}
