import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class GoogleSearchResultsPage extends BasePage{
    GoogleSearchResultsPage(WebDriver initialDriver){
        super(initialDriver);
    }

    List<WebElement> resultsOnPage() {
        return allByCss("div.g");
    }

    Boolean noResultsOnPage(){
        return findByCss(".mnr-c").isEnabled();
    }
}
