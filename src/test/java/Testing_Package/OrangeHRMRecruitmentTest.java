package Testing_Package;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static io.restassured.RestAssured.given;


public class OrangeHRMRecruitmentTest {

    private String baseUrl = "https://opensource-demo.orangehrmlive.com/web/index.php";
    private String sessionCookie;
    private SoftAssert softAssert;

    @BeforeClass
    public void setup() {
        RestAssured.useRelaxedHTTPSValidation();
        softAssert = new SoftAssert();

        System.out.println("Step 1: Logging in via API");

        Response loginResponse = given()
                .contentType(ContentType.URLENC)
                .formParam("username", "Admin")
                .formParam("password", "admin123")
                .when()
                .post(baseUrl + "/auth/validate")
                .then()
                .extract()
                .response();

        sessionCookie = loginResponse.getCookie("orangehrm");
        int statusCode = loginResponse.statusCode();

        System.out.println("Login status: " + statusCode);
        softAssert.assertTrue(statusCode == 200 || statusCode == 302, "Login failed!");
    }

    @Test(priority = 1)
    public void addCandidate() {
        System.out.println("Step 2: Adding a candidate...");

        String candidatePayload = "{"
                + "\"firstName\": \"John\","
                + "\"lastName\": \"Doe\","
                + "\"email\": \"john.doe@example.com\","
                + "\"contactNumber\": \"9876543210\","
                + "\"vacancyId\": null"
                + "}";

        Response addResponse = given()
                .cookie("orangehrm", sessionCookie)
                .contentType(ContentType.JSON)
                .body(candidatePayload)
                .when()
                .post(baseUrl + "/api/v2/recruitment/candidates")
                .then()
                .extract()
                .response();

        int statusCode = addResponse.statusCode();
        System.out.println("Add candidate response code: " + statusCode);
        System.out.println("Response body: " + addResponse.asString());

        softAssert.assertEquals(statusCode, 200, "Add candidate failed!");
    }

    @Test(priority = 2)
    public void deleteCandidate() {
        System.out.println("Step 3: Deleting a candidate...");

        // Get first candidate ID
        Response listResponse = given()
                .cookie("orangehrm", sessionCookie)
                .when()
                .get(baseUrl + "/api/v2/recruitment/candidates")
                .then()
                .extract()
                .response();

        int statusCodeList = listResponse.statusCode();
        softAssert.assertEquals(statusCodeList, 200, "Failed to retrieve candidates");

        if (statusCodeList == 200 && listResponse.jsonPath().getList("data").size() > 0) {
            int candidateId = listResponse.jsonPath().getInt("data[0].id");
            System.out.println("Candidate ID to delete: " + candidateId);

            Response deleteResponse = given()
                    .cookie("orangehrm", sessionCookie)
                    .contentType(ContentType.JSON)
                    .when()
                    .delete(baseUrl + "/api/v2/recruitment/candidates/" + candidateId)
                    .then()
                    .extract()
                    .response();

            System.out.println("Delete candidate response code: " + deleteResponse.statusCode());
            System.out.println("Response body: " + deleteResponse.asString());

            softAssert.assertEquals(deleteResponse.statusCode(), 200, "Delete candidate failed!");
        } else {
            System.out.println("No candidates available to delete.");
        }

        softAssert.assertAll();
    }
}
