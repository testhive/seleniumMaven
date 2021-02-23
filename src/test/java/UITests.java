import extensions.RetryAnalyzer;
import extensions.TestBase;
import extensions.TestListener;
import org.testng.annotations.*;
import org.testng.annotations.Test;

@Listeners(TestListener.class)
public class UITests extends TestBase {

    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void searchAndFindResults() {
        WikiHomePage homePage = new WikiHomePage(driver);
        WikiSearchResultsPage resultsPage = new WikiSearchResultsPage(driver);

        homePage.goToPage();
        homePage.searchTerm("Alan Turing");
        assert resultsPage.resultPageTitle().equalsIgnoreCase("alan turing");
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void findNoResults() {
        WikiHomePage homePage = new WikiHomePage(driver);
        WikiSearchResultsPage resultsPage = new WikiSearchResultsPage(driver);

        homePage.goToPage();
        homePage.searchTerm("thishjhafroirsdklfjlksdjfgkjsdflgk");

        assert resultsPage.noResultFound() ;
    }
}