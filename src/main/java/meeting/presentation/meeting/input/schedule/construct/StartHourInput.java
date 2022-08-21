package meeting.presentation.meeting.input.schedule.construct;

import meeting.domain.model.schedule.construct.StartHour;

import javax.validation.constraints.AssertTrue;

/**
 * 開始時間（入力用）
 */
public class StartHourInput implements ScheduleConstructElement {

    String value;

    boolean valid;

    @Deprecated
    StartHourInput() {}

    public StartHourInput(final String value) {
        this.value = value;

        valid = isValid();
    }

    static StartHourInput empty() {
        return new StartHourInput("");
    }

    public static StartHourInput of(final String value) {
        return new StartHourInput(value);
    }

    public static StartHourInput of(final int intValue) {
        return of(String.valueOf(intValue));
    }

    public static StartHourInput of(final StartHour startHour) {
        return of(startHour.value());
    }

    @AssertTrue(message = StartHour.BETWEEN_SPECIFIED_RANGE)
    boolean isValid() {
        if (isEmpty()) return true;
        return isValidAsDomain();
    }

    public boolean isEmpty() {
        return value.isEmpty();
    }

    public boolean isValidAsDomain() {
        if (isEmpty()) return false;
        try {
            toStartHour();
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public boolean isSame(StartHourInput other) {
        return this.value.equals(other.value);
    }

    public StartHour toStartHour() {
        return StartHour.of(value);
    }

    public String value() {
        return value;
    }
}
