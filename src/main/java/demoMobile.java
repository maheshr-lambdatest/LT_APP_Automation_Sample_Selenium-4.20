import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class demoMobile {

    public String username = System.getenv("LT_USERNAME");
    public String accesskey = System.getenv("LT_ACCESS_KEY");
    public RemoteWebDriver driver;
    public String gridURL = "mobile-hub.lambdatest.com";
    String status;
    String hub;
    SessionId sessionId;

    @org.testng.annotations.Parameters(value = {"browser", "platformVersion", "platform", "deviceName"})
    @BeforeTest
    public void setUp(String browser, String platformVersion, String platform, String deviceName) throws Exception {
        try {

            DesiredCapabilities capabilities = new DesiredCapabilities();
            HashMap<String, Object> ltOptions = new HashMap<String, Object>();
            ltOptions.put("w3c", true);
            ltOptions.put("browser", browser);
            ltOptions.put("platform", platform);
            ltOptions.put("deviceName", deviceName);
            ltOptions.put("platformVersion", platformVersion);
            //comment this line to run web automation tests or enter app ID for Android/IOS as per requirement and hit the command from mobile.xml file
            ltOptions.put("app", "lt://APP10160361821714396231827522");
            ltOptions.put("devicelog", true);
            ltOptions.put("visual", true);
            ltOptions.put("network", true);
            ltOptions.put("video", true);
            ltOptions.put("build", "Build_name");
            ltOptions.put("name", "Test_name");
            ltOptions.put("idleTimeout", 120);
            ltOptions.put("queueTimeout", 600);
            ltOptions.put("isRealMobile", true);
            capabilities.setCapability("lt:options", ltOptions);

            hub = "https://" + username + ":" + accesskey + "@" + gridURL + "/wd/hub";
            driver = new RemoteWebDriver(new URL(hub), capabilities);

            System.out.println("Driver created.");
            sessionId = driver  .getSessionId();
            System.out.println("session ID: " + sessionId);

        } catch (
                MalformedURLException e) {
            System.out.println("Invalid grid URL");
        } catch (Exception f) {
            System.out.println(f);

        }
    }

    @Test
    public void DesktopScript() {
        try {
            System.out.println("Test is running...");

            //Script goes here....

            Thread.sleep(15000);

            status="passed";
        } catch (Exception e) {

            System.out.println(e);
            status = "failed";
        }
    }


    @AfterTest
    public void tearDown() throws Exception {
        if (driver != null) {
            ((JavascriptExecutor) driver).executeScript("lambda-status=" + status);
            driver.quit();
        }
    }
}

