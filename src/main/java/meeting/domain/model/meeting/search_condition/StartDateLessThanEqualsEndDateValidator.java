package meeting.domain.model.meeting.search_condition;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static meeting.domain.model.meeting.search_condition.MeetingSearchConditionValidity.不当_検索開始日と検索終了日の前後関係が逆;

public class StartDateLessThanEqualsEndDateValidator
        implements ConstraintValidator<StartDateLessThanEqualsEndDate, MeetingSearchCondition> {

    private String message;

    @Override
    public void initialize(StartDateLessThanEqualsEndDate constraintAnnotation) {
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(MeetingSearchCondition value, ConstraintValidatorContext context) {

        if (value.validity() != 不当_検索開始日と検索終了日の前後関係が逆) return true;

        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation();
        return false;
    }
}
