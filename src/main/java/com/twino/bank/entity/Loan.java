package com.twino.bank.entity;

import com.twino.bank.entity.dto.request.NewLoanDto;
import com.twino.bank.enums.LoanStatus;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Range(max =80000)
    private BigDecimal amount;

    @ManyToOne
    private Customer customer;

    private LocalDateTime createdDate;
    private LocalDateTime term;
    private LoanStatus status;
    private String ipAddress;
    private boolean canProlongTerm;

    public Loan() {

    }

    public Loan(Long id,Customer customer, String ipAddress){
        this.id = id;
        this.customer = customer;
        this.ipAddress = ipAddress;
    }
    public Loan(LocalDateTime createdDate, BigDecimal amount){
        this.createdDate = createdDate;
        this.amount = amount;
    }

    public Loan(NewLoanDto dto){
        this.customer = dto.getCustomer();
        this.amount = dto.getAmount();
        this.ipAddress = dto.getIpAddress();
        this.term = dto.getTerm();
        this.createdDate = dto.getCreatedDate();
        this.canProlongTerm = true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public LocalDateTime getTerm() {
        return term;
    }

    public void setTerm(LocalDateTime term) {
        this.term = term;
    }

    public LoanStatus getStatus() {
        return status;
    }

    public void setStatus(LoanStatus status) {
        this.status = status;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public boolean isCanProlongTerm() {
        return canProlongTerm;
    }

    public void setCanProlongTerm(boolean canProlongTerm) {
        this.canProlongTerm = canProlongTerm;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Loan loan = (Loan) o;
        return canProlongTerm == loan.canProlongTerm &&
                Objects.equals(id, loan.id) &&
                Objects.equals(amount, loan.amount) &&
                Objects.equals(customer, loan.customer) &&
                Objects.equals(createdDate, loan.createdDate) &&
                Objects.equals(term, loan.term) &&
                status == loan.status &&
                Objects.equals(ipAddress, loan.ipAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, customer, createdDate, term, status, ipAddress, canProlongTerm);
    }
}
