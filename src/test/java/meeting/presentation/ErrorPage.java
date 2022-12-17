package meeting.presentation;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ErrorPage extends CustomPageObject {

    public static final String TITLE = "エラー";

    @FindBy(css = "main > article > p")
    private WebElement errorMessage;

    public ErrorPage(WebDriver driver) {
        super(driver);
    }

    public static boolean isTargetPage(WebDriver driver) {
        return driver.getTitle().equals(TITLE);
    }

    @Override
    protected boolean isNotTargetPage(WebDriver driver) {
        return !isTargetPage(driver);
    }

    @Override
    protected String pageName() {
        return "エラー";
    }

    public String errorMessage() {
        return errorMessage.getText();
    }
}
