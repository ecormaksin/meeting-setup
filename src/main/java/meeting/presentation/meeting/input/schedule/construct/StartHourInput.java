package meeting.presentation.meeting.input.schedule.construct;

import meeting.domain.model.schedule.construct.StartHour;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.AssertTrue;
import java.util.Objects;
import java.util.Set;

/**
 * 開始時間（入力用）
 */
public class StartHourInput {

    String value;

    @AssertTrue(message = StartHour.MESSAGE)
    public boolean isValid() {
        if (Objects.isNull(value)) return true;
        if (value.isEmpty()) return true;
        if (isNotNumeric()) return false;

        return isValidAsDomain();
    }

    private boolean isNotNumeric() {
        return !(value.chars().allMatch(Character::isDigit));
    }

    private boolean isValidAsDomain() {
        final StartHour startHour = new StartHour(Integer.valueOf(value));
        final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        final Validator validator = factory.getValidator();
        final Set<ConstraintViolation<StartHour>> constraintViolations = validator.validate(startHour);

        return constraintViolations.size() == 0;
    }

    public StartHourInput(String value) {
        this.value = value;
    }

    @Deprecated
    StartHourInput() {}

    public static StartHourInput empty() {
        return new StartHourInput("");
    }

    public String value() {
        return value;
    }

    public boolean isEmpty() {
        return value.isEmpty();
    }

    public boolean isSame(StartHourInput other) {
        return this.value.equals(other.value);
    }
}
