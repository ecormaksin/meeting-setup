package meeting.presentation;

import meeting.presentation.meeting.page_objects.SearchPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TopPage extends CustomPageObject {

    static final String URL_RELATIVE_PATH = "/";

    @FindBy(css = "main > article > ul > li:nth-child(2) > a")
    private WebElement meetingSearchLink;

    public TopPage(WebDriver driver) {
        super(driver);
    }

    public static TopPage to(WebDriver driver) {
        driver.get(URL_BASE + URL_RELATIVE_PATH);
        waitUntilPageOperable(driver);
        return PageFactory.initElements(driver, TopPage.class);
    }

    @Override
    protected boolean isNotTargetPage(WebDriver driver) {
        String url = driver.getCurrentUrl();
        return !url.endsWith(URL_RELATIVE_PATH);
    }

    @Override
    protected String pageName() {
        return "トップ";
    }

    private static void waitUntilPageOperable(WebDriver driver) {

        new WebDriverWait(driver, Duration.ofMinutes(3)).until(innerDriver -> {
            String url = innerDriver.getCurrentUrl();
            boolean isUrlMatched = url.matches(String.format(".*%s$", URL_RELATIVE_PATH));

            return isUrlMatched
                    && isOrganizerNumberSet(innerDriver)
                    && isParticipantNumberSet(innerDriver);
        });
    }

    public SearchPage moveToSearchPage() {
        meetingSearchLink.click();
        return PageFactory.initElements(driver, SearchPage.class);
    }
}
