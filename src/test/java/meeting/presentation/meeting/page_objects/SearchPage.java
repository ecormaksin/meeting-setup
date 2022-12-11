package meeting.presentation.meeting.page_objects;

import meeting.presentation.CustomPageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class SearchPage extends CustomPageObject {

    static final String URL_RELATIVE_PATH = "/meeting/search";

    private static final String MEETING_LIST_ROW_SELECTOR_TEMPLATE = "#searchResult > table > tbody > tr:nth-child(%d)";

    @FindBy(id = "keyword.value")
    private WebElement keyword;

    @FindBy(id = "startDate.value")
    private WebElement startDate;

    @FindBy(id = "endDate.value")
    private WebElement endDate;

    private WebElement searchMeetingButton;

    @FindBy(css = "#searchResult > table > tbody")
    private WebElement meetingList;

    @FindBy(css = "#searchResult > p")
    private WebElement noMatchFoundMessage;

    enum ColumnNumber {
        SUBJECT(2),
        SCHEDULES(3);

        int value;

        ColumnNumber(int value) {
            this.value = value;
        }

        public int value() {
            return value;
        }
    }

    public SearchPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected boolean isNotTargetPage(WebDriver driver) {
        String url = driver.getCurrentUrl();
        return !url.matches(".*" + URL_RELATIVE_PATH + "\\??.*");
    }

    @Override
    protected String pageName() {
        return "ミーティングの検索";
    }

    public String keywordValue() {
        return keyword.getAttribute("value");
    }

    public SearchPage inputKeyword(String value) {
        keyword.clear();
        keyword.sendKeys(value);
        return this;
    }

    public String startDateValue() {
        return startDate.getAttribute("value");
    }

    public SearchPage inputStartDate(String value) {
        startDate.clear();
        startDate.sendKeys(value);
        return this;
    }

    public String endDateValue() {
        return endDate.getAttribute("value");
    }

    public SearchPage inputEndDate(String value) {
        endDate.clear();
        endDate.sendKeys(value);
        return this;
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

    public List<WebElement> meetingListItemRows() {
        return meetingList.findElements(By.tagName("tr"));
    }

    WebElement meetingListItemRow(int index) {
        String cssSelector = MEETING_LIST_ROW_SELECTOR_TEMPLATE.formatted(index + 1);
        return meetingList.findElement(By.cssSelector(cssSelector));
    }

    public String subjectValue(int index) {
        WebElement subject = meetingListItemRow(index).findElement(By.cssSelector("td:nth-child(2)"));
        return subject.getText();
    }

    public String noMatchFoundMessage() {
        try {
            return noMatchFoundMessage.getText();
        } catch (NoSuchElementException e) {
            return "";
        }
    }
}
