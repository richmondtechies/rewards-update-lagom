package com.techtalks.meetup.com.actors;

import akka.actor.ActorContext;
import akka.actor.ActorRef;
import com.techtalks.meetup.com.repo.CardOfferRepository;

/**
 * Created by tki214 on 3/12/17.
 */
public class GetActorRef {

    private static ActorRef retrieveCreditScoreActor;
    private static ActorRef retrieveLoanHistoryActor;
    private static ActorRef retrieveCrimeHistDetailsActor;

    private GetActorRef(){

    }
    public static ActorRef getCreditScoreActorRef(ActorContext actorContext, CardOfferRepository repository){
        if(retrieveCreditScoreActor==null){
            retrieveCreditScoreActor = actorContext.actorOf(RetrieveCreditScoreActor.props(repository));
        }
        return retrieveCreditScoreActor;
    }
    public static ActorRef getLoanHistActorRef(ActorContext actorContext, CardOfferRepository repository){
        if(retrieveLoanHistoryActor==null){
            retrieveLoanHistoryActor = actorContext.actorOf(RetrieveLoanHistoryActor.props(repository));
        }
        return retrieveLoanHistoryActor;

    }
    public static ActorRef getCrimeHistActorRef(ActorContext actorContext, CardOfferRepository repository){
        if(retrieveCrimeHistDetailsActor==null){
            retrieveCrimeHistDetailsActor = actorContext.actorOf(RetrieveCrimeHistDetailsActor.props(repository));
        }
        return retrieveCrimeHistDetailsActor;

    }
}
