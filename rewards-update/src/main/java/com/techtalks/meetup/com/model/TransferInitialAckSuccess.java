package com.techtalks.meetup.com.model;

import akka.actor.ActorRef;
import com.techtalks.meetup.com.api.CardOfferRequestMessage;


/**
 * Created by tki214 on 12/7/16.
 */
public class TransferInitialAckSuccess extends InitialRequestAck{

    private  final String ackId;
    private final CardOfferRequestMessage cardOfferMessage;
    private final ActorRef replyTo;

    public String getAckId() {
        return ackId;
    }

    public CardOfferRequestMessage getUpdateAISRegistryMessage() {
        return cardOfferMessage;
    }

    public ActorRef getReplyTo() {
        return replyTo;
    }

    public TransferInitialAckSuccess(String ackId, CardOfferRequestMessage cardOfferMessage, ActorRef replyTo) {
        this.ackId = ackId;
        this.cardOfferMessage = cardOfferMessage;
        this.replyTo = replyTo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){ return true;}
        if (o == null || getClass() != o.getClass()){ return false;}

        TransferInitialAckSuccess that = (TransferInitialAckSuccess) o;

        if (ackId != null ? !ackId.equals(that.ackId) : that.ackId != null) {return false;}
        if (cardOfferMessage != null ? !cardOfferMessage.equals(that.cardOfferMessage) : that.cardOfferMessage != null)
        {
            return false;
        }
        return replyTo != null ? replyTo.equals(that.replyTo) : that.replyTo == null;

    }

    @Override
    public int hashCode() {
        int result = ackId != null ? ackId.hashCode() : 0;
        result = 31 * result + (cardOfferMessage != null ? cardOfferMessage.hashCode() : 0);
        result = 31 * result + (replyTo != null ? replyTo.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "InitialRequestAckSuccess{" +
                "ackId='" + ackId + '\'' +
                ", cardOfferMessage=" + cardOfferMessage +
                ", replyTo=" + replyTo +
                '}';
    }
}
