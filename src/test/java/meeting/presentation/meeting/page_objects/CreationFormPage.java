package meeting.presentation.meeting.page_objects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CreationFormPage extends RegistrationFormPage {

    static final String URL_RELATIVE_PATH = "/meeting/create";

    private WebElement createMeetingButton;

    public CreationFormPage(WebDriver driver) {
        super(driver);

        new WebDriverWait(driver, Duration.ofSeconds(300)).until(innerDriver -> {
            return isOrganizerNumberSet(innerDriver)
                    && isButtonEnabledById(innerDriver, "createMeetingButton");
        });
    }

    @Override
    protected boolean isNotTargetPage(WebDriver driver) {
        String url = driver.getCurrentUrl();
        return !url.endsWith(URL_RELATIVE_PATH);
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
        createMeetingButton.click();
        waitUntilPageReady();
        return PageFactory.initElements(driver, resultPage);
    }
}
