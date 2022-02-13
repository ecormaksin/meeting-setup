package meeting.domain.model.meeting.search_condition;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = { StartDateLessThanEqualsEndDateValidator.class })
public @interface StartDateLessThanEqualsEndDate {

    String message() default "{meeting.domain.model.meeting.search_condition.StartDateLessThanEqualsEndDate.message}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
