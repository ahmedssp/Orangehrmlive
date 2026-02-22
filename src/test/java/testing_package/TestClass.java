package testing_package;

import base.BaseTest;
import Pages.P1_Dashbord;
import com.fasterxml.jackson.databind.JsonNode;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.openqa.selenium.Cookie;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utils.ReporterUtil;
import utils.TestDataUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Listeners(testing_package.TestListener.class)
public class TestClass extends BaseTest {

    private JsonNode testData;
    private P1_Dashbord P1;
    private Map<String, String> sessionCookies;
    @BeforeClass
    public void loadTestData() {
        testData = TestDataUtil.readJson("src/test/resources/TestData.json");
    }


    @Test
    public void login() throws InterruptedException {

        String username = testData.get("login").get("username").asText();
        String password = testData.get("login").get("password").asText();
        String expectedUrl = testData.get("expectedUrl").asText();

        ReporterUtil.reporter("info", "Logging in to the app.");

        P0.login(username, password);
        P1 = P0.click_login_button();

        Assert.assertEquals(P0.actualUrl(), expectedUrl,
                String.format("Login failed! Expected: %s, got: %s",
                        expectedUrl, P0.actualUrl()));

        ReporterUtil.reporter("pass", "Login successful!");
        // Extract cookies for API use
        sessionCookies = new HashMap<>();
        Set<Cookie> seleniumCookies = driver.manage().getCookies();
        for (Cookie cookie : seleniumCookies) {
            sessionCookies.put(cookie.getName(), cookie.getValue());
        }
    }


    @Test(dependsOnMethods = "login")
    public void addAndDeleteValidationUI() throws InterruptedException {

        String employeeName = testData.get("newUser").get("employeeName").asText();
        String newUserPassword = testData.get("newUser").get("password").asText();

        // Step 1: Go to Admin tab
        P1.goToAdminTab();
        int beforeCount = P1.getRecordCount();
        ReporterUtil.reporter("info", "Records before adding: " + beforeCount);

        // Step 2: Add user
        String newUsername = "AutoUser_" + System.currentTimeMillis();
        P1.addNewUser(employeeName, newUsername, newUserPassword);
        ReporterUtil.reporter("info", "New user added: " + newUsername);

        // Step 3: Validate count increased
        int afterAddCount = P1.getRecordCount();
        Assert.assertEquals(afterAddCount, beforeCount + 1,
                "Record count did not increase after adding user.");
        ReporterUtil.reporter("pass", "Record count increased.");

        // Step 4: Search & Delete
        P1.searchUser(newUsername);
        P1.deleteUser();
        ReporterUtil.reporter("info", "User deleted: " + newUsername);

        // Step 5: Validate count restored
        P1.goToAdminTab();
        int afterDeleteCount = P1.getRecordCount();
        Assert.assertEquals(afterDeleteCount, beforeCount,
                "Record count did not decrease after deleting user.");
        ReporterUtil.reporter("pass", "Record count restored.");
    }
    @Test(priority = 3, dependsOnMethods = "login")
    public void testAddUserViaAPI() {

        RestAssured.baseURI = "https://opensource-demo.orangehrmlive.com";

        String newUsername = "AutoUser_" + System.currentTimeMillis();

        String payload = """
    {
      "username": "%s",
      "password": "StrongP@ssw0rd",
      "status": true,
      "userRoleId": 1,
      "empNumber": 7
    }
    """.formatted(newUsername);

        Response response = RestAssured
                .given()
                .cookies(sessionCookies)
                .header("Content-Type", "application/json")
                .body(payload)
                .post("/web/index.php/api/v2/admin/users")
                .then()
                .extract().response();

        System.out.println("Status Code: " + response.getStatusCode());
        System.out.println("Body: " + response.getBody().asString());

        Assert.assertEquals(response.getStatusCode(), 200);
    }
}