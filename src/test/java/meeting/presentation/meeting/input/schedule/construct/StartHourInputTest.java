package meeting.presentation.meeting.input.schedule.construct;

import meeting.domain.model.schedule.construct.StartHour;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StartHourInputTest {

    private static Validator validator;

    private static final int HOUR_MIN = 0;
    private static final int HOUR_MAX = 23;

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @ParameterizedTest
    @MethodSource("validInputs")
    void _開始時間が既定内の値の場合はエラーにならない(String inputValue) {
        final StartHourInput startHourInput = new StartHourInput(inputValue);

        final Set<ConstraintViolation<StartHourInput>> constraintViolations = validator.validate(startHourInput);

        assertEquals(0, constraintViolations.size());
    }

    static List<String> validInputs() {
        List<String> inputs = new ArrayList<> ();

        inputs.add(null);
        inputs.add("");
        for (int i = StartHour.MIN; i <= StartHour.MAX; i++) {
            inputs.add(String.valueOf(i));
        }
        return inputs;
    }

    @ParameterizedTest
    @MethodSource("invalidInputs")
    void _開始時間が規定外の値の場合はエラーになる(String inputValue) {
        final StartHourInput startHourInput = new StartHourInput(inputValue);

        final Set<ConstraintViolation<StartHourInput>> constraintViolations = validator.validate(startHourInput);

        assertEquals(1, constraintViolations.size());
        assertEquals("開始時間は9時から17時の間で指定してください。", constraintViolations.iterator().next().getMessage());
    }

    static List<String> invalidInputs() {
        List<String> inputs = new ArrayList<> ();

        for (int i = HOUR_MIN; i < StartHour.MIN; i++) {
            inputs.add(String.valueOf(i));
        }
        for (int i = (StartHour.MAX + 1); i <= HOUR_MAX; i++) {
            inputs.add(String.valueOf(i));
        }
        inputs.add("abc");

        return inputs;
    }
}
