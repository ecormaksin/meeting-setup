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

public class MemoTest {

    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @ParameterizedTest
    @MethodSource("validInputs")
    void _メモが既定内の値の場合はエラーにならない(String inputValue) {
        final Memo memo = new Memo(inputValue);

        final Set<ConstraintViolation<Memo>> constraintViolations = validator.validate(memo);

        assertEquals(0, constraintViolations.size());
    }

    static List<String> validInputs() {
        return Arrays.asList(
                null,
                "",
                "あ".repeat(Memo.MAX_SIZE)
        );
    }

    @Test
    void _メモが規定外の値の場合はエラーになる() {
        final Memo memo = new Memo("あ".repeat(Memo.MAX_SIZE + 1));

        final Set<ConstraintViolation<Memo>> constraintViolations = validator.validate(memo);

        assertEquals(1, constraintViolations.size());
        assertEquals("メモは100文字以内で入力してください。", constraintViolations.iterator().next().getMessage());
    }
}
