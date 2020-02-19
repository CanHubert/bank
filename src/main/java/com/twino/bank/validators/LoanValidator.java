package com.twino.bank.validators;

import com.twino.bank.entity.Loan;
import org.hibernate.validator.constraints.Range;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.lang.reflect.Field;
import java.time.LocalTime;
import java.util.Set;

public class LoanValidator implements MyValidator{

    private final Loan loan;


    public LoanValidator(Loan loan){
        this.loan = loan;
    }

    @Override
    public boolean isValid() {
        boolean valid = isAmountRangeValid();
        if(valid)
        {
            valid = isCreatedAfter6AM();
            if(!valid)
            {
                valid = isAmountNotMaxed();
            }
        }
        return valid;
    }

    private boolean isAmountRangeValid(){
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Loan>> violations = validator.validate(loan);
        return violations.isEmpty();
    }

    private boolean isCreatedAfter6AM(){
        LocalTime time = loan.getCreatedDate().toLocalTime();
        return time.isAfter(LocalTime.of(6,0));
    }

    private boolean isAmountNotMaxed()  {
        Field field;
        try
        {
            field = Loan.class.getDeclaredField("amount");
        }
        catch (NoSuchFieldException e)
        {
            return false;
        }

        Range range = field.getAnnotation(Range.class);

        return range != null && this.loan.getAmount().longValue() != range.max();
    }


}
