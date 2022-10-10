package meeting.presentation.meeting;

import meeting.domain.model.meeting.MeetingNumber;
import meeting.presentation.CustomPageObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class ShowPage extends CustomPageObject {

    static final String MEETING_NUMBER_PLACE_HOLDER = "{meetingNumber}";
    static final String URL_RELATIVE_PATH_TEMPLATE = "/meeting/" + MEETING_NUMBER_PLACE_HOLDER + "/show";

    private WebElement editMeetingLink;

    public ShowPage(WebDriver driver) {
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
    protected String pageName() {
        return "ミーティングの詳細";
    }

    public static ShowPage to(WebDriver driver, MeetingNumber meetingNumber) {
        String url = URL_BASE + relativePath(meetingNumber.value());
        driver.get(url);
        return PageFactory.initElements(driver, ShowPage.class);
    }

    public EditFormPage moveToEditPage() {
        editMeetingLink.click();
        waitUntilPageReady();
        return PageFactory.initElements(driver, EditFormPage.class);
    }
}
