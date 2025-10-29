package Testing_Package;

import Base_Package.TestBase;
import Pages.P1_Dashbord;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

public class testDashbord extends TestBase {

    private JsonNode testData;

    public testDashbord() {
        try {
            String path = System.getProperty("user.dir") + File.separator + "src"
                    + File.separator + "test" + File.separator + "java"
                    + File.separator + "resources" + File.separator + "TestData" + File.separator + "TestData.json";

            ObjectMapper mapper = new ObjectMapper();
            testData = mapper.readTree(new File(path));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read TestData.json file", e);
        }
    }

    @Test
    public void testLogin2() throws InterruptedException {
        // Read JSON data
        String username = testData.get("login").get("username").asText();
        String password = testData.get("login").get("password").asText();
        String expectedUrl = testData.get("expectedUrl").asText();

        String employeeName = testData.get("newUser").get("employeeName").asText();
        String newUserPassword = testData.get("newUser").get("password").asText();

        logger.info("Logging in to the app.");

        // Step 1: Login
        P0.login(username, password);
        P1_Dashbord P1 = P0.click_login_button();

        // Step 2: Verify login
        Assert.assertEquals(P0.actualUrl(), expectedUrl,
                String.format("Login failed! Expected: %s, got: %s", expectedUrl, P0.actualUrl()));
        logger.pass("Login successful! Navigated to Dashboard.");

        // Step 3: Go to Admin tab
        P1.goToAdminTab();
        int beforeCount = P1.getRecordCount();

        logger.info("Records before adding new user: " + beforeCount);

        // Step 4: Add new user
        String newUsername = "AutoUser_" + System.currentTimeMillis();
        P1.addNewUser(employeeName, newUsername, newUserPassword);
        logger.info("New user added: " + newUsername);

        // Step 5: Verify record count increased
        int afterAddCount = P1.getRecordCount();
        Assert.assertEquals(afterAddCount, beforeCount + 1,
                "Record count did not increase after adding user.");
        logger.pass("Record count increased.");

        // Step 6: Delete user
        P1.searchUser(newUsername);
        P1.deleteUser();
        logger.info("User deleted: " + newUsername);

        // Step 7: Verify count restored
        P1.goToAdminTab();

        int afterDeleteCount = P1.getRecordCount();
        Assert.assertEquals(afterDeleteCount, beforeCount,
                "Record count did not decrease after deleting user.");
        logger.pass("Record count restored.");
    }
}
