import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

abstract class BasePage {
    private static Integer defaultMaxWAitTime = 20;
    private static WebDriver driver;

    BasePage(WebDriver initialDriver){
        driver = initialDriver;
    }
    WebElement findByCss(String css){
        WebDriverWait wait = new WebDriverWait(driver, defaultMaxWAitTime);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(css)));
    }

    List<WebElement> allByCss(String css){
        WebDriverWait wait = new WebDriverWait(driver, defaultMaxWAitTime);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(css)));
        return driver.findElements(By.cssSelector(css));
    }
}
