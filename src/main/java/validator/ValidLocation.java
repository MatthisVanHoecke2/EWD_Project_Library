package validator;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = LocationValidator.class)
@Target({METHOD, FIELD, TYPE_USE})
@Retention(RUNTIME)
public @interface ValidLocation {
	String message() default "{location.difference.message}";
	
	Class<?>[] groups() default{};
	
	Class<? extends Payload>[] payload() default {};
}
