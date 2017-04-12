package com.techtalks.meetup.com.model;

/**
 * Created by tki214 on 3/11/17.
 */
public class CrimeHistoryResponse {

    String crimeHistStatus;

    public CrimeHistoryResponse(String crimeHistStatus) {
        this.crimeHistStatus = crimeHistStatus;
    }

    public String getCrimeHistStatus() {
        return crimeHistStatus;
    }

    public void setCrimeHistStatus(String crimeHistStatus) {
        this.crimeHistStatus = crimeHistStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CrimeHistoryResponse that = (CrimeHistoryResponse) o;

        return crimeHistStatus.equals(that.crimeHistStatus);

    }

    @Override
    public int hashCode() {
        return crimeHistStatus.hashCode();
    }
}
