package meeting.presentation.attendance.page_objects;

import meeting.presentation.CustomPageObject;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public abstract class RegistrationForm<T extends RegistrationForm> extends CustomPageObject {

    private static final String CONVENIENCE_CATEGORY_TEMPLATE = "conveniences.list%d.convenienceCategory";

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

    @SuppressWarnings("unchecked")
    public <T extends RegistrationForm> T inputAttendanceComment(String value) {
        attendanceComment.clear();
        attendanceComment.sendKeys(value);
        return (T) this;
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
