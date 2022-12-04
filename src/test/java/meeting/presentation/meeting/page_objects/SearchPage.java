package meeting.presentation.meeting.page_objects;

import meeting.presentation.CustomPageObject;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SearchPage extends CustomPageObject {

    static final String URL_RELATIVE_PATH = "/meeting/search";

    @FindBy(id = "keyword.value")
    private WebElement keyword;

    @FindBy(id = "startDate.value")
    private WebElement startDate;

    @FindBy(id = "endDate.value")
    private WebElement endDate;

    private WebElement searchMeetingButton;

    @FindBy(css = "#searchResult > p")
    private WebElement noMatchFoundMessage;

    public SearchPage(WebDriver driver) {
        super(driver);
    }

    protected boolean isNotTargetPage(WebDriver driver) {
        String url = driver.getCurrentUrl();
        return !url.endsWith(URL_RELATIVE_PATH);
    }

    protected String pageName() {
        return "ミーティングの検索";
    }

    public String keywordValue() {
        return keyword.getAttribute("value");
    }

    public String startDateValue() {
        return startDate.getAttribute("value");
    }

    public String endDateValue() {
        return endDate.getAttribute("value");
    }

    public SearchPage search() {
        new WebDriverWait(driver, Duration.ofMinutes(3)).until(innerDriver -> {
            new Actions(innerDriver).moveToElement(searchMeetingButton).perform();
            enableButtonByIdForcibly(innerDriver, "searchMeetingButton");
            return searchMeetingButton.isDisplayed()
                    && searchMeetingButton.isEnabled();
        });
        searchMeetingButton.click();
        return PageFactory.initElements(driver, SearchPage.class);
    }

    public String noMatchFoundMessage() {
        try {
            return noMatchFoundMessage.getText();
        } catch (NoSuchElementException e) {
            return "";
        }
    }
}
