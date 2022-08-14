package meeting.presentation;

import org.openqa.selenium.WebDriver;

public abstract class CustomPageObject {

    protected static final String URL_BASE = "http://localhost:9990";

    protected WebDriver driver;

    public CustomPageObject(WebDriver driver) {
        this.driver = driver;

        if (isNotTargetPage(driver)) {
            final String message = String.format("%sページではありません。: %s, %s", pageName(), driver.getCurrentUrl(), driver.getPageSource());
            throw new IllegalStateException(message);
        }
    }

    protected abstract boolean isNotTargetPage(WebDriver driver);

    protected abstract String pageName();
}
