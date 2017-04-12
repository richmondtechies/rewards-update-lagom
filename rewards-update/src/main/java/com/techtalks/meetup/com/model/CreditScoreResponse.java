package com.techtalks.meetup.com.model;

/**
 * Created by tki214 on 3/10/17.
 */
public class CreditScoreResponse {
    int creditScore;


    public CreditScoreResponse(int creditScore) {
        this.creditScore = creditScore;
    }

    public int getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(int creditScore) {
        this.creditScore = creditScore;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CreditScoreResponse that = (CreditScoreResponse) o;

        return creditScore == that.creditScore;

    }

    @Override
    public int hashCode() {
        return creditScore;
    }
}
