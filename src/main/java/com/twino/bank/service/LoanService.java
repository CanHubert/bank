package com.twino.bank.service;

import com.twino.bank.entity.Customer;
import com.twino.bank.entity.Loan;
import com.twino.bank.repository.ILoanRepository;
import com.twino.bank.repository.LoanRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoanService implements ILoanService {

    private ILoanRepository loanRepository;

    public LoanService() {
        this.loanRepository = new LoanRepository();
    }

    @Override
    public long countByCustomerAndIpAddress(Customer customer, String ipAddress) {
        return loanRepository.countByCustomerAndIpAddress(customer, ipAddress);
    }

    @Override
    public void save(Loan loan){
        loanRepository.save(loan);
    }

    @Override
    public Optional<Loan> findById(Long id){
        return loanRepository.findById(id);
    }

}
