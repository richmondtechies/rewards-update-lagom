package com.techtalks.meetup.com.repo;

import com.techtalks.meetup.com.api.CardOfferService;
import com.techtalks.meetup.com.servicelocator.ServiceLocatorImpl;
import com.google.inject.AbstractModule;
import com.lightbend.lagom.javadsl.api.ServiceLocator;
import com.lightbend.lagom.javadsl.server.ServiceGuiceSupport;
import play.Configuration;
import play.Environment;

/**
 * Created by tki214 on 3/8/17.
 */
public class CardOfferServiceModule extends AbstractModule implements ServiceGuiceSupport {

    private final Environment environment;
    private final Configuration configuration;

    public CardOfferServiceModule(Environment environment, Configuration configuration) {
        this.environment=environment;
        this.configuration=configuration;
    }

    @Override
    protected void configure() {
        bind(CardOfferRepository.class).to(CardOfferRepositoryImpl.class);
        bindServices(serviceBinding(CardOfferService.class, CardOfferServiceImpl.class));
        bind(ServiceLocator.class).to(ServiceLocatorImpl.class);
    }
}