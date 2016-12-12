package bluoh.feed.util.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ashutosh on 11-12-2016.
 */
public class StringValidatorImpl implements ConstraintValidator<ValidateString, String> {

   private List<String> valueList;

   public void initialize(ValidateString constraint) {
      valueList = new ArrayList<String>();
      for(String val : constraint.acceptedValues()) {
         valueList.add(val.toUpperCase());
      }
   }

   public boolean isValid(String value, ConstraintValidatorContext context) {
      if(!valueList.contains(value.toUpperCase())) {
         return false;
      }
      return true;
   }
}
