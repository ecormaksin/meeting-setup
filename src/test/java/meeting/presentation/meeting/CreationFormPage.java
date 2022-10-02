package meeting.presentation.meeting;

import meeting.presentation.CustomPageObject;
import meeting.presentation.meeting.input.schedule.construct.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CreationFormPage extends CustomPageObject {

    static final String URL_RELATIVE_PATH = "/meeting/create";

    static final String SCHEDULE_CONSTRUCT_PREFIX = "scheduleInputs.list%d.scheduleConstruct.";

    Logger logger = LoggerFactory.getLogger(CreationFormPage.class);

    @FindBy(id = "subject.value")
    private WebElement subject;

    @FindBy(id = "memo.value")
    private WebElement memo;

    @FindBy(id = "addSchedule")
    private WebElement addScheduleButton;

    @FindBy(id = "createMeetingButton")
    private WebElement createButton;

    private WebElement subjectError;

    private WebElement memoError;

    private WebElement globalErrors;

    private WebElement scheduleInputsError;

    public CreationFormPage(WebDriver driver) {
        super(driver);
    }

    protected boolean isNotTargetPage(WebDriver driver) {
        String url = driver.getCurrentUrl();
        return !url.endsWith(URL_RELATIVE_PATH);
    }

    protected String pageName() {
        return "ミーティングの作成";
    }

    public static CreationFormPage to(WebDriver driver) {
        driver.get(URL_BASE + URL_RELATIVE_PATH);
        waitUntilPageOperable(driver);
        return PageFactory.initElements(driver, CreationFormPage.class);
    }

    private static void waitUntilPageOperable(WebDriver driver) {

        new WebDriverWait(driver, Duration.ofSeconds(60)).until(innerDriver -> {
            String url = innerDriver.getCurrentUrl();
            boolean isUrlMatched = url.matches(String.format(".*%s$", URL_RELATIVE_PATH));

            JavascriptExecutor javascriptExecutor = (JavascriptExecutor) innerDriver;

            String organizerNumberValue = (String) javascriptExecutor.executeScript("return localStorage.getItem('organizerNumber')");
            boolean isOrganizerNumberSet = Objects.nonNull(organizerNumberValue) && !organizerNumberValue.isBlank();

            boolean isCreateButtonEnabled = (boolean) javascriptExecutor.executeScript("return !document.getElementById('createMeetingButton').disabled");

            return isUrlMatched
                    && isOrganizerNumberSet
                    && isCreateButtonEnabled;
        });
    }

    String subjectValue() {
        return subject.getAttribute("value");
    }

    String memoValue() {
        return memo.getAttribute("value");
    }

    public CreationFormPage inputSubject(String subject) {
        this.subject.sendKeys(subject);
        return this;
    }

    public CreationFormPage inputMemo(String memo) {
        this.memo.sendKeys(memo);
        return this;
    }

    public CreationFormPage inputScheduleAt(int index, ScheduleConstructInput input) {
        inputMeetingDateAt(index, input.meetingDate());
        inputStartHourAt(index, input.startHour());
        inputStartMinuteAt(index, input.startMinute());
        inputDurationAt(index, input.duration());
        return this;
    }

    private WebElement scheduleConstructElement(int index, String itemName) {
        String byId = String.format(SCHEDULE_CONSTRUCT_PREFIX, index) + itemName;
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

    public CreationFormPage removeScheduleAt(int index) {
        WebElement removeScheduleButton = removeScheduleButtons().get(index);
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

    private WebElement meetingDate(int index) {
        return scheduleConstructElement(index, "meetingDate.value");
    }

    public CreationFormPage inputMeetingDateAt(int index, MeetingDateInput meetingDate) {
        WebElement webElement = meetingDate(index);
        webElement.sendKeys(meetingDate.value());
        return this;
    }

    public CreationFormPage inputStartHourAt(int index, StartHourInput startHour) {
        WebElement webElement = scheduleConstructElement(index, "startHour.value");
        webElement.sendKeys(startHour.value());
        return this;
    }

    public CreationFormPage inputStartMinuteAt(int index, StartMinuteInput startMinute) {
        WebElement webElement = scheduleConstructElement(index, "startMinute.value");
        Select selectObject = new Select(webElement);
        selectObject.selectByValue(startMinute.value());
        return this;
    }

    public CreationFormPage inputDurationAt(int index, DurationInput duration) {
        WebElement webElement = scheduleConstructElement(index, "duration.value");
        Select selectObject = new Select(webElement);
        selectObject.selectByValue(duration.value());
        return this;
    }

    public <T> T createMeeting(Class<T> resultPage) {
        createButton.click();
        waitUntilPageReady();
        return PageFactory.initElements(driver, resultPage);
    }

    public String subjectErrorMessage() {
        try {
            return subjectError.getText();
        } catch (NoSuchElementException e) {
            return "";
        }
    }

    public String memoErrorMessage() {
        try {
            return memoError.getText();
        } catch (NoSuchElementException e) {
            return "";
        }
    }

    public List<String> globalErrorMessages() {
        try {

            List<WebElement> globalErrorList = globalErrors.findElements(By.cssSelector("li"));
            return globalErrorList.stream()
                    .map(WebElement::getText)
                    .collect(Collectors.toList());

        } catch (NoSuchElementException e) {
            return Collections.emptyList();
        }
    }

    public String scheduleInputsErrorMessage() {
        try {
            return scheduleInputsError.getText();
        } catch (NoSuchElementException e) {
            return "";
        }
    }

    public String meetingDateEmptyErrorMessageAt(int index) {
        return scheduleConstructItemErrorMessageAt(index, "meetingDateNotEmpty");
    }

    public String startHourEmptyErrorMessageAt(int index) {
        return scheduleConstructItemErrorMessageAt(index, "startHourNotEmpty");
    }

    public String startHourInvalidErrorMessageAt(int index) {
        return scheduleConstructItemErrorMessageAt(index, "startHour.valid");
    }

    public String startMinuteEmptyErrorMessageAt(int index) {
        return scheduleConstructItemErrorMessageAt(index, "startMinuteNotEmpty");
    }

    public String durationEmptyErrorMessageAt(int index) {
        return scheduleConstructItemErrorMessageAt(index, "durationNotEmpty");
    }

    private String scheduleConstructItemErrorMessageAt(int index, String itemName) {
        try {
            WebElement webElement =  scheduleConstructElement(index, itemName);
            return webElement.getText();
        } catch (NoSuchElementException e) {
            return "";
        }
    }
}
