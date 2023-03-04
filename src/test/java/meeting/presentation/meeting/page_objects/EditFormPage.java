package meeting.presentation.meeting.page_objects;

import meeting.domain.model.meeting.MeetingNumber;
import meeting.presentation.ErrorPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.logging.Logger;

public class EditFormPage extends RegistrationForm {

    Logger logger = Logger.getLogger(EditFormPage.class.getName());

    static final String URL_RELATIVE_PATH_TEMPLATE = "/meeting/%s/edit".formatted(MeetingNumber.REGEX_PATTERN);

    private WebElement editMeetingButton;

    private WebElement deleteMeetingButton;

    public EditFormPage(WebDriver driver) {
        super(driver);

        new WebDriverWait(driver, Duration.ofMinutes(3)).until(innerDriver -> {
            return isOrganizerNumberSet(innerDriver)
                    && isButtonEnabledById(innerDriver, "editMeetingButton");
        });
    }

    private static boolean urlMatches(String url) {
        return url.matches(".*" + URL_RELATIVE_PATH_TEMPLATE);
    }

    public static boolean isTargetPage(WebDriver driver) {
        String url = driver.getCurrentUrl();
        return urlMatches(url);
    }

    @Override
    protected boolean isNotTargetPage(WebDriver driver) {
        return !isTargetPage(driver);
    }

    @Override
    protected String pageName() {
        return "ミーティングの編集";
    }

    public <S> S editMeeting(Class<S> resultPage) {
        new WebDriverWait(driver, Duration.ofMinutes(3)).until(innerDriver -> {
            new Actions(innerDriver).moveToElement(editMeetingButton).perform();
            enableButtonByIdForcibly(innerDriver, "editMeetingButton");
            return editMeetingButton.isDisplayed()
                    && editMeetingButton.isEnabled();
        });
        editMeetingButton.click();
        new WebDriverWait(driver, Duration.ofMinutes(3)).until(innerDriver -> {
            boolean isTargetPage = false;
            String resultPageClazzName = resultPage.getName();
            if (resultPageClazzName.equals(EditFormPage.class.getName())) {
                isTargetPage = EditFormPage.isTargetPage(innerDriver);
            }
            if (resultPageClazzName.equals(EditCompletedPage.class.getName())) {
                isTargetPage = EditCompletedPage.isTargetPage(innerDriver);
            }
            if (resultPageClazzName.equals(ErrorPage.class.getName())) {
                isTargetPage = ErrorPage.isTargetPage(innerDriver);
            }
            if (!isTargetPage) {
                logger.info("編集ボタン再押下");
                editMeetingButton.click();
            }
            return isTargetPage;
        });
        return PageFactory.initElements(driver, resultPage);
    }

    public <S> S deleteMeeting(Class<S> resultPage) {
        new WebDriverWait(driver, Duration.ofMinutes(3)).until(innerDriver -> {
            new Actions(innerDriver).moveToElement(deleteMeetingButton).perform();
            enableButtonByIdForcibly(innerDriver, "deleteMeetingButton");
            return deleteMeetingButton.isDisplayed()
                    && deleteMeetingButton.isEnabled();
        });
        deleteMeetingButton.click();
        if (resultPage.getName().equals(DeleteCompletedPage.class.getName())) {
            new WebDriverWait(driver, Duration.ofMinutes(3)).until(innerDriver -> {
                boolean isTargetPage = DeleteCompletedPage.isTargetPage(innerDriver);
                if (!isTargetPage) {
                    logger.info("削除ボタン再押下");
                    deleteMeetingButton.click();
                }
                return isTargetPage;
            });
        }

        return PageFactory.initElements(driver, resultPage);
    }
}