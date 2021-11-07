package example.domain.model.schedule.construct;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class ScheduleConstructNotNullValidator
        implements ConstraintValidator<ScheduleConstructNotNull, ScheduleConstruct> {

    private String message;

    @Override
    public void initialize(ScheduleConstructNotNull constraintAnnotation) {
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(ScheduleConstruct value, ConstraintValidatorContext context) {

        // すべて未入力はOKとする
        if (Objects.isNull(value)) return true;
        if (value.isNull()) return true;

        // すべて入力済ならOK
        if (value.isNotNull()) return true;

        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation();
        return false;
    }
}
