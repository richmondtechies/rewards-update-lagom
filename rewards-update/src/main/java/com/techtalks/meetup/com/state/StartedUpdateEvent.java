package com.techtalks.meetup.com.state;

/**
 * Created by tki214 on 4/9/17.
 */
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.lightbend.lagom.serialization.Jsonable;
import com.techtalks.meetup.com.api.CardOfferRequestMessage;

import javax.annotation.concurrent.Immutable;

/**
 * Created by awg049 on 11/22/16.
 */
@SuppressWarnings("serial")
@Immutable
@JsonDeserialize
public class StartedUpdateEvent implements Jsonable{

    final public CardOfferRequestMessage updateAISRegistryMessage;
    @JsonCreator
    public StartedUpdateEvent(CardOfferRequestMessage updateAISRegistryMessage) {
        this.updateAISRegistryMessage = updateAISRegistryMessage;
    }

    @Override
    public String toString() {
        return "StartedUpdateEvent{" +
                "updateAISRegistryMessage=" + updateAISRegistryMessage +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StartedUpdateEvent)) return false;

        StartedUpdateEvent that = (StartedUpdateEvent) o;

        return updateAISRegistryMessage != null ? updateAISRegistryMessage.equals(that.updateAISRegistryMessage) : that.updateAISRegistryMessage == null;

    }

    @Override
    public int hashCode() {
        return updateAISRegistryMessage != null ? updateAISRegistryMessage.hashCode() : 0;
    }
}
