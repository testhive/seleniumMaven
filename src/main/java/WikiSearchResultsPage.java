import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class WikiSearchResultsPage extends BasePage{
    void resultPageTitleContains(String term) {
        $(By.cssSelector("#firstHeading")).shouldHave(text(term));
    }

    Boolean noResultFound(){
        return $(By.cssSelector(".mw-search-nonefound")).isDisplayed();
    }
}
