package testCases;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;



public class TC_VideoGameAPI {

    //    Video Game API = "http://localhost:8080/swagger-ui/index.html#!/Video_Games/listVideoGames"
//    GET
    @Test(priority = 1)
    public void test_Getallvideogames() {
        given().

        when().
                get("http://localhost:8080/app/videogames").

        then()
                .statusCode(200);
    }

    //    POST
    @Test(priority = 2)
    public void test_AddNewVideoGame() {
        HashMap data = new HashMap();
        data.put("id", 1010);
        data.put("name", "Batman");
        data.put("releaseDate", "2023-07-05T17:40:38.113Z");
        data.put("reviewScore", 50);
        data.put("category", "Adventure");
        data.put("rating", "Universal");

        Response res =

                given().
                        contentType("application/json").
                        body(data).
                when().
                        post("http://localhost:8080/app/videogames").

                then().
                        statusCode(200).
                        log().body().
                        extract().response();

        String jsonString = res.asString();
        Assert.assertEquals(jsonString.contains("Record Added Successfully"), true);
    }

    //    AFTER POST / GET
    @Test(priority = 3)
    public void test_GetVideoGamesById() {

        given().

        when().
                get("http://localhost:8080/app/videogames/1010").

        then().
                statusCode(200).
                log().body().
                body("videoGame.id", equalTo("1010")).
                body("videoGame.name", equalTo("Batman"));
    }

    //    PUT
    @Test(priority = 4)
    public void test_UpdateVideoGame() {

        HashMap data = new HashMap();
        data.put("id", 1010);
        data.put("name", "Pacman");
        data.put("releaseDate", "2023-07-05T17:40:38.113Z");
        data.put("reviewScore", 500);
        data.put("category", "Adventure");
        data.put("rating", "Universal");


        given().
                contentType("application/json").
                body(data).

        when().
                put("http://localhost:8080/app/videogames/1010").

        then().
                statusCode(200).
                log().body().
                body("videoGame.id", equalTo("1010")).
                body("videoGame.name", equalTo("Pacman")).
                body("videoGame.reviewScore", equalTo("500"));

    }

    //    DELETE
    @Test(priority = 5)
    public void test_DeleteVideoGame() {

        Response res =

                given().

                when().
                        delete("http://localhost:8080/app/videogames/1010").

                then().
                        statusCode(200).
                        log().body().
                        extract().response();

        String jsonString = res.asString();
        Assert.assertEquals(jsonString.contains("Record Deleted Successfully"), true);

    }

}
