package example.domain.model.schedule;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class SchedulesNotDuplicatedValidator
        implements ConstraintValidator<SchedulesNotDuplicated, Schedules> {

    private String message;

    @Override
    public void initialize(SchedulesNotDuplicated constraintAnnotation) {
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Schedules value, ConstraintValidatorContext context) {

        if (isNotDuplicated(value)) return true;

        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation();
        return false;
    }

    private boolean isNotDuplicated(Schedules value) {
        if (Objects.isNull(value)) return false;
        return value.isNotDuplicated();
    }
}
