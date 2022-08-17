package meeting.presentation.meeting.input.schedule.construct;

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

public class MeetingDateInputTest {

    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @ParameterizedTest
    @MethodSource("validInputs")
    void _開催日が既定内の値の場合はエラーにならない(String inputValue) {
        final MeetingDateInput meetingDateInput = new MeetingDateInput(inputValue);

        final Set<ConstraintViolation<MeetingDateInput>> constraintViolations = validator.validate(meetingDateInput);

        assertEquals(0, constraintViolations.size());
    }

    static List<String> validInputs() {
        List<String> inputs = new ArrayList<> ();

        inputs.add("");
        inputs.add("2022-01-01");
        return inputs;
    }

    @ParameterizedTest
    @MethodSource("invalidInputs")
    void _開催日が規定外の値の場合はエラーになる(String inputValue) {
        final MeetingDateInput meetingDateInput = new MeetingDateInput(inputValue);

        final Set<ConstraintViolation<MeetingDateInput>> constraintViolations = validator.validate(meetingDateInput);

        assertEquals(1, constraintViolations.size());
        assertEquals("開催日は'yyyy-MM-dd'形式で入力してください。", constraintViolations.iterator().next().getMessage());
    }

    static List<String> invalidInputs() {
        List<String> inputs = new ArrayList<> ();

        inputs.add("a");
        inputs.add("2022/01/01");

        return inputs;
    }
}
