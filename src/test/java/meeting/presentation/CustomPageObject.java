package meeting.presentation;

import meeting.domain.model.organizer.OrganizerNumber;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class CustomPageObject {

    protected static final String URL_BASE = "http://localhost:9990";

    protected WebDriver driver;

    protected WebElement globalErrors;

    public CustomPageObject(WebDriver driver) {
        this.driver = driver;

        driver.manage().window().maximize();
        new WebDriverWait(driver, Duration.ofMinutes(3)).until(innerDriver -> readyStateCompleted(innerDriver));

        if (isNotTargetPage(driver)) {
            String message = String.format("%sページではありません。: %s, %s", pageName(), driver.getCurrentUrl(), driver.getPageSource());
            throw new IllegalStateException(message);
        }
    }

    public void setOrganizerNumber(WebDriver driver, OrganizerNumber organizerNumber) {

        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
        String organizerNumberValue = organizerNumber.value();
        String script = "localStorage.setItem('organizerNumber', '%s');".formatted(organizerNumberValue);
        javascriptExecutor.executeScript(script);

        driver.manage().deleteCookieNamed("organizerNumber");
        driver.manage().addCookie(new Cookie("organizerNumber",organizerNumberValue));
    }

    public void enableButtonByIdForcibly(WebDriver driver, String buttonId) {

        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
        String script = """
                var buttonObj = document.getElementById('%s');
                if (!buttonObj) return;
                buttonObj.disabled = false;
                """.formatted(buttonId);
        javascriptExecutor.executeScript(script);
    }

    protected abstract boolean isNotTargetPage(WebDriver driver);

    protected abstract String pageName();

    protected void waitUntilPageReady() {
        waitUntilPageReady(Duration.ofMinutes(1));
    }

    protected void waitUntilPageReady(Duration duration) {

        new WebDriverWait(driver, duration).until(
                (ExpectedCondition<Boolean>) driver -> readyStateCompleted(driver)
        );
    }

    protected boolean readyStateCompleted(WebDriver driver) {
        return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
    }

    protected static boolean isOrganizerNumberSet(WebDriver driver) {

        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
        String organizerNumberValue = (String) javascriptExecutor.executeScript("return localStorage.getItem('organizerNumber')");

        return Objects.nonNull(organizerNumberValue) && !organizerNumberValue.isBlank();
    }

    protected static boolean isParticipantNumberSet(WebDriver driver) {

        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
        String participantNumberValue = (String) javascriptExecutor.executeScript("return localStorage.getItem('participantNumber')");

        return Objects.nonNull(participantNumberValue) && !participantNumberValue.isBlank();
    }

    protected static boolean elementExists(WebDriver driver, String selectors) {
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
        String script = String.format("return !!(document.querySelector('%s'));", selectors) ;
        return (boolean) javascriptExecutor.executeScript(script);
    }

    protected static boolean isButtonEnabledById(WebDriver driver, String buttonId) {

        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
        String script = """
                var buttonObj = !!(document.getElementById('%s'));
                if (!buttonObj) return false;
                return !buttonObj.disabled;
                """.formatted(buttonId);
        return (boolean) javascriptExecutor.executeScript(script);
    }

    public List<String> globalErrorMessages() {
        try {

            List<WebElement> globalErrorList = globalErrors.findElements(By.cssSelector("li"));
            return globalErrorList.stream()
                    .map(WebElement::getText)
                    .collect(Collectors.toList());

        } catch (NoSuchElementException e) {
            return Collections.<String>emptyList();
        }
    }
}
