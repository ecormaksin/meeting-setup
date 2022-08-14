package meeting.domain.model.meeting;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MeetingNumberTest {

    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void _ミーティング番号が既定内の値の場合はエラーにならない() {
        final MeetingNumber meetingNumber = MeetingNumber.newNumber();

        final Set<ConstraintViolation<MeetingNumber>> constraintViolations = validator.validate(meetingNumber);

        assertEquals(0, constraintViolations.size());
    }

    @ParameterizedTest
    @MethodSource("invalidInputs")
    void _ミーティング番号が規定外の値の場合はエラーになる(String inputValue) {
        final MeetingNumber meetingNumber = new MeetingNumber(inputValue);

        final Set<ConstraintViolation<MeetingNumber>> constraintViolations = validator.validate(meetingNumber);

        assertEquals(1, constraintViolations.size());
        assertEquals("ミーティング番号は26桁の半角英数字で指定してください。", constraintViolations.iterator().next().getMessage());
    }

    static List<String> invalidInputs() {

        final MeetingNumber meetingNumber = MeetingNumber.newNumber();
        final String validValue = meetingNumber.value();
        final String sizeShortValue = validValue.substring(0, MeetingNumber.SIZE - 1);
        final String sizeOverValue = validValue + "a";
        final String symbolIncluded = sizeShortValue + "$";

        return Arrays.asList(
                null,
                "",
                sizeShortValue,
                sizeOverValue,
                symbolIncluded);
    }

    @Test
    void testStaticMethod() {
        try(MockedStatic<MeetingNumber> meetingNumberMockedStatic = Mockito.mockStatic(MeetingNumber.class)) {
            meetingNumberMockedStatic.when(MeetingNumber::newNumber).thenReturn(MeetingNumberTestHelper.fromInt(1));
            MeetingNumber meetingNumber = MeetingNumber.newNumber();
            System.out.println(meetingNumber.value());
            assertTrue(meetingNumber.value().endsWith("001"));
        }
    }
}
