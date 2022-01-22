package meeting.domain.model.schedule.construct;

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
@Constraint(validatedBy = {ScheduleConstructAllInputValidator.class})
public @interface ScheduleConstructAllInput {

    String message() default "{meeting.domain.model.schedule.construct.ScheduleConstructAllInput.message}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
