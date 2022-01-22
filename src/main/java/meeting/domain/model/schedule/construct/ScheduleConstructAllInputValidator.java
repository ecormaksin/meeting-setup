package meeting.domain.model.schedule.construct;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class ScheduleConstructAllInputValidator
        implements ConstraintValidator<ScheduleConstructAllInput, ScheduleConstruct> {

    private String message;

    @Override
    public void initialize(ScheduleConstructAllInput constraintAnnotation) {
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(ScheduleConstruct value, ConstraintValidatorContext context) {

        if (Objects.isNull(value)) return true;
        if (value.validity() !=  ScheduleConstructValidity.不当_一部未入力あり) return true;

        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation();
        return false;
    }
}
