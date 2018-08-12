import org.testng.annotations.*;
import org.testng.annotations.Test;

@Listeners(TestListener.class)
public class ChromeTest extends TestBase {

    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void searchAndFindResults() {
        GoogleHomepage homePage = new GoogleHomepage(driver);
        GoogleSearchResultsPage resultsPage = new GoogleSearchResultsPage(driver);

        homePage.goToPage();
        homePage.searchTerm("Alper Mermer");
        assert resultsPage.resultsOnPage().size() > 0 ;
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void findNoResults() {
        GoogleHomepage homePage = new GoogleHomepage(driver);
        GoogleSearchResultsPage resultsPage = new GoogleSearchResultsPage(driver);

        homePage.goToPage();
        homePage.searchTerm("thishjhafroirsdklfjlksdjfgkjsdflgk");

        assert resultsPage.noResultsOnPage() ;
    }
}