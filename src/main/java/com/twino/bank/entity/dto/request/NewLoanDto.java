package com.twino.bank.entity.dto.request;

import com.twino.bank.entity.Customer;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class NewLoanDto {

    @NotNull private String ipAddress;
    @NotNull private Customer customer;
    @NotNull @Range(max = 80000)
    private BigDecimal amount;
    @NotNull private LocalDateTime createdDate;
    @NotNull private LocalDateTime term;

    public NewLoanDto() {
        term = LocalDateTime.now();
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getTerm() {
        return term;
    }

    public void setTerm(LocalDateTime term) {
        this.term = term;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
}
