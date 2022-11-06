package meeting.presentation;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ErrorPage extends CustomPageObject {

    static final String URL_RELATIVE_PATH = "/error";

    @FindBy(css = "main > article > p")
    private WebElement errorMessage;

    public ErrorPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected boolean isNotTargetPage(WebDriver driver) {
        return !driver.getTitle().equals("エラー");
    }

    @Override
    protected String pageName() {
        return "エラー";
    }

    private static void waitUntilPageOperable(WebDriver driver) {

        new WebDriverWait(driver, Duration.ofMinutes(3)).until(ExpectedConditions.titleIs("エラー"));
    }

    public String errorMessage() {
        return errorMessage.getText();
    }
}
