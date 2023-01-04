package meeting.presentation.attendance.page_objects;

import meeting.domain.model.attendance.AttendanceNumber;
import meeting.domain.model.meeting.MeetingNumber;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.logging.Logger;

public class EditFormPage extends RegistrationForm {

    Logger logger = Logger.getLogger(EditFormPage.class.getName());

    static final String URL_RELATIVE_PATH_TEMPLATE = "/meeting/%s/attendance/%s/edit"
            .formatted(MeetingNumber.REGEX_PATTERN, AttendanceNumber.REGEX_PATTERN);

    static final String EDIT_ATTENDANCE_BUTTON_ID = "editAttendanceButton";

    private WebElement editAttendanceButton;

    public EditFormPage(WebDriver driver) {
        super(driver);

        new WebDriverWait(driver, Duration.ofMinutes(3)).until(innerDriver -> {
            return isParticipantNumberSet(innerDriver)
                    && isButtonEnabledById(innerDriver, EDIT_ATTENDANCE_BUTTON_ID);
        });
    }

    private static boolean urlMatches(String url) {
        return url.matches(".*" + URL_RELATIVE_PATH_TEMPLATE);
    }

    @Override
    protected boolean isNotTargetPage(WebDriver driver) {
        return !isTargetPage(driver);
    }

    public static boolean isTargetPage(WebDriver driver) {
        String url = driver.getCurrentUrl();
        return urlMatches(url);
    }

    @Override
    protected String pageName() {
        return "出欠の編集";
    }

    public <S> S editAttendance(Class<S> resultPage) {
        new WebDriverWait(driver, Duration.ofMinutes(3)).until(innerDriver -> {
            new Actions(innerDriver).moveToElement(editAttendanceButton).perform();
            enableButtonByIdForcibly(innerDriver, EDIT_ATTENDANCE_BUTTON_ID);
            return editAttendanceButton.isDisplayed()
                    && editAttendanceButton.isEnabled();
        });
        editAttendanceButton.click();
        return PageFactory.initElements(driver, resultPage);
    }
}
