package meeting.domain;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class ValidationConcern {

    protected <T> void validate(T object) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<T>> violations = validator.validate(object);

        if (violations.size() == 0) {
            return;
        }

        List<String> messages = new ArrayList<>();

        for (ConstraintViolation<T> violation : violations) {
            messages.add(violation.getMessage());
        }
        String messageString = String.join(System.lineSeparator(), messages);

        throwException(messageString);
    }

    protected void throwException(String messageString) {
        throw new IllegalArgumentException(messageString);
    }
}
