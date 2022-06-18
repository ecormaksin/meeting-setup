package meeting.presentation.meeting;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;
public class CreationPage extends LoadableComponent<CreationPage> {

    private static final String URL_RELATIVE_PATH = "/meeting/create/";

    private final WebDriver driver;

    @FindBy(id = "meetingNumber.value")
    private WebElement meetingNumber;

    @FindBy(id = "subject.value")
    private WebElement subject;

    @FindBy(id = "memo.value")
    private WebElement memo;

    @FindBy(id = "organizerNumber.value")
    private WebElement organizerNumber;

    public CreationPage(final WebDriver driver) {
        this.driver = driver;

        PageFactory.initElements(driver, this);
    }

    @Override
    protected void load() {
        driver.get("http://localhost:9990" + URL_RELATIVE_PATH);
    }

    @Override
    protected void isLoaded() throws Error {
        final String url = driver.getCurrentUrl();
        assertTrue(url.endsWith(URL_RELATIVE_PATH), "ミーティングの作成ページではありません。: "  + url);
    }

    String meetingNumberValue() {
        return meetingNumber.getAttribute("value");
    }

    String subjectValue() {
        return subject.getAttribute("value");
    }

    String memoValue() {
        return memo.getAttribute("value");
    }

    String organizerNumberValue() {
        return organizerNumber.getAttribute("value");
    }
}
