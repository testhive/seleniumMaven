import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class GoogleHomepage extends BasePage{
    private static WebDriver driver;
    String searchField = "#lst-ib";
    String url = "http://www.google.com";

    GoogleHomepage(WebDriver initialDriver){
        super(initialDriver);
        driver = initialDriver;
    }
    void goToPage(){
        driver.get(url);
    }

    public void searchTerm(String term){
        WebElement elem = findByCss(searchField);
        elem.sendKeys(term);
        elem.sendKeys(Keys.RETURN);
    }
}
