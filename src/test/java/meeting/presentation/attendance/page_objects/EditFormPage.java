package meeting.presentation.attendance.page_objects;

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

    static final String MEETING_NUMBER_PLACE_HOLDER = "{meetingNumber}";
    static final String ATTENDANCE_NUMBER_PLACE_HOLDER = "{attendanceNumber}";

    static final String URL_RELATIVE_PATH_TEMPLATE = "/meeting/%s/attendance/%s/edit"
            .formatted(MEETING_NUMBER_PLACE_HOLDER, ATTENDANCE_NUMBER_PLACE_HOLDER);

    private WebElement inputAttendanceButton;

    public EditFormPage(WebDriver driver) {
        super(driver);

        new WebDriverWait(driver, Duration.ofMinutes(3)).until(innerDriver -> {
            return isParticipantNumberSet(innerDriver)
                    && isButtonEnabledById(innerDriver, "editAttendanceButton");
        });
    }

    private static String relativePath(String replaceValue) {
        return URL_RELATIVE_PATH_TEMPLATE.replace(MEETING_NUMBER_PLACE_HOLDER, replaceValue);
    }

    private static String relativePathRegexPattern() {
        return ".*" + relativePath(MeetingNumber.REGEXP);
    }

    private static boolean urlMatches(String url) {
        return url.matches(relativePathRegexPattern());
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
        return "出欠の入力";
    }

    public <S> S inputAttendance(Class<S> resultPage) {
        new WebDriverWait(driver, Duration.ofMinutes(3)).until(innerDriver -> {
            new Actions(innerDriver).moveToElement(inputAttendanceButton).perform();
            enableButtonByIdForcibly(innerDriver, "inputAttendanceButton");
            return inputAttendanceButton.isDisplayed()
                    && inputAttendanceButton.isEnabled();
        });
        inputAttendanceButton.click();
        return PageFactory.initElements(driver, resultPage);
    }
}
