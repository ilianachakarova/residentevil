package softuni.residentevil.customAnnotations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Date;

public class PresentOrPastValidator implements ConstraintValidator<PresentOrPast, Date> {

    @Override
    public boolean isValid(Date date, ConstraintValidatorContext constraintValidatorContext) {
        Date today = new Date();
        return date.before(today);
    }
}
