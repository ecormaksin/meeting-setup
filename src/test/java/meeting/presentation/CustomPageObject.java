package meeting.presentation;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class CustomPageObject {

    protected static final String URL_BASE = "http://localhost:9990";

    protected WebDriver driver;

    public CustomPageObject(WebDriver driver) {
        this.driver = driver;

        if (isNotTargetPage(driver)) {
            String message = String.format("%sページではありません。: %s, %s", pageName(), driver.getCurrentUrl(), driver.getPageSource());
            throw new IllegalStateException(message);
        }
    }

    protected abstract boolean isNotTargetPage(WebDriver driver);

    protected abstract String pageName();

    protected void waitUntilPageReady() {
        waitUntilPageReady(Duration.ofSeconds(30));
    }

    protected void waitUntilPageReady(Duration duration) {

        new WebDriverWait(driver, duration).until(
                (ExpectedCondition<Boolean>) driver ->
                        ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete")
        );
    }
}
