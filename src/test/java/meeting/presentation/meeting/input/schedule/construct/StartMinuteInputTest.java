package meeting.presentation.meeting.input.schedule.construct;

import meeting.domain.model.schedule.construct.StartMinute;
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

public class StartMinuteInputTest {

    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @ParameterizedTest
    @MethodSource("validInputs")
    void _開始分が既定内の値の場合はエラーにならない(String inputValue) {
        StartMinuteInput startMinuteInput = new StartMinuteInput(inputValue);

        Set<ConstraintViolation<StartMinuteInput>> constraintViolations = validator.validate(startMinuteInput);

        assertEquals(0, constraintViolations.size());
    }

    static List<String> validInputs() {
        List<String> inputs = new ArrayList<> ();

        inputs.add(null);
        inputs.add("");
        for (StartMinute startMinute : StartMinute.values()) {
            inputs.add(String.valueOf(startMinute.value()));
        }
        return inputs;
    }

    @ParameterizedTest
    @MethodSource("invalidInputs")
    void _開始分が規定外の値の場合はエラーになる(String inputValue) {
        StartMinuteInput startMinuteInput = new StartMinuteInput(inputValue);

        Set<ConstraintViolation<StartMinuteInput>> constraintViolations = validator.validate(startMinuteInput);

        assertEquals(1, constraintViolations.size());
        assertEquals("開始分は30分単位で入力してください。", constraintViolations.iterator().next().getMessage());
    }

    static List<String> invalidInputs() {
        List<String> inputs = new ArrayList<> ();

        inputs.add("1");
        inputs.add("31");
        inputs.add("abc");

        return inputs;
    }
}
