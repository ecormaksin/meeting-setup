package meeting.presentation.meeting.page_objects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.logging.Logger;

public class CreationFormPage extends RegistrationForm {

    Logger logger = Logger.getLogger(CreationFormPage.class.getName());
    static final String URL_RELATIVE_PATH = "/meeting/create";

    private WebElement createMeetingButton;

    public CreationFormPage(WebDriver driver) {
        super(driver);

        new WebDriverWait(driver, Duration.ofMinutes(3)).until(innerDriver -> {
            return isOrganizerNumberSet(innerDriver)
                    && isButtonEnabledById(innerDriver, "createMeetingButton");
        });
    }

    public static boolean isTargetPage(WebDriver driver) {
        String url = driver.getCurrentUrl();
        return url.endsWith(URL_RELATIVE_PATH);
    }

    @Override
    protected boolean isNotTargetPage(WebDriver driver) {
        return !isTargetPage(driver);
    }

    @Override
    protected String pageName() {
        return "ミーティングの作成";
    }

    public static CreationFormPage to(WebDriver driver) {
        driver.get(URL_BASE + URL_RELATIVE_PATH);
        return PageFactory.initElements(driver, CreationFormPage.class);
    }

    public <S> S createMeeting(Class<S> resultPage) {
        new WebDriverWait(driver, Duration.ofMinutes(3)).until(innerDriver -> {
            new Actions(innerDriver).moveToElement(createMeetingButton).perform();
            enableButtonByIdForcibly(innerDriver, "createMeetingButton");
            return createMeetingButton.isDisplayed()
                    && createMeetingButton.isEnabled();
        });
        createMeetingButton.click();
        new WebDriverWait(driver, Duration.ofMinutes(3)).until(innerDriver -> {
            boolean isTargetPage = false;
            String resultPageClazzName = resultPage.getName();
            if (resultPageClazzName.equals(CreationFormPage.class.getName())) {
                isTargetPage = CreationFormPage.isTargetPage(innerDriver);
            }
            if (resultPageClazzName.equals(CreationCompletedPage.class.getName())) {
                isTargetPage = CreationCompletedPage.isTargetPage(innerDriver);
            }
            if (!isTargetPage) {
                logger.info("作成ボタン再押下");
                createMeetingButton.click();
            }
            return isTargetPage;
        });
        return PageFactory.initElements(driver, resultPage);
    }
}