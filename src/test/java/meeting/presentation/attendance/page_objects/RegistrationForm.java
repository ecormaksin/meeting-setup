package meeting.presentation.attendance.page_objects;

import meeting.presentation.CustomPageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.logging.Logger;

public abstract class RegistrationForm<T extends RegistrationForm> extends CustomPageObject {

    Logger logger = Logger.getLogger(RegistrationForm.class.getName());

    private static final String CONVENIENCE_CATEGORY_NAME_TEMPLATE = "conveniences.list[%d].convenienceCategory";

    @FindBy(id = "participantName.value")
    protected WebElement participantName;

    @FindBy(id = "attendanceComment.value")
    protected WebElement attendanceComment;

    protected WebElement participantNameError;

    protected WebElement attendanceCommentError;

    public RegistrationForm(WebDriver driver) {
        super(driver);
    }

    @SuppressWarnings("unchecked")
    public <T extends RegistrationForm> T inputParticipantName(String value) {
        participantName.clear();
        participantName.sendKeys(value);
        return (T) this;
    }

    public String participantNameValue() {
        return participantName.getAttribute("value");
    }

    @SuppressWarnings("unchecked")
    public <T extends RegistrationForm> T inputAttendanceComment(String value) {
        attendanceComment.clear();
        attendanceComment.sendKeys(value);
        return (T) this;
    }

    public String attendanceCommentValue() {
        return attendanceComment.getAttribute("value");
    }

    public String convenienceCategoryValue(int scheduleOrdinalNumber) {
        String name = CONVENIENCE_CATEGORY_NAME_TEMPLATE.formatted(scheduleOrdinalNumber - 1);
        List<WebElement> radioButtons = driver.findElements(By.name(name));

        return radioButtons.stream()
                .filter(r -> r.isSelected())
                .findAny()
                .map(r -> r.getAttribute("value"))
                .orElse("");
    }

    public String participantNameErrorMessage() {
        try {
            return participantNameError.getText();
        } catch (NoSuchElementException e) {
            return "";
        }
    }

    public String attendanceCommentErrorMessage() {
        try {
            return attendanceCommentError.getText();
        } catch (NoSuchElementException e) {
            return "";
        }
    }
}
