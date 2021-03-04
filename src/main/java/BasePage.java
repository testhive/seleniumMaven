import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class BasePage {
    SelenideElement findByCss(String css){ return $(By.cssSelector(css)); }

    ElementsCollection allByCss(String css){
        return $$(By.cssSelector(css));
    }
}
