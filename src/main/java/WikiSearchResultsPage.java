import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class WikiSearchResultsPage extends BasePage{
    WikiSearchResultsPage(WebDriver initialDriver){
        super(initialDriver);
    }

    String resultPageTitle() {
        return findByCss("#firstHeading").getText();
    }

    Boolean noResultFound(){
        return findByCss(".mw-search-nonefound").isEnabled();
    }
}
