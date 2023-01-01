package meeting.presentation.attendance.page_objects;

import meeting.domain.model.attendance.convenience.ConvenienceCategory;
import meeting.presentation.CustomPageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.logging.Logger;

public abstract class RegistrationForm<T extends RegistrationForm> extends CustomPageObject {

    Logger logger = Logger.getLogger(RegistrationForm.class.getName());

    private static final String CONVENIENCE_CATEGORY_NAME_TEMPLATE = "conveniences.list[%d].convenienceCategory";

    @FindBy(id = "participantName.value")
    protected WebElement participantName;

    @FindBy(id = "attendanceComment.value")
    protected WebElement attendanceComment;

    protected WebElement participantNameError;

    protected WebElement attendanceCommentError;

    private By convenienceCategoryLabel = By.xpath("../label");

    public RegistrationForm(WebDriver driver) {
        super(driver);
    }

    @SuppressWarnings("unchecked")
    public <T extends RegistrationForm> T inputParticipantName(String value) {
        participantName.clear();
        participantName.sendKeys(value);
        return (T) this;
    }

    public String participantNameValue() {
        return participantName.getAttribute("value");
    }

    @SuppressWarnings("unchecked")
    public <T extends RegistrationForm> T inputAttendanceComment(String value) {
        attendanceComment.clear();
        attendanceComment.sendKeys(value);
        return (T) this;
    }

    public String attendanceCommentValue() {
        return attendanceComment.getAttribute("value");
    }

    private List<WebElement> convenienceCategoryOptions(int scheduleOrdinalNumber) {
        String name = CONVENIENCE_CATEGORY_NAME_TEMPLATE.formatted(scheduleOrdinalNumber - 1);
        List<WebElement> radioButtons = driver.findElements(By.name(name));
        return radioButtons;
    }

    @SuppressWarnings("unchecked")
    public <T extends RegistrationForm> T inputConvenienceCategory(
            int scheduleOrdinalNumber,
            ConvenienceCategory convenienceCategory) {

        List<WebElement> radioButtons = convenienceCategoryOptions(scheduleOrdinalNumber);
        String targetValue = convenienceCategory.symbol();


        WebElement radioButton = radioButtons.stream()
                .filter(r -> convenienceCategoryLabel(r).getText().equals(convenienceCategory.symbol()))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException("「%s」に該当する選択肢が見つかりませんでした。".formatted(targetValue)));

        WebElement buttonLabel = radioButton.findElement(convenienceCategoryLabel);
        buttonLabel.click();

        return (T) this;
    }

    private WebElement convenienceCategoryLabel(WebElement radioButton) {
        return radioButton.findElement(convenienceCategoryLabel);
    }

    public String convenienceCategoryText(int scheduleOrdinalNumber) {
        List<WebElement> radioButtons = convenienceCategoryOptions(scheduleOrdinalNumber);

        return radioButtons.stream()
                .filter(r -> r.isSelected())
                .findAny()
                .map(r -> convenienceCategoryLabel(r).getText())
                .orElse("");
    }

    public String participantNameErrorMessage() {
        try {
            return participantNameError.getText();
        } catch (NoSuchElementException e) {
            return "";
        }
    }

    public String attendanceCommentErrorMessage() {
        try {
            return attendanceCommentError.getText();
        } catch (NoSuchElementException e) {
            return "";
        }
    }
}
