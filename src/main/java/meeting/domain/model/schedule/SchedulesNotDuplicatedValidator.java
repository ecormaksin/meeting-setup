package meeting.domain.model.schedule;

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

        if (Objects.isNull(value)) return true;
        if (value.validity() != SchedulesValidity.不当_日程重複) return true;

        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation();
        return false;
    }
}
