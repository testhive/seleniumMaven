import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class BasePage {
    BasePage(){
        Configuration.browser = "Firefox";
        Configuration.startMaximized = true;
    }

    SelenideElement findByCss(String css){ return $(By.cssSelector(css)); }

    ElementsCollection allByCss(String css){
        return $$(By.cssSelector(css));
    }
}
