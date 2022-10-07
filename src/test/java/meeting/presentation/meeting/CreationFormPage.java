package meeting.presentation.meeting;

import meeting.presentation.CustomPageObject;
import meeting.presentation.meeting.input.schedule.construct.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CreationFormPage extends RegistrationFormPage {

    static final String URL_RELATIVE_PATH = "/meeting/create";

    @FindBy(id = "createMeetingButton")
    private WebElement createButton;

    public CreationFormPage(WebDriver driver) {
        super(driver);
    }

    protected boolean isNotTargetPage(WebDriver driver) {
        String url = driver.getCurrentUrl();
        return !url.endsWith(URL_RELATIVE_PATH);
    }

    protected String pageName() {
        return "ミーティングの作成";
    }

    public static CreationFormPage to(WebDriver driver) {
        driver.get(URL_BASE + URL_RELATIVE_PATH);
        waitUntilPageOperable(driver);
        return PageFactory.initElements(driver, CreationFormPage.class);
    }

    protected static void waitUntilPageOperable(WebDriver driver) {

        new WebDriverWait(driver, Duration.ofSeconds(60)).until(innerDriver -> {
            String url = innerDriver.getCurrentUrl();
            boolean isUrlMatched = url.matches(String.format(".*%s$", URL_RELATIVE_PATH));

            return isUrlMatched
                    && isOrganizerNumberSet(innerDriver)
                    && isButtonEnabledById(innerDriver, "createMeetingButton");
        });
    }

    public <S> S createMeeting(Class<S> resultPage) {
        createButton.click();
        waitUntilPageReady();
        return PageFactory.initElements(driver, resultPage);
    }
}
