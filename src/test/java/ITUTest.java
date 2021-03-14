import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.testng.ScreenShooter;
import extensions.RetryAnalyzer;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.Set;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

@Listeners({ ScreenShooter.class })
public class ITUTest {
    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void searchBook() {

        Configuration.browser = "Chrome";
        Configuration.startMaximized = true;
        Configuration.timeout = 10000;
//        Configuration.headless = true;

        open("http://kutuphane.itu.edu.tr/");
        $(".allsq").setValue("Agile Testing").pressEnter();

        int handles = getWebDriver().getWindowHandles().size();
        switchTo().window(handles-1);

        $(".closeguest").click();

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
