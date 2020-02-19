package com.twino.bank.validators;

import com.twino.bank.entity.Loan;
import com.twino.bank.entity.dto.request.ProlongateLoanTermDto;
import org.hibernate.validator.constraints.Range;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.lang.reflect.Field;
import java.util.Set;

public class ProlongateRangeValidator implements MyValidator {

    private final ProlongateLoanTermDto dto;

    public ProlongateRangeValidator(ProlongateLoanTermDto dto){
        this.dto = dto;
    }

    @Override
    public boolean isValid() {
        if(!validateFields())
        {
            return false;
        }

        Field field;
        try
        {
            field = ProlongateLoanTermDto.class.getDeclaredField("prolongateTermForDays");
        }
        catch (NoSuchFieldException e)
        {
            return false;
        }

        Range range = field.getAnnotation(Range.class);

        return range != null
                && this.dto.getProlongateTermForDays() <=  range.max();
    }

    private boolean validateFields(){
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<ProlongateLoanTermDto>> violations = validator.validate(dto);
        return violations.isEmpty();
    }


}
