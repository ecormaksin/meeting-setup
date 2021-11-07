package example.domain.model.schedule;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class StartTimeLessThanEndTimeValidator
        implements ConstraintValidator<StartTimeLessThanEndTime, Schedule> {

    private String message;

    @Override
    public void initialize(StartTimeLessThanEndTime constraintAnnotation) {
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Schedule value, ConstraintValidatorContext context) {

        if (value.areAllElementsNull()) return true;
        if (!value.areAllElementsNotNull()) return true; // 別のバリデーションでエラーになるので、ここではスルーする
        if (value.isStartTimeLessThanEndTime()) return true;

        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation();
        return false;
    }
}
