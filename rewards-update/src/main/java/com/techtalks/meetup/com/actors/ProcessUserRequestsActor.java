package com.techtalks.meetup.com.actors;

import akka.actor.*;
import akka.japi.pf.DeciderBuilder;
import akka.japi.pf.ReceiveBuilder;

import com.techtalks.meetup.com.api.CardOfferRequestMessage;
import com.techtalks.meetup.com.model.*;
import com.techtalks.meetup.com.repo.CardOfferRepository;

import com.techtalks.meetup.com.state.UserRequestState;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import scala.PartialFunction;
import scala.compat.java8.FutureConverters;

import scala.runtime.BoxedUnit;

import java.util.*;
import java.util.concurrent.CompletableFuture;


import static akka.pattern.Patterns.pipe;


/**
 * Created by tki214 on 03/7/17.
 */
@Slf4j
public class ProcessUserRequestsActor extends AbstractActor {
    final Config config = ConfigFactory.load();
    private final CardOfferRepository repository;
    private  String ackId;
    private UserRequestState state;


    public ProcessUserRequestsActor(CardOfferRepository repository) {
        this.repository = repository;
        this.ackId = UUID.randomUUID().toString();
        receive(initialReceive());
    }


    private PartialFunction<Object, BoxedUnit> initialReceive() {

        return ReceiveBuilder.match(CardOfferRequestMessage.class, this::cardOfferMessageHandler)
                .match(InitialRequestAckFailure.class, initialRequestAck -> initialRequestAck.getReplyTo().tell("Failure happened while persisting initial record.", self()))
                .match(TransferInitialAckSuccess.class, this::initialRequestAckSuccess)
                .match(CreditScoreResponse.class, this::processCreditScoreResponse)
                .match(LoanHistoryResponse.class, this::processLoanHistoryResponse)
                .match(CrimeHistoryResponse.class, this::processCrimeHistoryResponse)
                .build();
    }


    private void cardOfferMessageHandler(CardOfferRequestMessage startUpdate) {

        ActorRef client = sender();
        this.state = new UserRequestState(startUpdate.getName(),startUpdate.getSsn(),startUpdate.getDob(), this.ackId, client);
        CompletableFuture<InitialRequestAck> initialRequestAckCompletableFuture =
                    repository.initialInsert(startUpdate, this.ackId)
                            .thenApply(initialInsert -> {
                                if (initialInsert instanceof CardOfferRepository.SuccessfulInsert) {
                                    return new TransferInitialAckSuccess(initialInsert.getResult(), startUpdate, client);
                                } else {
                                    return new InitialRequestAckFailure(initialInsert.getResult(), client);
                                }
                            });
      //pipe initial insert status back to self - initialReceive method.
       pipe(
                FutureConverters.toScala(
                        initialRequestAckCompletableFuture),
                context().dispatcher()).to(self());

    }

    private void initialRequestAckSuccess(TransferInitialAckSuccess initialRequestAck) {

        IndividualDetailMsg individualDetailMsg = new IndividualDetailMsg(this.state.getSsn(), this.state.getDob(),this.state.getName());

        /*ActorRef creditScoreActor = context().actorOf(RetrieveCreditScoreActor.props(repository));
        creditScoreActor.tell(individualDetailMsg, self());


        ActorRef retrieveLoanHistoryActor = context().actorOf(RetrieveLoanHistoryActor.props(repository));
        retrieveLoanHistoryActor.tell(individualDetailMsg, self());

        ActorRef retrieveCrimeHistDetails = context().actorOf(RetrieveCrimeHistDetailsActor.props(repository));
        retrieveCrimeHistDetails.tell(individualDetailMsg, self()); */

        ActorRef creditScoreActor = GetActorRef.getCreditScoreActorRef(context(), repository);
        log.info("Single Instance crditScoreActor:"+creditScoreActor);
        creditScoreActor.tell(individualDetailMsg, self());
        ActorRef retrieveLoanHistoryActor = GetActorRef.getLoanHistActorRef(context(), repository);
        log.info("Single Instance retrieveLoanHistoryActor:"+retrieveLoanHistoryActor);
        retrieveLoanHistoryActor.tell(individualDetailMsg, self());
        ActorRef retrieveCrimeHistDetails = GetActorRef.getCrimeHistActorRef(context(), repository);
        log.info("Single Instance retrieveCrimeHistDetails:"+retrieveCrimeHistDetails);
        retrieveCrimeHistDetails.tell(individualDetailMsg, self());



    }
    private void processCreditScoreResponse(CreditScoreResponse creditScoreResponse){
        this.state.updateCreditScoreResponse(creditScoreResponse);
        finalizeDecision();

    }

