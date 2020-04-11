package org.george.tests;

import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.george.entities.Gender;
import org.george.entities.SuperHero;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;

public class TestRest extends BaseTestRest {


    private static String superheroes = "/superheroes";

    @Test
    public void testGetAllSuperHeroesReturnsList() {
        List<SuperHero> a = when().
                get(superheroes).
                then()
                .contentType(ContentType.JSON).
                        statusCode(HttpStatus.SC_OK).
                        body("id", hasSize(greaterThan(0)))
                .extract()
                .body()
                .jsonPath()
                .getList(".", SuperHero.class);
        Assertions.assertAll(
                () -> Assert.assertEquals("On the run: 1", a.size(), getSuperHeroes().size()),
                () -> Assert.assertEquals("On the run: 2", a.size(), getSuperHeroes().size()),
                () -> Assert.assertEquals("On the run: 3", a.size(), getSuperHeroes().size()),
                () -> Assert.assertEquals("On the run: 4", a.size(), getSuperHeroes().size()),
                () -> Assert.assertEquals("On the run: 5", a.size(), getSuperHeroes().size()),
                () -> Assert.assertEquals("On the run: 6", a.size(), getSuperHeroes().size()),
                () -> Assert.assertEquals("On the run: 7", a.size(), getSuperHeroes().size()),
                () -> Assert.assertEquals("On the run: 8", a.size(), getSuperHeroes().size()),
                () -> Assert.assertEquals("On the run: 9", a.size(), getSuperHeroes().size()),
                () -> Assert.assertEquals("On the run: 10", a.size(), getSuperHeroes().size())
        );

    }

    @Test
    public void testAddSuperHeroVerifyReturnedFields() {

        SuperHero superHero = getDefaultSuperhero();

        SuperHero returnedSuperHero = given().contentType(ContentType.JSON).
                accept(ContentType.ANY).
                body(superHero).
                when().
                post(superheroes).
                then()
                .extract().as(SuperHero.class);
        superHero.setId(returnedSuperHero.getId());

        assertThat(returnedSuperHero).isEqualToComparingFieldByField(superHero);
    }

    @Test
    public void testAddSuperHeroVerifyReturnedStatus() {

        SuperHero superHero = getDefaultSuperhero();

        given().contentType(ContentType.JSON).
                accept(ContentType.ANY).
                body(superHero).
                when().
                post(superheroes).
                then().
                statusCode(HttpStatus.SC_CREATED);

    }

    @Test
    public void testAddSuperHeroWithAdditionalProperty() {
        SuperHero superHero = getDefaultSuperhero();
        superHero.setAdditionalProperty("TEST", "newProperty");
        given().contentType(ContentType.JSON).
                accept(ContentType.ANY).
                body(superHero).
                when().
                post(superheroes).
                then().
                statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    public void testAddSuperHeroWitBirthdayInFuture() {
        SuperHero superHero = getDefaultSuperhero();
        superHero.setBirthDate("2030-12-12");
        superHero.setPhone(null);
        SuperHero returnedSuperHero = given().contentType(ContentType.JSON).
                accept(ContentType.ANY).
                body(superHero).
                when().
                post(superheroes).
                then().
                extract().as(SuperHero.class);
        superHero.setId(returnedSuperHero.getId());
        assertThat(returnedSuperHero).isEqualToComparingFieldByField(superHero);
    }

    static int id = -1;

    static int getExistingId() {
        if (id == -1) {
            id = getSuperHeroes().stream().findFirst().get().getId();
        }
        return id;
    }

    @RepeatedTest(10)
    public void testGetSuperheroById() {

        given().accept(ContentType.ANY).
                when().
                get(superheroes + "/" + getExistingId()).
                then().
                statusCode(HttpStatus.SC_OK).
                body("id", is(getExistingId()));
    }

    @Test
    public void testGetSuperheroByNonExistingId() {
        given().accept(ContentType.ANY).
                when().
                get(superheroes + "/9999999").
                then().
                statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @RepeatedTest(10)
    public void testEditExistingHero() {
        SuperHero sh = getDefaultSuperhero();
        SuperHero returnedSuperHero = given().contentType(ContentType.JSON).
                accept(ContentType.ANY).
                body(sh).
                when().
                post(superheroes).
                then().
                extract().as(SuperHero.class);
        returnedSuperHero.setCity("Vienna");
        given().contentType(ContentType.JSON).
                accept(ContentType.ANY).
                body(returnedSuperHero).
                when().
                put(superheroes + "/" + returnedSuperHero.getId());
        SuperHero returnedSuperHeroPut = getUserFromList(returnedSuperHero.getId());
        assertThat(returnedSuperHeroPut).isEqualToComparingFieldByField(returnedSuperHero);
    }

    @RepeatedTest(10)
    public void testDeleteExistingHero() {
        SuperHero sh = getDefaultSuperhero();
        SuperHero returnedSuperHero = given().contentType(ContentType.JSON).
                accept(ContentType.ANY).
                body(sh).
                when().
                post(superheroes).
                then().
                extract().as(SuperHero.class);
        System.out.println(returnedSuperHero.getId());
        given().contentType(ContentType.JSON).
                accept(ContentType.ANY).
                body(returnedSuperHero).
                when().
                delete(superheroes + "/" + returnedSuperHero.getId()).
                then().
                statusCode(HttpStatus.SC_OK);


        assertThat(getSuperHeroes().stream().filter(x -> x.getId().equals(returnedSuperHero.getId())).count()).isEqualTo(0);
    }

    @Test
    public void testDeleteExistingHeroByIncorrectId() {
        given().contentType(ContentType.JSON).
                accept(ContentType.ANY).
                when().
                delete(superheroes + "/9999999999").
                then().
                statusCode(HttpStatus.SC_NOT_FOUND);
    }


    @Test
    public void testAdd20SuperHeroes() {
        List<SuperHero> list = new ArrayList<>();
        long unique = System.currentTimeMillis();
        for (int i = 0; i < 20; i++) {
            SuperHero sh = getDefaultSuperhero();
            sh.setMainSkill(unique + "_" + i);
            list.add(sh);
        }

        list.forEach(hero -> given().contentType(ContentType.JSON).
                accept(ContentType.ANY).
                body(hero).
                when().
                post(superheroes));
        List<SuperHero> actualList = getSuperHeroes();
        list.forEach(hero -> Assert.assertEquals("There is no hero with mainskill: " + hero.getMainSkill(), 1, actualList.stream().filter(x -> x.getMainSkill().equals(hero.getMainSkill())).count()));
    }


    private static List<SuperHero> getSuperHeroes() {
        return when().
                get(superheroes).
                then()
                .contentType(ContentType.JSON).
                        statusCode(HttpStatus.SC_OK).
                        body("id", hasSize(greaterThan(0)))
                .extract()
                .body()
                .jsonPath()
                .getList(".", SuperHero.class);

    }


    private SuperHero getDefaultSuperhero() {
        SuperHero superHero = new SuperHero();
        superHero.setBirthDate("2019-02-21");
        superHero.setCity("Gotham");
        superHero.setFullName("Test Auto");
        superHero.setGender(Gender.M.name());
        superHero.setMainSkill("Magic");
        superHero.setPhone("+711111111111");
        return superHero;
    }

    private SuperHero getUserFromList(int id) {
        return getSuperHeroes().stream().filter(x -> x.getId().equals(id)).findFirst().get();
    }
}
