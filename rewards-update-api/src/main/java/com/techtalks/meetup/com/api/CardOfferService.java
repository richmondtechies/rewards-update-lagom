/*
 *
 */
package com.techtalks.meetup.com.api;

import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.ServiceCall;

import static com.lightbend.lagom.javadsl.api.Service.named;
import static com.lightbend.lagom.javadsl.api.Service.*;

/**
 * The hello service interface.
 * <p>
 * This describes everything that Lagom needs to know about how to serve and
 * consume the HelloService.
 */
public interface CardOfferService extends Service {

    @Override
    default Descriptor descriptor() {
        //updateAISRegistry
        // @formatter:off
        return named("cardOfferRequestMessage").withCalls(
                pathCall("/api/card/offer", this::cardOffers)
        ).withAutoAcl(true);
        // @formatter:on
    }

    ServiceCall<CardOfferRequestMessage, String> cardOffers();
}
