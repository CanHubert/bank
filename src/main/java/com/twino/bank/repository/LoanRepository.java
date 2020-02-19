package com.twino.bank.repository;

import com.twino.bank.entity.Customer;
import com.twino.bank.entity.Loan;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class LoanRepository implements ILoanRepository{

    private Set<Loan> loans;

    private AtomicLong atomicLong;

    public LoanRepository(){
        this.loans = new HashSet<>();
        this.atomicLong = new AtomicLong(0);
    }
    @Override
    public void save(Loan loan) {
        if(loan.getId() == null)
        {
            loan.setId(atomicLong.incrementAndGet());
        }
        else
        {
            loans.removeIf(loan1 -> loan1.getId().equals(loan.getId()));
        }
        this.loans.add(loan);
    }

    @Override
    public long countByCustomerAndIpAddress(Customer customer, String ipAddress) {
        return this.loans.stream().filter( loan -> loan.getCustomer().getId().equals(customer.getId())
                && loan.getIpAddress().equals(ipAddress)).count();
    }

    @Override
    public Optional<Loan> findById(Long id){
        return loans.stream().filter(loan -> loan.getId().equals(id)).findAny();
    }
}
