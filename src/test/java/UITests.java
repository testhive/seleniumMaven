import com.codeborne.selenide.testng.ScreenShooter;
import extensions.RetryAnalyzer;
import org.testng.annotations.*;
import org.testng.annotations.Test;

@Listeners({ ScreenShooter.class })
public class UITests{
    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void searchAndFindResults() {
        WikiHomePage homePage = new WikiHomePage();
        WikiSearchResultsPage resultsPage = new WikiSearchResultsPage();

        homePage.goToPage();
        homePage.searchTerm("Alan Turing");
        resultsPage.resultPageTitleContains("alan turing");
        System.out.println(homePage.allByCss(".thumbinner").size());
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void findNoResults() {
        WikiHomePage homePage = new WikiHomePage();
        WikiSearchResultsPage resultsPage = new WikiSearchResultsPage();

        homePage.goToPage();
        homePage.searchTerm("thishjhafroirsdklfjlksdjfgkjsdflgk");

        assert resultsPage.noResultFound() ;
    }
}