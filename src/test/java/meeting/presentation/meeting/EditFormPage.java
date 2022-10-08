package meeting.presentation.meeting;

import meeting.domain.model.meeting.MeetingNumber;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class EditFormPage extends RegistrationFormPage {

    static final String MEETING_NUMBER_PLACE_HOLDER = "{meetingNumber}";
    static final String URL_RELATIVE_PATH_TEMPLATE = "/meeting/" + MEETING_NUMBER_PLACE_HOLDER + "/edit";

    private WebElement editMeetingButton;

    private WebElement deleteMeetingButton;

    public EditFormPage(WebDriver driver) {
        super(driver);
    }

    private static String relativePath(String replaceValue) {
        return URL_RELATIVE_PATH_TEMPLATE.replace(MEETING_NUMBER_PLACE_HOLDER, replaceValue);
    }

    private static String relativePathRegexPattern() {
        return relativePath(MeetingNumber.REGEXP);
    }

    private static boolean urlMatches(String url) {
        return url.matches(relativePathRegexPattern());
    }

    @Override
    protected boolean isNotTargetPage(WebDriver driver) {
        String url = driver.getCurrentUrl();
        return !urlMatches(url);
    }

    @Override
    protected String pageName() {
        return "ミーティングの編集";
    }

    public static EditFormPage to(WebDriver driver, MeetingNumber meetingNumber) {
        String url = URL_BASE + relativePath(meetingNumber.value());
        driver.get(url);
        waitUntilPageOperable(driver);
        return PageFactory.initElements(driver, EditFormPage.class);
    }

    protected static void waitUntilPageOperable(WebDriver driver) {

        new WebDriverWait(driver, Duration.ofSeconds(60)).until(innerDriver -> {
            String url = innerDriver.getCurrentUrl();
            boolean isUrlMatched = url.matches(String.format(".*%s$", relativePathRegexPattern()));

            return isUrlMatched
                    && isOrganizerNumberSet(innerDriver)
                    && isButtonEnabledById(innerDriver, "editMeetingButton");
        });
    }

    public <S> S editMeeting(Class<S> resultPage) {
        editMeetingButton.click();
        waitUntilPageReady();
        return PageFactory.initElements(driver, resultPage);
    }
}
