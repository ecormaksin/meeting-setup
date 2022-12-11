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
import java.util.ArrayList;
import java.util.List;

public class SearchPage extends CustomPageObject {

    static final String URL_RELATIVE_PATH = "/meeting/search";

    @FindBy(id = "keyword.value")
    private WebElement keyword;

    @FindBy(id = "startDate.value")
    private WebElement startDate;

    @FindBy(id = "endDate.value")
    private WebElement endDate;

    private WebElement searchMeetingButton;

    @FindBy(css = "#searchResult > table > tbody > tr")
    private List<WebElement> meetingListItemRows;
    private static final String MEETING_LIST_ITEM_CELL_SELECTOR_TEMPLATE = "td:nth-child(%d)";

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

    public int numberOfMeetingListItems() {
        return meetingListItemRows.size();
    }

    private WebElement meetingListItemCell(int rowNumber, int columnNumber) {
        String cssSelector = MEETING_LIST_ITEM_CELL_SELECTOR_TEMPLATE.formatted(columnNumber);
        return meetingListItemRows.get(rowNumber - 1).findElement(By.cssSelector(cssSelector));
    }

    public String subjectValue(int rowNumber) {
        WebElement subject = meetingListItemCell(rowNumber, ColumnNumber.SUBJECT.value);
        return subject.getText();
    }

    public List<String> schedulesValue(int rowNumber) {
        WebElement schedulesCell = meetingListItemCell(rowNumber, ColumnNumber.SCHEDULES.value);
        List<WebElement> schedules = schedulesCell.findElements(By.cssSelector("div > span"));

        List<String> schedulesValue = new ArrayList<>();
        for (WebElement schedule : schedules) {
            schedulesValue.add(schedule.getText());
        }
        return schedulesValue;
    }

    public String noMatchFoundMessage() {
        try {
            return noMatchFoundMessage.getText();
        } catch (NoSuchElementException e) {
            return "";
        }
    }
}
