package meeting.presentation.meeting.page_objects;

import meeting.domain.model.meeting.MeetingNumber;
import meeting.presentation.CustomPageObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShowPage extends CustomPageObject {

    static final String MEETING_NUMBER_PLACE_HOLDER = "{meetingNumber}";
    static final String URL_RELATIVE_PATH_TEMPLATE = "/meeting/" + MEETING_NUMBER_PLACE_HOLDER + "/show";

    private static final Logger logger = LoggerFactory.getLogger(ShowPage.class);

    private WebElement editMeetingLink;

    private WebElement inputAttendanceButton;

    public ShowPage(WebDriver driver) {
        super(driver);
    }

    private static String relativePath(String replaceValue) {
        return URL_RELATIVE_PATH_TEMPLATE.replace(MEETING_NUMBER_PLACE_HOLDER, replaceValue);
    }

    private static String relativePathRegexPattern() {
        return ".*" + relativePath(MeetingNumber.REGEXP);
    }

    private static boolean urlMatches(String url) {
        String relativePathRegexPattern = relativePathRegexPattern();
        boolean urlMatches = url.matches(relativePathRegexPattern);
        return urlMatches;
    }

    @Override
    protected boolean isNotTargetPage(WebDriver driver) {
        String url = driver.getCurrentUrl();
        return !urlMatches(url);
    }

    @Override
    protected String pageName() {
        return "ミーティングの詳細";
    }

    public static ShowPage to(WebDriver driver, MeetingNumber meetingNumber) {
        String url = URL_BASE + relativePath(meetingNumber.value());
        driver.get(url);
        return PageFactory.initElements(driver, ShowPage.class);
    }

    public <T extends CustomPageObject> T moveToEditPage(Class<T> responsePage) throws Exception {
        try {
            editMeetingLink.click();
            return PageFactory.initElements(driver, responsePage);
        } catch (Exception e) {
            logger.error(driver.getCurrentUrl());
            logger.error(driver.getPageSource());
            throw e;
        }
    }

    public <T extends CustomPageObject> T moveToAttendanceInputPage(Class<T> responsePage) throws Exception {
        try {
            inputAttendanceButton.click();
            return PageFactory.initElements(driver, responsePage);
        } catch (Exception e) {
            logger.error(driver.getCurrentUrl());
            logger.error(driver.getPageSource());
            throw e;
        }
    }
}
