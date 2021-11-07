package example.domain.model.schedule.construct;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Repeatable(StartTimeLessThanEndTime.List.class)
@Documented
@Constraint(validatedBy = { StartTimeLessThanEndTimeValidator.class })
public @interface StartTimeLessThanEndTime {

    String message() default "{example.domain.model.schedule.construct.StartTimeLessThanEndTime.message}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };

    @Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
    @Retention(RUNTIME)
    @Documented
    @interface List {
        StartTimeLessThanEndTime[] value();
    }
}
