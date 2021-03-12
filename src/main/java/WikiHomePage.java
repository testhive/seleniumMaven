import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.open;

public class WikiHomePage extends BasePage{
//    private static WebDriver driver;
    String searchField = "#searchInput";
    String searchButton = "#searchButton";
    String url = "https://en.wikipedia.org/wiki/Main_Page";

    void goToPage(){
        open(url);
    }

    public void searchTerm(String term){
        SelenideElement elem = findByCss(searchField);
        elem.setValue(term);
        findByCss(searchButton).click();
    }
}
