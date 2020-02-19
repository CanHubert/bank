package com.twino.bank;

import com.twino.bank.entity.Customer;
import com.twino.bank.entity.Loan;
import com.twino.bank.service.LoanService;
import com.twino.bank.validators.LoanValidator;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static java.time.LocalTime.of;
import static java.time.LocalDateTime.of;

class BankApplicationTests {

	private LoanService loanService = new LoanService();


	@Test
	void countLoansWithSameIpAddress(){
		String ipAddress = "192.168.1.1";
		Customer customer1 = new Customer(1L, "John", "Doe");
		Customer customer2 = new Customer(2L, "Mary", "Jane");
		loanService.save(new Loan(1L, customer1, ipAddress));
		loanService.save(new Loan(2L, customer1, ipAddress));
		loanService.save(new Loan(3L, customer1, "1.1.1.1"));
		loanService.save(new Loan(4L, customer2, ipAddress));

		assertEquals(2L, loanService.countByCustomerAndIpAddress(customer1, ipAddress));
		assertEquals(1L, loanService.countByCustomerAndIpAddress(customer2, ipAddress));
	}

	@Test
	public void loanApplyIsValid() {
		LocalDate now = LocalDate.now();
		BigDecimal maxed = new BigDecimal(80000);
		BigDecimal notMaxed = new BigDecimal(25000);
		Loan loan1 = new Loan(of(now, of(6,1)), maxed);
		Loan loan2 = new Loan(of(now, of(5,59)), notMaxed);
		Loan loan3 = new Loan(of(now, of(10,15)), notMaxed);

		assertTrue(new LoanValidator(loan1).isValid());
		assertTrue(new LoanValidator(loan2).isValid());
		assertTrue(new LoanValidator(loan3).isValid());
	}
}
