package com.twino.bank;

import com.twino.bank.controller.LoanController;
import com.twino.bank.entity.Customer;
import com.twino.bank.entity.dto.request.NewLoanDto;
import com.twino.bank.entity.dto.request.ProlongateLoanTermDto;
import com.twino.bank.entity.dto.response.ResponseMessage;
import org.junit.jupiter.api.*;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static java.time.LocalTime.of;
import static java.time.LocalDateTime.of;
import static java.time.LocalDate.of;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LoanControllerTest {

    private LoanController loanController;
    private NewLoanDto newLoanDto;

    String ipAddress = "192.168.1.1";
    Customer customer1 = new Customer(1L, "John", "Doe");

    @BeforeAll
    public void setUp(){
        this.loanController = new LoanController();
        newLoanDto = new NewLoanDto();
        newLoanDto.setCustomer(customer1);
        newLoanDto.setIpAddress(ipAddress);
        newLoanDto.setTerm(of(of(2021, Month.DECEMBER, 31), of(12,0)));
    }

    @Test
    @Order(1)
    public void addLoanSuccessfully(){
        newLoanDto.setAmount(new BigDecimal(75000));
        newLoanDto.setCreatedDate(of(of(2020, Month.FEBRUARY, 19), of(12,0)));

        String actualMessage = Objects.requireNonNull(loanController.addLoan(newLoanDto).getBody()).getMessage();
        String expectedMessage = "Loan granted successfully";

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    @Order(2)
    public void addLoan_ValidationFailed(){
        newLoanDto.setAmount(new BigDecimal(80000));
        newLoanDto.setCreatedDate(of(LocalDate.now(), of(5,50)));

        String actualMessage = Objects.requireNonNull(loanController.addLoan(newLoanDto).getBody()).getMessage();
        String expectedMessage = "Loan validation failed!";

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    @Order(3)
    public void addLoan_SameIpAddress(){
        newLoanDto.setAmount(new BigDecimal(45000));
        newLoanDto.setCreatedDate(LocalDateTime.now());

        String actualMessage = Objects.requireNonNull(loanController.addLoan(newLoanDto).getBody()).getMessage();
        String expectedMessage = "Too many requests from same IP address";

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    @Order(4)
    public void prolongateTermTestForGranted(){
        ResponseEntity<ResponseMessage> responseMessage =
                loanController.prolongateLoanTerm(new ProlongateLoanTermDto(1,14));

        String expectedMessage = "Term of loan prolongated successfully!";
        LocalDateTime expectedDate = of(of(2022, Month.JANUARY, 14), of(12,0));

        String actualMessage = Objects.requireNonNull(responseMessage.getBody()).getMessage();
        LocalDateTime actualDate = (LocalDateTime) responseMessage.getBody().getValue();

        assertEquals(expectedMessage, actualMessage);
        assertEquals(expectedDate, actualDate);
    }

    @Test
    @Order(5)
    public void prolongateTermAgain(){
        ResponseEntity<ResponseMessage> responseMessage =
                loanController.prolongateLoanTerm(new ProlongateLoanTermDto(1,14));

        String expectedMessage = "Term of loan can't be prolongated.";
        String actualMessage = Objects.requireNonNull(responseMessage.getBody()).getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    @Order(6)
    public void wrongLoanId(){
        long loanId = 10L;
        ResponseEntity<ResponseMessage> responseMessage =
                loanController.prolongateLoanTerm(new ProlongateLoanTermDto(loanId,14));

        String expectedMessage = String.format("Loan with id = %d doesn't exists!", loanId);
        String actualMessage = Objects.requireNonNull(responseMessage.getBody()).getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    @Order(7)
    public void prolongateTermOutOfRange(){
        ResponseEntity<ResponseMessage> responseMessage =
                loanController.prolongateLoanTerm(new ProlongateLoanTermDto(1,16));

        String expectedMessage = "Wrong range of prolongated days!";
        String actualMessage = Objects.requireNonNull(responseMessage.getBody()).getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    @Order(8)
    public void prolongateTermTestForRejected(){
        ResponseEntity<ResponseMessage> responseMessage =
                loanController.prolongateLoanTerm(new ProlongateLoanTermDto(2,12));

        String expectedMessage = "Term of loan can't be prolongated.";
        String actualMessage = Objects.requireNonNull(responseMessage.getBody()).getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

}
