package meeting.domain.model.schedule.construct;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class StartTimeLessThanEndTimeValidator
        implements ConstraintValidator<StartTimeLessThanEndTime, ScheduleConstruct> {

    private String message;

    @Override
    public void initialize(StartTimeLessThanEndTime constraintAnnotation) {
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(ScheduleConstruct value, ConstraintValidatorContext context) {

        if (value.validity() != ScheduleConstructValidity.不当_時刻の前後関係が不当) return true;

        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation();
        return false;
    }
}
