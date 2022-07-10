package meeting.presentation.meeting.input.schedule.construct;

import meeting.domain.model.schedule.construct.Duration;
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

public class DurationInputTest {

    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @ParameterizedTest
    @MethodSource("validInputs")
    void _会議時間が既定内の値の場合はエラーにならない(String inputValue) {
        final DurationInput durationInput = new DurationInput(inputValue);

        final Set<ConstraintViolation<DurationInput>> constraintViolations = validator.validate(durationInput);

        assertEquals(0, constraintViolations.size());
    }

    static List<String> validInputs() {
        List<String> inputs = new ArrayList<> ();

        inputs.add("");
        for (Duration startMinute : Duration.values()) {
            inputs.add(String.valueOf(startMinute.value()));
        }
        return inputs;
    }

    @ParameterizedTest
    @MethodSource("invalidInputs")
    void _会議時間が規定外の値の場合はエラーになる(String inputValue) {
        final DurationInput durationInput = new DurationInput(inputValue);

        final Set<ConstraintViolation<DurationInput>> constraintViolations = validator.validate(durationInput);

        assertEquals(1, constraintViolations.size());
        assertEquals("会議時間は30分単位・2時間以内で入力してください。", constraintViolations.iterator().next().getMessage());
    }

    static List<String> invalidInputs() {
        List<String> inputs = new ArrayList<> ();

        inputs.add(null);
        inputs.add("31");
        inputs.add("121");
        inputs.add("abc");

        return inputs;
    }
}
