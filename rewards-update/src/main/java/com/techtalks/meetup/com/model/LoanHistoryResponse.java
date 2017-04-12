package com.techtalks.meetup.com.model;

/**
 * Created by tki214 on 3/11/17.
 */
public class LoanHistoryResponse {

    String loanStatus;

    public LoanHistoryResponse(String loanStatus) {
        this.loanStatus = loanStatus;
    }

    public String getLoanStatus() {
        return loanStatus;
    }

    public void setLoanStatus(String loanStatus) {
        this.loanStatus = loanStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LoanHistoryResponse that = (LoanHistoryResponse) o;

        return loanStatus.equals(that.loanStatus);

    }

    @Override
    public int hashCode() {
        return loanStatus.hashCode();
    }
}
