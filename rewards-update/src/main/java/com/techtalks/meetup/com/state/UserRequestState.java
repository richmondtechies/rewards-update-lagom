package com.techtalks.meetup.com.state;

import akka.actor.ActorRef;
import com.techtalks.meetup.com.model.CreditScoreResponse;
import com.techtalks.meetup.com.model.CrimeHistoryResponse;
import com.techtalks.meetup.com.model.LoanHistoryResponse;

/**
 * Created by tki214 on 3/10/17.
 */
public class UserRequestState {
    String name;
    String ssn;
    String dob;
    String ackId;
    ActorRef clientRef;
    CreditScoreResponse creditScoreResponse;
    LoanHistoryResponse loanHistoryResponse;
    CrimeHistoryResponse crimeHistoryResponse;


    public UserRequestState(String name, String ssn, String dob, String ackId, ActorRef clientRef){
        this.name = name;
        this.ssn = ssn;
        this.dob = dob;
        this.ackId = ackId;
        this.clientRef = clientRef;
    }

    public UserRequestState(String name, String ssn, String dob, String ackId, ActorRef clientRef, CreditScoreResponse creditScoreResponse, LoanHistoryResponse loanHistoryResponse, CrimeHistoryResponse crimeHistoryResponse){
        this.name = name;
        this.ssn = ssn;
        this.dob = dob;
        this.ackId = ackId;
        this.clientRef = clientRef;
        this.creditScoreResponse = creditScoreResponse;
        this.loanHistoryResponse = loanHistoryResponse;
        this.crimeHistoryResponse = crimeHistoryResponse;
    }


    public void updateCreditScoreResponse(CreditScoreResponse creditScoreResponse){
        this.creditScoreResponse = creditScoreResponse;
    }

    public void updateLoanHistoryResponse(LoanHistoryResponse loanHistoryResponse){
        this.loanHistoryResponse = loanHistoryResponse;
    }

    public void updateCrimeHistoryResponse(CrimeHistoryResponse crimeHistoryResponse){
        this.crimeHistoryResponse = crimeHistoryResponse;

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

    public String getAckId() {
        return ackId;
    }

    public ActorRef getClientRef() {
        return clientRef;
    }


    public CreditScoreResponse getCreditScoreResponse() {
        return creditScoreResponse;
    }

    public LoanHistoryResponse getLoanHistoryResponse() {
        return loanHistoryResponse;
    }

    public CrimeHistoryResponse getCrimeHistoryResponse() {
        return crimeHistoryResponse;
    }

}
