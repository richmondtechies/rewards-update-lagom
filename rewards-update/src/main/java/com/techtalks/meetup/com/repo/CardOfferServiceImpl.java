package com.techtalks.meetup.com.repo;


import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.japi.Pair;
import akka.util.Timeout;

import com.techtalks.meetup.com.actors.ProcessUserRequestsActor;
import com.techtalks.meetup.com.api.CardOfferRequestMessage;
import com.techtalks.meetup.com.api.CardOfferService;

import com.lightbend.lagom.javadsl.api.transport.ResponseHeader;
import com.lightbend.lagom.javadsl.server.HeaderServiceCall;
import lombok.extern.slf4j.Slf4j;

import play.api.inject.Injector;
import play.libs.ws.WSClient;
import scala.compat.java8.FutureConverters;
import scala.concurrent.duration.Duration;

import javax.inject.Inject;

import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

import static akka.pattern.Patterns.ask;

/**
 * Created by tki214 on 11/22/16.
 */
@Slf4j
public class CardOfferServiceImpl implements CardOfferService{
    private final ActorSystem actorSystem;
    private final CardOfferRepository repository;

    @Inject
    public CardOfferServiceImpl(Injector injector, ActorSystem actorSystem, WSClient client,
                                CardOfferRepository repository) {

        this.actorSystem = actorSystem;
        this.repository = repository;
    }


    @Override
    public HeaderServiceCall<CardOfferRequestMessage, String> cardOffers() {
        final Timeout timeout = new Timeout(Duration.create(10, TimeUnit.SECONDS));
        return (request, message) -> {
                ActorRef ref = actorSystem
                        .actorOf(Props.create(ProcessUserRequestsActor.class, () -> new ProcessUserRequestsActor(repository)));

                CompletionStage<Object> res = FutureConverters.toJava(ask(ref, message, timeout));
                ResponseHeader responseHeader = ResponseHeader.OK.withStatus(202);

                return res.thenApply(x -> Pair.create(responseHeader, x.toString()));
        };
    }

}
