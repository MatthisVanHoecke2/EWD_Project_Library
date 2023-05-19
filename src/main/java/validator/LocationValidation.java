package validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.ewd.project_library.Location;

public class LocationValidation implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return Location.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Location loc = (Location) target;
		
		validatePlacecode(loc.getPlacecode1(), "placecode1", errors);
		validatePlacecode(loc.getPlacecode2(), "placecode2", errors);
		
		
	}
	
	private void validatePlacecode(String value, String placecode, Errors errors) {
		try {
			int code = Integer.parseInt(value);
			if(code < 50 || code > 300) errors.rejectValue(placecode, "location.codes.inRange.message", "must be between 49 and 301");
		}
		catch(NumberFormatException ex) {
			errors.rejectValue(placecode, "numeric.valueType", "must be a numeric value");
		}
	}

}
