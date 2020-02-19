package com.twino.bank.controller;

import com.twino.bank.entity.dto.response.ResponseMessage;
import com.twino.bank.entity.Loan;
import com.twino.bank.entity.dto.request.NewLoanDto;
import com.twino.bank.entity.dto.request.ProlongateLoanTermDto;
import com.twino.bank.enums.LoanStatus;
import com.twino.bank.service.ILoanService;
import com.twino.bank.service.LoanService;
import com.twino.bank.validators.LoanValidator;
import com.twino.bank.validators.ProlongateRangeValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@Component
public class LoanController {

    private ILoanService loanService;

    public LoanController(ILoanService loanService){
        this.loanService = loanService;
    }
    public LoanController(){
        this.loanService = new LoanService();
    }

    @PostMapping("/loans")
    public ResponseEntity<ResponseMessage> addLoan(@RequestBody @Valid NewLoanDto newLoanDto){
        Loan loan = new Loan(newLoanDto);
        ResponseEntity<ResponseMessage> response;
        if(loanService.countByCustomerAndIpAddress(newLoanDto.getCustomer(), newLoanDto.getIpAddress()) > 1)
        {
            loan.setStatus(LoanStatus.REJECTED);
            response = ResponseEntity.badRequest().body(new ResponseMessage("Too many requests from same IP address"));
        }
        else
        {
            if(new LoanValidator(loan).isValid())
            {
                loan.setStatus(LoanStatus.GRANTED);
                response = ResponseEntity.ok(new ResponseMessage("Loan granted successfully", loan));
            }
            else
            {
                loan.setStatus(LoanStatus.REJECTED);
                response = ResponseEntity.badRequest().body(new ResponseMessage("Loan validation failed!"));
            }
        }
        loanService.save(loan);
        return response;
    }

    @PutMapping("/loans")
    public ResponseEntity<ResponseMessage> prolongateLoanTerm(@RequestBody @Valid ProlongateLoanTermDto prolongateLoanTermDto){
        ResponseEntity<ResponseMessage> response;
        Optional<Loan> loanOptional = loanService.findById(prolongateLoanTermDto.getLoanId());
        if(!loanOptional.isPresent())
        {
           return ResponseEntity.badRequest().body(
                   new ResponseMessage(String.format("Loan with id = %d doesn't exists!", prolongateLoanTermDto.getLoanId())));
        }

        Loan loan = loanOptional.get();

//                        () -> new RuntimeException(String.format("Loan with id = %d don't exists!", prolongateLoanTermDto.getLoanId())));
        if(!new ProlongateRangeValidator(prolongateLoanTermDto).isValid())
        {
            response = ResponseEntity.badRequest().body(new ResponseMessage("Wrong range of prolongated days!"));
        }
        else if(loan.isCanProlongTerm() && loan.getStatus() == LoanStatus.GRANTED)
        {
            loan.setTerm(loan.getTerm().plusDays(prolongateLoanTermDto.getProlongateTermForDays()));
            loan.setCanProlongTerm(false);
            loanService.save(loan);
            response = ResponseEntity.ok(new ResponseMessage("Term of loan prolongated successfully!", loan.getTerm()));
        }
        else
        {
            response = ResponseEntity.badRequest().body(new ResponseMessage("Term of loan can't be prolongated."));
        }
       return response;
    }
}