    private void processLoanHistoryResponse(LoanHistoryResponse loanHistoryResponse){
        this.state.updateLoanHistoryResponse(loanHistoryResponse);
        finalizeDecision();
    }

    private void processCrimeHistoryResponse(CrimeHistoryResponse crimeHistoryResponse){
        this.state.updateCrimeHistoryResponse(crimeHistoryResponse);
        finalizeDecision();
    }

    private void finalizeDecision(){
        String approvalStatus = "";
        String offerDetails = "";
        String cardOffered = "";
        if (this.state.getCreditScoreResponse()!=null && this.state.getLoanHistoryResponse() !=null && this.state.getCrimeHistoryResponse() != null){
            if (StringUtils.equals(this.state.getLoanHistoryResponse().getLoanStatus(), "bad")
                    || StringUtils.equals(this.state.getCrimeHistoryResponse().getCrimeHistStatus(), "bad")){

                approvalStatus = "DENIED";
                cardOffered = "NONE";
                offerDetails = "Denied, you will receive an letter with explanation soon..";
            }else {
                offerDetails = "Congrats, you are eligible for::: Venture";
                approvalStatus = "APPROVED";
                if (this.state.getCreditScoreResponse().getCreditScore()>299 && this.state.getCreditScoreResponse().getCreditScore()<=500){
                    cardOffered = "Secured";
                    offerDetails = "Congrats, you are eligible for:::"+cardOffered;
                }
                if (this.state.getCreditScoreResponse().getCreditScore()>500 && this.state.getCreditScoreResponse().getCreditScore()<=700){
                    cardOffered = "Journey";
                    offerDetails = "Congrats, you are eligible for:::"+cardOffered;
                }
                if (this.state.getCreditScoreResponse().getCreditScore()>700 && this.state.getCreditScoreResponse().getCreditScore()<=850){
                    cardOffered = "Venture";
                    offerDetails = "Congrats, you are eligible for:::"+cardOffered;
                }
                System.out.println("State is::"+state+",result::"+offerDetails+",ackId:"+this.state.getAckId());

            }

            repository.successfulUpdate(this.state.getAckId(),this.state.getCreditScoreResponse().getCreditScore(),
                    this.state.getLoanHistoryResponse().getLoanStatus(), this.state.getCrimeHistoryResponse().getCrimeHistStatus(),approvalStatus, cardOffered,  offerDetails);

            this.state.getClientRef().
                    tell(offerDetails, self());
        }
    }



    @Override
    public SupervisorStrategy supervisorStrategy() {
        return new OneForOneStrategy(
                false,
                DeciderBuilder
                        .match(ActorInitializationException.class, e -> {
                            e.printStackTrace();
                            return SupervisorStrategy.stop();
                        })
                        .match(ActorKilledException.class, e -> SupervisorStrategy.stop())
                        .match(RuntimeException.class, e -> {
                            e.printStackTrace();
                            log.info("CHILD ACTOR WILL NOW BE  RESTARTED..,ADDED THRED SLEEP FOR DEMO PUPOSE......");
                            Thread.sleep(5000);
                            return SupervisorStrategy.restart();
                        })
                        .matchAny(e -> SupervisorStrategy.escalate())
                        .build()
        );
    }

}
