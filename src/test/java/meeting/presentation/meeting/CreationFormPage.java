package meeting.presentation.meeting;

import meeting.presentation.CustomPageObject;
import meeting.presentation.meeting.input.schedule.construct.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
public class CreationFormPage extends CustomPageObject {

    private static final String URL_RELATIVE_PATH = "/meeting/create";

    private static final String SCHEDULE_CONSTRUCT_PREFIX = "scheduleInputs.list%d.scheduleConstruct.";

    @FindBy(id = "subject.value")
    private WebElement subject;

    @FindBy(id = "memo.value")
    private WebElement memo;

    @FindBy(id = "createMeetingButton")
    private WebElement createButton;

    @FindBy(css = "#schedules > tbody > tr")
    private List<WebElement> scheduleRows;

    public CreationFormPage(final WebDriver driver) {
        super(driver);

        final String url = driver.getCurrentUrl();
        if (!url.endsWith(URL_RELATIVE_PATH)) {
            throw new IllegalStateException("ミーティングの作成ページではありません。: "  + url);
        }
    }

    public static CreationFormPage to(WebDriver driver) {
        driver.get(URL_BASE + URL_RELATIVE_PATH);
        new WebDriverWait(driver, Duration.ofSeconds(20)).until(ExpectedConditions.urlContains(URL_RELATIVE_PATH));
        return PageFactory.initElements(driver, CreationFormPage.class);
    }

    String subjectValue() {
        return subject.getAttribute("value");
    }

    String memoValue() {
        return memo.getAttribute("value");
    }

    List<WebElement> scheduleRows() {
        return scheduleRows;
    }

    public CreationFormPage inputSubject(final String subject) {
        this.subject.sendKeys(subject);
        return this;
    }

    public CreationFormPage inputMemo(final String memo) {
        this.memo.sendKeys(memo);
        return this;
    }

    public CreationFormPage inputSchedules(final List<ScheduleConstructInput> inputs) {
        for (int index = 0; index < inputs.size(); index++) {
            final ScheduleConstructInput schedule = inputs.get(index);
            inputScheduleAt(index, schedule);
        }
        return this;
    }

    public CreationFormPage inputScheduleAt(final int index, final ScheduleConstructInput input) {
        inputMeetingDateAt(index, input.meetingDate());
        inputStartHourAt(index, input.startHour());
        inputStartMinuteAt(index, input.startMinute());
        inputDurationAt(index, input.duration());
        return this;
    }

    private String byId(final int index, final String itemName) {
        return String.format(SCHEDULE_CONSTRUCT_PREFIX, index) + itemName;
    }

    private WebElement scheduleConstructElement(final int index, final String itemName) {
        final String byId = byId(index, itemName);
        return driver.findElement(By.id(byId));
    }

    public CreationFormPage inputMeetingDateAt(final int index, final MeetingDateInput meetingDate) {
        final WebElement webElement = scheduleConstructElement(index, "meetingDate.value");
        webElement.sendKeys(meetingDate.value());
        return this;
    }

    public CreationFormPage inputStartHourAt(final int index, final StartHourInput startHour) {
        final WebElement webElement = scheduleConstructElement(index, "startHour.value");
        webElement.sendKeys(startHour.value());
        return this;
    }

    public CreationFormPage inputStartMinuteAt(final int index, final StartMinuteInput startMinute) {
        final WebElement webElement = scheduleConstructElement(index, "startMinute.value");
        Select selectObject = new Select(webElement);
        selectObject.selectByValue(startMinute.value());
        return this;
    }

    public CreationFormPage inputDurationAt(final int index, final DurationInput duration) {
        final WebElement webElement = scheduleConstructElement(index, "duration.value");
        Select selectObject = new Select(webElement);
        selectObject.selectByValue(duration.value());
        return this;
    }

    public <T> T createMeeting(Class<T> resultPage) {
        this.createButton.click();
        return PageFactory.initElements(driver, resultPage);
    }
}
