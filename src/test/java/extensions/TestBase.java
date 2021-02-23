package extensions;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import java.time.Duration;

public class TestBase {
    public static WebDriver driver;

    @BeforeSuite
    public static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeClass
    public void setupTest() {
        String browser = System.getenv("BROWSER");

        if(browser == null)
        {
            driver = new ChromeDriver();
        }
        else if(browser.toLowerCase().equals("firefox")){
            driver = new FirefoxDriver();
        }
        else{
            driver = new ChromeDriver();
        }
        int defaultMaxWAitTime = 10;
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(defaultMaxWAitTime));
        driver.manage().window().maximize();
    }

    @AfterClass
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
