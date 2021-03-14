import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.testng.ScreenShooter;
import extensions.RetryAnalyzer;
import org.openqa.selenium.Keys;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;

@Listeners({ ScreenShooter.class })
public class ITUTest {
    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void findActorinMovie() {

        Configuration.browser = "Chrome";
        Configuration.startMaximized = true;
        Configuration.timeout = 10000;
//        Configuration.headless = true;

        open("http://kutuphane.itu.edu.tr/");
        $(".allsq").setValue("Agile Testing").pressEnter();

        switchTo().window(1);
        $(".closeguest").click();

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
