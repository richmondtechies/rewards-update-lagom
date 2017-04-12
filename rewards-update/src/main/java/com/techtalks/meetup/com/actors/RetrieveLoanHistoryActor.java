package com.techtalks.meetup.com.actors;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import com.techtalks.meetup.com.repo.CardOfferRepository;
import com.techtalks.meetup.com.model.IndividualDetailMsg;

import com.techtalks.meetup.com.model.LoanHistoryResponse;
import lombok.extern.slf4j.Slf4j;
import scala.PartialFunction;
import scala.runtime.BoxedUnit;

import java.util.Random;

/**
 * Created by tki214 on 3/7/17.
 */

@Slf4j
public class RetrieveLoanHistoryActor extends AbstractActor {

    private final CardOfferRepository repository;

    public RetrieveLoanHistoryActor(CardOfferRepository repository) {
        this.repository = repository;
        receive(initialReceive());
    }
    private PartialFunction<Object, BoxedUnit> initialReceive() {
        return ReceiveBuilder.match(IndividualDetailMsg.class, this::processLoanHistory)
                .build();
    }

    private void processLoanHistory(IndividualDetailMsg msg){

        String loanHistStatus = "good";
        if (new Random().nextInt(20) <2){
            loanHistStatus = "bad";
        }else {
            loanHistStatus = "good";
        }

        LoanHistoryResponse loanHistoryResponse = new LoanHistoryResponse(loanHistStatus);
        sender().
                tell(loanHistoryResponse, self());
    }


    public static Props props(CardOfferRepository repository) {
        return Props.create(RetrieveLoanHistoryActor.class, () -> new RetrieveLoanHistoryActor(repository));
    }

}
