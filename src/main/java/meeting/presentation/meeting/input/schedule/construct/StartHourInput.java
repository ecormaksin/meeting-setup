package meeting.presentation.meeting.input.schedule.construct;

import meeting.domain.model.schedule.construct.StartHour;

import javax.validation.constraints.AssertTrue;
import java.util.Objects;

/**
 * 開始時間（入力用）
 */
public class StartHourInput implements Element {

    String value;

    @Deprecated
    StartHourInput() {}

    public StartHourInput(String value) {
        this.value = value;
    }

    static StartHourInput empty() {
        return new StartHourInput("");
    }

    public static StartHourInput fromDomainObject(final StartHour startHour) {
        final int intValue = startHour.value();
        final String stringValue = String.valueOf(intValue);
        return new StartHourInput(stringValue);
    }

    @AssertTrue(message = StartHour.MESSAGE)
    boolean isValid() {
        if (isEmpty()) return true;
        return isValidAsDomain();
    }

    public boolean isEmpty() {
        if (Objects.isNull(value)) return true;
        return value.isEmpty();
    }

    public boolean isValidAsDomain() {
        if (isEmpty()) return false;
        try {
            toDomainObject();
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public boolean isSame(StartHourInput other) {
        return this.value.equals(other.value);
    }

    public StartHour toDomainObject() {
        return StartHour.fromString(value);
    }

    public String value() {
        return value;
    }
}
