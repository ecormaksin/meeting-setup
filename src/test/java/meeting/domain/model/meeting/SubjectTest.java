package meeting.domain.model.meeting;

import org.junit.jupiter.api.BeforeAll;
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

public class SubjectTest {

    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @ParameterizedTest
    @MethodSource("validInputs")
    void _件名が既定内の値の場合はエラーにならない(String inputValue) {
        final Subject subject = new Subject(inputValue);

        final Set<ConstraintViolation<Subject>> constraintViolations = validator.validate(subject);

        assertEquals(0, constraintViolations.size());
    }

    static List<String> validInputs() {
        return Arrays.asList(
                "あ",
                " あ",
                "あ ",
                " あ ",
                "あ い",
                "　あ",
                "あ　",
                "　あ　",
                "あ　い",
                "あ".repeat(Subject.MAX_SIZE)
        );
    }

    @ParameterizedTest
    @MethodSource("invalidInputs")
    void _件名が規定外の値の場合はエラーになる(String inputValue) {
        final Subject subject = new Subject(inputValue);

        final Set<ConstraintViolation<Subject>> constraintViolations = validator.validate(subject);

        assertEquals(1, constraintViolations.size());
        assertEquals("件名は30文字以内で必ず入力してください。", constraintViolations.iterator().next().getMessage());
    }

    static List<String> invalidInputs() {
        final String sizeOverValue = "あ".repeat(Subject.MAX_SIZE + 1);
        return Arrays.asList(
                null,
                "",
                " ",
                "　",
                sizeOverValue);
    }
}
