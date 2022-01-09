package meeting.domain.model.schedule;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class ScheduleIsFutureWhenToAddValidator implements ConstraintValidator<ScheduleIsFutureWhenToAdd, Schedule> {

    private String message;

    @Override
    public void initialize(ScheduleIsFutureWhenToAdd constraintAnnotation) {
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Schedule value, ConstraintValidatorContext context) {
        if (Objects.isNull(value)) return true;
        return value.isFutureWhenToAdd();
    }
}
