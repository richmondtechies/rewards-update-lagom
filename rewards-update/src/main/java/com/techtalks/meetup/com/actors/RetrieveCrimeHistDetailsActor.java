package com.techtalks.meetup.com.actors;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import com.techtalks.meetup.com.repo.CardOfferRepository;
import com.techtalks.meetup.com.model.CrimeHistoryResponse;
import com.techtalks.meetup.com.model.IndividualDetailMsg;

import lombok.extern.slf4j.Slf4j;
import scala.PartialFunction;
import scala.runtime.BoxedUnit;

import java.util.Random;

/**
 * Created by tki214 on 3/7/17.
 */

@Slf4j
public class RetrieveCrimeHistDetailsActor extends AbstractActor {

    private final CardOfferRepository repository;

    public RetrieveCrimeHistDetailsActor(CardOfferRepository repository) {
        this.repository = repository;
        receive(initialReceive());
    }
    private PartialFunction<Object, BoxedUnit> initialReceive() {
        return ReceiveBuilder.match(IndividualDetailMsg.class, this::processRetrieveCrimeHistDetails)
                .build();
    }

    private void processRetrieveCrimeHistDetails(IndividualDetailMsg msg){

        String crimeHistStatus = "good";
        if (new Random().nextInt(20) <2){
            crimeHistStatus = "bad";
        }else {
            crimeHistStatus = "good";
        }

        CrimeHistoryResponse crimeHistoryResponse = new CrimeHistoryResponse(crimeHistStatus);
        sender().
                tell(crimeHistoryResponse, self());
    }


    public static Props props(CardOfferRepository repository) {
        return Props.create(RetrieveCrimeHistDetailsActor.class, () -> new RetrieveCrimeHistDetailsActor(repository));
    }

}