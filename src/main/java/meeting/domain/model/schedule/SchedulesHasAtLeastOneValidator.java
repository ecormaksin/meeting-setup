package meeting.domain.model.schedule;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class SchedulesHasAtLeastOneValidator
        implements ConstraintValidator<SchedulesHasAtLeastOne, Schedules> {

    private String message;

    @Override
    public void initialize(SchedulesHasAtLeastOne constraintAnnotation) {
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Schedules value, ConstraintValidatorContext context) {

        if (hasValidSchedule(value)) return true;

        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation();
        return false;
    }

    private boolean hasValidSchedule(Schedules value) {
        if (Objects.isNull(value)) return false;
        return value.hasValidSchedule();
    }
}
