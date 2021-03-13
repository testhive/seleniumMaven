import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.testng.ScreenShooter;
import extensions.RetryAnalyzer;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selectors.byTagName;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

@Listeners({ ScreenShooter.class })
public class IMDBTest {
    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void findActorinMovie() {

//        TODO
        Configuration.browser = "Chrome";
        Configuration.startMaximized = true;
        Configuration.timeout = 45000;


//        go to imdb.com
        open("http://imdb.com");
//        search for movie name in search box  #suggestion-search  #suggestion-search-button  The Matrix
        $("#suggestion-search").setValue("The Matrix");
        $("#suggestion-search-button").click();
//        go to movie details     .findResult  .primary_photo
        $(".findResult").$$(".primary_photo").first().click();
//        get actor list    .cast_list
        SelenideElement cast = $(".cast_list");
//        actor list should contain actorName    Carrie-Anne Moss
//        go to actor details page
        cast.$(byText("Carrie-Anne Moss")).click();
//        check movie list for actor  .filmo-category-section
//        movie list should contain movieName   The Matrix  actress-tt0133093
        $("#actress-tt0133093").click();
    }
}