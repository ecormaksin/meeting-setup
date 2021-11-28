package meeting.domain.model.schedule;

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
@Constraint(validatedBy = { SchedulesNotDuplicatedValidator.class })
public @interface SchedulesNotDuplicated {
    String message() default "{meeting.domain.model.schedule.SchedulesNotDuplicated.message}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
