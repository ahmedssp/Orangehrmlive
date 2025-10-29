package Testing_Package;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.filter.cookie.CookieFilter;

public class API_TEST {
    static String baseUrl = "https://opensource-demo.orangehrmlive.com";
    static CookieFilter cookieFilter = new CookieFilter();

    public static void main(String[] args) {
        // Step 1: Login
        Response login = RestAssured.given()
                .filter(cookieFilter)
                .formParam("username", "Admin")
                .formParam("password", "admin123")
                .post(baseUrl + "/web/index.php/auth/validate");

        System.out.println("Login status: " + login.getStatusCode());

        // Step 2: Add Candidate
        addCandidate();

        // Step 3: Delete Candidate
        deleteCandidate(1); // Replace with actual candidate ID
    }

    static void addCandidate() {
        String candidatePayload = """
        {
            "firstName": "John",
            "lastName": "Doe",
            "email": "john.doe@test.com",
            "vacancyId": 2
        }
        """;

        Response response = RestAssured.given()
                .filter(cookieFilter)
                .header("Content-Type", "application/json")
                .body(candidatePayload)
                .post(baseUrl + "/web/index.php/api/v2/recruitment/candidates");

        System.out.println("Add Candidate Response: " + response.asPrettyString());
    }

    static void deleteCandidate(int candidateId) {
        Response response = RestAssured.given()
                .filter(cookieFilter)
                .delete(baseUrl + "/web/index.php/api/v2/recruitment/candidates/" + candidateId);

        System.out.println("Delete Candidate Response: " + response.getStatusCode());
    }
}
