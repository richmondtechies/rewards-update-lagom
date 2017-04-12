package com.techtalks.meetup.com.actors;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import com.techtalks.meetup.com.repo.CardOfferRepository;
import com.techtalks.meetup.com.model.CreditScoreResponse;
import com.techtalks.meetup.com.model.IndividualDetailMsg;

import lombok.extern.slf4j.Slf4j;
import scala.PartialFunction;
import scala.runtime.BoxedUnit;

/**
 * Created by tki214 on 3/7/17.
 */
@Slf4j
public class RetrieveCreditScoreActor extends AbstractActor {

    private final CardOfferRepository repository;

    public RetrieveCreditScoreActor(CardOfferRepository repository) {
        this.repository = repository;
        receive(initialReceive());
    }
    private PartialFunction<Object, BoxedUnit> initialReceive() {
        return ReceiveBuilder.match(IndividualDetailMsg.class, this::processMsgFromProcessTransfer)
                .build();
    }

    private void processMsgFromProcessTransfer(IndividualDetailMsg msg){
        int lower = 300;
        int upper = 850;
        int randomNumber = (int) (Math.random() * (upper - lower)) + lower;

        if(msg.getSocial().equals("0")){
            log.info("ABOUT TO THROW RUNTIME EXCEPTION");
            throw new RuntimeException("Test Actor Supervision..");
        }

        CreditScoreResponse msgForParent = new CreditScoreResponse(randomNumber);
        sender().
                tell(msgForParent, self());
    }

    @Override
    public void postRestart(Throwable reason) throws Exception {
        log.info("RetrieveCreditScoreActor >>>>>>>>>>>>> is getting RESTARTED..........");
        super.postRestart(reason);
    }


    public static Props props(CardOfferRepository repository) {
        return Props.create(RetrieveCreditScoreActor.class, () -> new RetrieveCreditScoreActor(repository));
    }

}
