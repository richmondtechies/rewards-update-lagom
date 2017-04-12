package com.techtalks.meetup.com.api;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.annotation.concurrent.Immutable;


/**
 * Created by tki214 on 11/22/16.
 */
@Immutable
@JsonDeserialize
public final class CardOfferRequestMessage {

    private final String name;

    private final String ssn;

    private final String dob;


    public CardOfferRequestMessage(String name, String ssn, String dob) {
        this.name = name;
        this.ssn = ssn;
        this.dob = dob;
    }

    public String getName() {
        return name;
    }

    public String getSsn() {
        return ssn;
    }

    public String getDob() {
        return dob;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CardOfferRequestMessage that = (CardOfferRequestMessage) o;

        if (!name.equals(that.name)) return false;
        if (!ssn.equals(that.ssn)) return false;
        return dob.equals(that.dob);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + ssn.hashCode();
        result = 31 * result + dob.hashCode();
        return result;
    }
}