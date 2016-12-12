package bluoh.feed.util.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Created by Ashutosh on 11-12-2016.
 */

@Documented
@Constraint(validatedBy = StringValidatorImpl.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ValidateString {
    String[] acceptedValues();

    String message() default "{uk.dds.ideskos.validator.ValidateString.message}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
