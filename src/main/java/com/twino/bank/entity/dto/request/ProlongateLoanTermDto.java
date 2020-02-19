package com.twino.bank.entity.dto.request;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

public class ProlongateLoanTermDto {

    @NotNull
    private long loanId;
    @NotNull @Range(max = 14)
    private long prolongateTermForDays;

    public ProlongateLoanTermDto() {
    }

    public ProlongateLoanTermDto(long loanId, int prolongateTermForDays) {
        this.loanId = loanId;
        this.prolongateTermForDays = prolongateTermForDays;
    }

    public long getLoanId() {
        return loanId;
    }

    public void setLoanId(long loanId) {
        this.loanId = loanId;
    }

    public long getProlongateTermForDays() {
        return prolongateTermForDays;
    }

    public void setProlongateTermForDays(long prolongateTermForDays) {
        this.prolongateTermForDays = prolongateTermForDays;
    }
}
