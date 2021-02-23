import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class WikiHomePage extends BasePage{
    private static WebDriver driver;
    String searchField = "#searchInput";
    String searchButton = "#searchButton";
    String url = "https://en.wikipedia.org/wiki/Main_Page";

    WikiHomePage(WebDriver initialDriver){
        super(initialDriver);
        driver = initialDriver;
    }
    void goToPage(){
        driver.get(url);
    }

    public void searchTerm(String term){
        WebElement elem = findByCss(searchField);
        elem.sendKeys(term);
        findByCss(searchButton).click();
    }
}
