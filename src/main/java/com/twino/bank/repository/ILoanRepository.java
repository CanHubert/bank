package com.twino.bank.repository;

import com.twino.bank.entity.Customer;
import com.twino.bank.entity.Loan;

import java.util.Optional;

public interface ILoanRepository {

    long countByCustomerAndIpAddress(Customer customer, String ipAddress);

    void save(Loan loan);

    Optional<Loan> findById(Long id);
}
