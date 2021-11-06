package example.domain.model.schedule;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class ScheduleElementsNotNullValidator
        implements ConstraintValidator<ScheduleElementsNotNull, Schedule> {

    private String message;

    @Override
    public void initialize(ScheduleElementsNotNull constraintAnnotation) {
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Schedule value, ConstraintValidatorContext context) {

        // すべて未入力はOKとする
        if (Objects.isNull(value)) return true;
        if (value.areAllElementsNull()) return true;

        // すべて入力済ならOK
        if (value.areAllElementsNotNull()) return true;

        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation();
        return false;
    }
}
