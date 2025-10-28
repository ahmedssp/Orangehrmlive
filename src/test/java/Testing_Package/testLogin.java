package Testing_Package;
import Base_Package.TestBase;
import Pages.P1_Dashbord;
import org.testng.Assert;
import org.testng.annotations.Test;


public class testLogin extends TestBase {

     @Test
    public void testLogin() throws InterruptedException {
         logger.info("Login to the app .");
        // Step 1: Perform login
        P0.login("Admin", "admin123");

        P1_Dashbord P1 = P0.click_login_button();

        // Step 3: Assert that the URL is exactly as expected
        String expectedUrl = "https://opensource-demo.orangehrmlive.com/web/index.php/dashboard/index";
        Assert.assertEquals(expectedUrl, P0.actualUrl(),
                "Login failed! Expected to navigate to Dashboard, but got: " + P0.actualUrl());

         logger.pass("Login successful! Navigated to Dashboard successfully.");
         // go to admin tap
         P1.goToAdminTab();
//          Step 3: Capture number of records before adding a user
        int beforeCount = P1.getRecordCount();
         logger.info("Records before adding a new user: " + beforeCount);
        // add new user
         String newUsername = "AutoUser_" + System.currentTimeMillis();

         P1.addNewUser("james butler",newUsername,"xx123_3f@!wec");

         logger.info(" New user added with username: " + newUsername);
         // Step 10: Verify that the number of records increased by 1
        int afterAddCount = P1.getRecordCount();
         logger.info(" Records after adding new user: " + afterAddCount);
        Assert.assertEquals(afterAddCount, beforeCount + 1,
                " Record count did not increase after adding a new user.");
         logger.pass(" Record count increased by 1 after adding a user.");

        // Step 11: Search with the username for the new user
        P1.searchUser(newUsername);
         logger.info("Searched for user:" + newUsername);

        // Step 12: Delete the new user
        P1.deleteUser();
         logger.info("🗑New user deleted successfully.");

         P1.goToAdminTab();

        // Step 13: Verify that the number of records decreased by 1
        int afterDeleteCount = P1.getRecordCount();
        System.out.println("Records after deleting user: " + afterDeleteCount);
        Assert.assertEquals(afterDeleteCount, beforeCount,
                "Record count did not decrease after deleting the user.");
        logger.pass("Record count decreased by 1 after deleting the user.");
    }

 }