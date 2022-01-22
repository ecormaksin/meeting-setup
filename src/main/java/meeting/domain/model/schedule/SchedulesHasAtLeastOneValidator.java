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

        if (Objects.nonNull(value) && value.validity() != SchedulesValidity.不当_日程候補なし) return true;

        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation();
        return false;
    }
}
