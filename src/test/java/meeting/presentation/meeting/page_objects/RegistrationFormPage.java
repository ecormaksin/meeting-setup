package meeting.presentation.meeting.page_objects;

import meeting.presentation.CustomPageObject;
import meeting.presentation.meeting.input.schedule.construct.*;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public abstract class RegistrationFormPage<T extends RegistrationFormPage> extends CustomPageObject {

    protected static final String SCHEDULE_CONSTRUCT_PREFIX = "scheduleInputs.list%d.scheduleConstruct.";


    @FindBy(id = "subject.value")
    protected WebElement subject;

    @FindBy(id = "memo.value")
    protected WebElement memo;

    @FindBy(id = "addSchedule")
    protected WebElement addScheduleButton;

    protected WebElement globalErrors;

    protected WebElement subjectError;

    protected WebElement memoError;

    protected WebElement scheduleInputsError;

    public RegistrationFormPage(WebDriver driver) {
        super(driver);
    }

    @SuppressWarnings("unchecked")
    public <T extends RegistrationFormPage> T inputSubject(String subjectValue) {
        subject.clear();
        subject.sendKeys(subjectValue);
        return (T) this;
    }

    public String subjectValue() {
        return subject.getAttribute("value");
    }

    public String memoValue() {
        return memo.getAttribute("value");
    }

    @SuppressWarnings("unchecked")
    public <T extends RegistrationFormPage> T inputMemo(String memoValue) {
        memo.clear();
        memo.sendKeys(memoValue);
        return (T)this;
    }

    @SuppressWarnings("unchecked")
    public <T extends RegistrationFormPage> T inputScheduleAt(int index, ScheduleConstructInput input) {
        inputMeetingDateAt(index, input.meetingDate());
        inputStartHourAt(index, input.startHour());
        inputStartMinuteAt(index, input.startMinute());
        inputDurationAt(index, input.duration());
        return (T)this;
    }

    @SuppressWarnings("unchecked")
    public <T extends RegistrationFormPage> T inputMeetingDateAt(int index, MeetingDateInput meetingDate) {
        WebElement webElement = meetingDate(index);
        webElement.clear();
        webElement.sendKeys(meetingDate.value());
        return (T)this;
    }

    @SuppressWarnings("unchecked")
    public <T extends RegistrationFormPage> T inputStartHourAt(int index, StartHourInput startHour) {
        WebElement webElement = scheduleConstructElement(index, "startHour.value");
        webElement.clear();
        webElement.sendKeys(startHour.value());
        return (T)this;
    }

    @SuppressWarnings("unchecked")
    public <T extends RegistrationFormPage> T inputStartMinuteAt(int index, StartMinuteInput startMinute) {
        WebElement webElement = scheduleConstructElement(index, "startMinute.value");
        Select selectObject = new Select(webElement);
        selectObject.selectByValue(startMinute.value());
        return (T)this;
    }

    @SuppressWarnings("unchecked")
    public <T extends RegistrationFormPage> T inputDurationAt(int index, DurationInput duration) {
        WebElement webElement = scheduleConstructElement(index, "duration.value");
        Select selectObject = new Select(webElement);
        selectObject.selectByValue(duration.value());
        return (T)this;
    }

    public <T extends RegistrationFormPage> T removeScheduleAt(int index, Class<T> resultPage) {
        WebElement removeScheduleButton = (WebElement) removeScheduleButtons().get(index);
        removeScheduleButton.click();
        return PageFactory.initElements(driver, resultPage);
    }

    protected WebElement scheduleConstructElement(int index, String itemName) {
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

    protected List<WebElement> removeScheduleButtons() {
        return driver.findElements(By.name("removeSchedule"));
    }

    public int countOfRemoveScheduleButtons() {
        return removeScheduleButtons().size();
    }

    public boolean isAddScheduleButtonEnabled() {
        return addScheduleButton.isEnabled();
    }

    public <T extends RegistrationFormPage> T addNewEmptyScheduleInput(Class<T> resultPage) {
        addScheduleButton.click();
        return PageFactory.initElements(driver, resultPage);
    }

    protected WebElement meetingDate(int index) {
        return scheduleConstructElement(index, "meetingDate.value");
    }

    public List<String> globalErrorMessages() {
        try {

            List<WebElement> globalErrorList = globalErrors.findElements(By.cssSelector("li"));
            return globalErrorList.stream()
                    .map(WebElement::getText)
                    .collect(Collectors.toList());

        } catch (NoSuchElementException e) {
            return Collections.<String>emptyList();
        }
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

    protected String scheduleConstructItemErrorMessageAt(int index, String itemName) {
        try {
            WebElement webElement =  scheduleConstructElement(index, itemName);
            return webElement.getText();
        } catch (NoSuchElementException e) {
            return "";
        }
    }
}
