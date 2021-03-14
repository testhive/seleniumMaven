import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.testng.ScreenShooter;
import extensions.RetryAnalyzer;
import org.openqa.selenium.Keys;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selectors.byTagName;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;


@Listeners({ ScreenShooter.class })
public class GoogleMapsTest {
   @Test(retryAnalyzer = RetryAnalyzer.class)
    public void findActorinMovie() {

        Configuration.browser = "Chrome";
        Configuration.startMaximized = true;
//        Configuration.headless = true;

        open("https://www.google.com/maps");
        switchTo().frame($(".widget-consent-frame"));
        $("#introAgreeButton").click();

        $("#searchboxinput").setValue("Istanbul");
        $("#searchbox-searchbutton").click();
        $(".iRxY3GoUYUY__button").click();
        $("#sb_ifc51").find("input.tactile-searchbox-input").setValue("Ankara").sendKeys(Keys.ENTER);

       try {
           Thread.sleep(4000);
       } catch (InterruptedException e) {
           e.printStackTrace();
       }
   }
}