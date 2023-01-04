package meeting.presentation.attendance.page_objects;

import meeting.domain.model.meeting.MeetingNumber;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.logging.Logger;

public class InputFormPage extends RegistrationForm {

    Logger logger = Logger.getLogger(InputFormPage.class.getName());

    static final String URL_RELATIVE_PATH_TEMPLATE = "/meeting/%s/attendance/input".formatted(MeetingNumber.REGEX_PATTERN);

    static final String INPUT_ATTENDANCE_BUTTON_ID = "inputAttendanceButton";

    private WebElement inputAttendanceButton;

    public InputFormPage(WebDriver driver) {
        super(driver);

        new WebDriverWait(driver, Duration.ofMinutes(3)).until(innerDriver -> {
            return isParticipantNumberSet(innerDriver)
                    && isButtonEnabledById(innerDriver, INPUT_ATTENDANCE_BUTTON_ID);
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
        return "出欠の入力";
    }

    public <S> S inputAttendance(Class<S> resultPage) {
        new WebDriverWait(driver, Duration.ofMinutes(3)).until(innerDriver -> {
            new Actions(innerDriver).moveToElement(inputAttendanceButton).perform();
            enableButtonByIdForcibly(innerDriver, INPUT_ATTENDANCE_BUTTON_ID);
            return inputAttendanceButton.isDisplayed()
                    && inputAttendanceButton.isEnabled();
        });
        inputAttendanceButton.click();
        return PageFactory.initElements(driver, resultPage);
    }
}
