package org.george.tests;

import org.george.utils.RestUtils;
import org.junit.jupiter.api.BeforeAll;

public abstract class BaseTestRest {
    private static String restAPIUrl;

    @BeforeAll
    public static void setUpClass() {
        restAPIUrl = System.getProperty("host", "https://superhero.qa-test.csssr.com/");
        RestUtils.setBaseURI(restAPIUrl);

    }
}
