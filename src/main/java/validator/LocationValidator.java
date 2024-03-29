package validator;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import com.ewd.project_library.Location;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class LocationValidator implements ConstraintValidator<ValidLocation, Location>{

	@Autowired
	private MessageSource messageSource;
	
	@Override
	public void initialize(ValidLocation constraintAnnotation) {
	}
	
	@Override
	public boolean isValid(Location value, ConstraintValidatorContext context) {
		return validatePlacecodes(value, context);
	}
	
	private boolean validatePlacecodes(Location value, ConstraintValidatorContext context) {
		boolean isValid = true;
		if(value.getPlacecode1().isEmpty() && value.getPlacecode2().isEmpty()) return true;
		
		if(!validatePlacecode(value.getPlacecode1(), "placecode1", context)) isValid = false;
		if(!validatePlacecode(value.getPlacecode2(), "placecode2", context)) isValid = false;
		try {
			int code1 = Integer.parseInt(value.getPlacecode1());
			int code2 = Integer.parseInt(value.getPlacecode2());
			int difference = Math.abs(code1-code2);
			if(difference < 50) {
				isValid = false;
			}
		}
		catch(NumberFormatException ex) {
			isValid = false;
		}
		return isValid;
	}
	
	private boolean validatePlacecode(String value, String placecode, ConstraintValidatorContext context) {
		try {
			int code = Integer.parseInt(value);
			if(code < 50 || code > 300) {
				Locale locale = LocaleContextHolder.getLocale();
				context.buildConstraintViolationWithTemplate(messageSource.getMessage("location.codes.inRange.message", new Object[]{}, locale))
					.addPropertyNode(placecode)
					.addConstraintViolation();
				return false;
			}
		}
		catch(NumberFormatException ex) {
			return false;
		}
		return true;
	}

}
