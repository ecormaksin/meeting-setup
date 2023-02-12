package meeting.presentation.meeting.page_objects;

import meeting.domain.model.meeting.MeetingNumber;
import meeting.presentation.CustomPageObject;
import meeting.presentation.attendance.page_objects.EditFormPage;
import meeting.presentation.attendance.page_objects.InputFormPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Objects;

public class ShowPage extends CustomPageObject {

    private static final String MEETING_NUMBER_PLACE_HOLDER = "{meetingNumber}";
    private static final String URL_RELATIVE_PATH_TEMPLATE = "/meeting/%s/show".formatted(MEETING_NUMBER_PLACE_HOLDER);

    private static final String ATTENDANCE_EDIT_SELECTOR_TEMPLATE = "main > article > form > table > thead > tr > th:nth-child(%d) > a";
    private static final int ATTENDANCE_EDIT_LINK_INDEX_OFFSET = 5;

    private static final String MEETING_SCHEDULE_DATETIME_COLUMN_SELECTOR_TEMPLATE = "body > main > article > form > table > tbody > tr:nth-child(%d) > td:nth-child(2)";

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
        return ".*" + relativePath(MeetingNumber.REGEX_PATTERN);
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

    public <T extends CustomPageObject> T moveToEditPage(Class<T> resultPage) throws Exception {
        try {
            editMeetingLink.click();
            return PageFactory.initElements(driver, resultPage);
        } catch (Exception e) {
            logger.error(driver.getCurrentUrl());
            logger.error(driver.getPageSource());
            throw e;
        }
    }

    public <T extends CustomPageObject> T moveToAttendanceInputPage(Class<T> resultPage) throws Exception {
        try {
            inputAttendanceButton.click();
            if (resultPage.getName().equals(InputFormPage.class.getName())) {
                new WebDriverWait(driver, Duration.ofMinutes(3)).until(innerDriver -> {
                    boolean isTargetPage = InputFormPage.isTargetPage(innerDriver);
                    if (!isTargetPage) {
                        logger.info("出欠入力ボタン再押下");
                        inputAttendanceButton.click();
                    }
                    return isTargetPage;
                });
            }
            return PageFactory.initElements(driver, resultPage);
        } catch (Exception e) {
            logger.error(driver.getCurrentUrl());
            logger.error(driver.getPageSource());
            throw e;
        }
    }

    public <T extends CustomPageObject> T moveToAttendanceEditPage(int indexNumber, Class<T> resultPage) throws Exception {
        try {
            String cssSelector = ATTENDANCE_EDIT_SELECTOR_TEMPLATE.formatted((indexNumber + ATTENDANCE_EDIT_LINK_INDEX_OFFSET));
            WebElement attendanceEditLink = driver.findElement(By.cssSelector(cssSelector));
            attendanceEditLink.click();
            if (resultPage.getName().equals(EditFormPage.class.getName())) {
                new WebDriverWait(driver, Duration.ofMinutes(3)).until(innerDriver -> {
                    boolean isTargetPage = EditFormPage.isTargetPage(innerDriver);
                    if (!isTargetPage) {
                        logger.info("出欠編集リンク再押下");
                        attendanceEditLink.click();
                    }
                    return isTargetPage;
                });
            }
            return PageFactory.initElements(driver, resultPage);
        } catch (Exception e) {
            logger.error(driver.getCurrentUrl());
            logger.error(driver.getPageSource());
            throw e;
        }
    }

    public boolean isBackgroundColorEmphasizedAt(int scheduleRowIndex) {
        String cssSelector = MEETING_SCHEDULE_DATETIME_COLUMN_SELECTOR_TEMPLATE.formatted(scheduleRowIndex + 1);
        WebElement scheduleColumn = driver.findElement(By.cssSelector(cssSelector));
        String className = scheduleColumn.getAttribute("class");
        return Objects.nonNull(className) && className.contains("convenience-most-suitable");
    }
}
