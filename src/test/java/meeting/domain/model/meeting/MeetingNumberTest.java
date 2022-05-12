package meeting.domain.model.meeting;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MeetingNumberTest {

    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void _既定内の値の場合はエラーにならない() {
        MeetingNumber meetingNumber = MeetingNumber.newNumber();
        MeetingNumber testTargetMeetingNumber = new MeetingNumber(meetingNumber.value());

        Set<ConstraintViolation<MeetingNumber>> constraintViolations = validator.validate(meetingNumber);

        assertEquals(0, constraintViolations.size());
    }

    @ParameterizedTest
    @MethodSource("invalidInputs")
    void _ミーティング番号が規定外の値の場合はエラーになる(String inputValue) {
        MeetingNumber meetingNumber = new MeetingNumber(inputValue);

        Set<ConstraintViolation<MeetingNumber>> constraintViolations = validator.validate(meetingNumber);

        assertEquals(1, constraintViolations.size());
        assertEquals("ミーティング番号は26桁の半角英数字で指定してください。", constraintViolations.iterator().next().getMessage());
    }

    static List<String> invalidInputs() {
        MeetingNumber meetingNumber = MeetingNumber.newNumber();
        String validValue = meetingNumber.value();
        String sizeShortValue = validValue.substring(0, MeetingNumber.SIZE - 1);
        return Arrays.asList(
                null,
                "",
                sizeShortValue, // 桁数不足
                validValue + "a", // 桁数超過
                sizeShortValue + "$" // 記号が含まれている
        );
    }
}
