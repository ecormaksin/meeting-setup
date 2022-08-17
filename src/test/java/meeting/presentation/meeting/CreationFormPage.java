package meeting.presentation.meeting;

import meeting.presentation.CustomPageObject;
import meeting.presentation.meeting.input.schedule.construct.*;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
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

    @FindBy(id = "addSchedule")
    private WebElement addScheduleButton;

    @FindBy(id = "createMeetingButton")
    private WebElement createButton;

    public CreationFormPage(final WebDriver driver) {
        super(driver);
    }

    protected boolean isNotTargetPage(WebDriver driver) {
        final String url = driver.getCurrentUrl();
        return !url.endsWith(URL_RELATIVE_PATH);
    }

    protected String pageName() {
        return "ミーティングの作成";
    }

    public static CreationFormPage to(WebDriver driver) {
        driver.get(URL_BASE + URL_RELATIVE_PATH);
        new WebDriverWait(driver, Duration.ofSeconds(20)).until(ExpectedConditions.urlMatches(".*" + URL_RELATIVE_PATH + "$"));
        return PageFactory.initElements(driver, CreationFormPage.class);
    }

    String subjectValue() {
        return subject.getAttribute("value");
    }

    String memoValue() {
        return memo.getAttribute("value");
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

    public int countOfScheduleInputs() {
        int count = 0;

        while (true) {
            try {
                meetingDate(count);
                count++;
            } catch (NoSuchElementException e) {
                break;
            }
        }

        return count;
    }

    private List<WebElement> removeScheduleButtons() {
        return driver.findElements(By.name("removeSchedule"));
    }

    public int countOfRemoveScheduleButtons() {
        return removeScheduleButtons().size();
    }

    public CreationFormPage removeScheduleAt(final int index) {
        final WebElement removeScheduleButton = removeScheduleButtons().get(index);
        removeScheduleButton.click();
        return PageFactory.initElements(driver, CreationFormPage.class);
    }

    public boolean isAddScheduleButtonEnabled() {
        return addScheduleButton.isEnabled();
    }

    public CreationFormPage addNewEmptyScheduleInput() {
        addScheduleButton.click();
        return PageFactory.initElements(driver, CreationFormPage.class);
    }

    private WebElement meetingDate(final int index) {
        return scheduleConstructElement(index, "meetingDate.value");
    }

    public CreationFormPage inputMeetingDateAt(final int index, final MeetingDateInput meetingDate) {
        final WebElement webElement = meetingDate(index);
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
        createButton.click();
        waitUntilPageReady();
        return PageFactory.initElements(driver, resultPage);
    }
}
