import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

abstract class BasePage {
    private static WebDriver driver;

    BasePage(WebDriver initialDriver){
        driver = initialDriver;
    }
    WebElement findByCss(String css){
        return driver.findElement(By.cssSelector(css));
    }

    List<WebElement> allByCss(String css){
        return driver.findElements(By.cssSelector(css));
    }
}
