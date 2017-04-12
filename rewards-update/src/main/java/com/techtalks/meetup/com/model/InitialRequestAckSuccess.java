package com.techtalks.meetup.com.model;

import akka.actor.ActorRef;

import com.techtalks.meetup.com.api.CardOfferRequestMessage;

/**
 * Created by tki214 on 12/7/16.
 */
public class InitialRequestAckSuccess extends InitialRequestAck{

    private  final String ackId;
    private final CardOfferRequestMessage cardOfferRequestMessage;
    private final ActorRef replyTo;

    public String getAckId() {
        return ackId;
    }

    public CardOfferRequestMessage getCardOffeRequestMessage() {
        return cardOfferRequestMessage;
    }

    public ActorRef getReplyTo() {
        return replyTo;
    }

    public InitialRequestAckSuccess(String ackId, CardOfferRequestMessage cardOfferRequestMessage, ActorRef replyTo) {
        this.ackId = ackId;
        this.cardOfferRequestMessage = cardOfferRequestMessage;
        this.replyTo = replyTo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){ return true;}
        if (o == null || getClass() != o.getClass()){ return false;}

        InitialRequestAckSuccess that = (InitialRequestAckSuccess) o;

        if (ackId != null ? !ackId.equals(that.ackId) : that.ackId != null) {return false;}
        if (cardOfferRequestMessage != null ? !cardOfferRequestMessage.equals(that.cardOfferRequestMessage) : that.cardOfferRequestMessage != null)
        {
            return false;
        }
        return replyTo != null ? replyTo.equals(that.replyTo) : that.replyTo == null;

    }

    @Override
    public int hashCode() {
        int result = ackId != null ? ackId.hashCode() : 0;
        result = 31 * result + (cardOfferRequestMessage != null ? cardOfferRequestMessage.hashCode() : 0);
        result = 31 * result + (replyTo != null ? replyTo.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "InitialRequestAckSuccess{" +
                "ackId='" + ackId + '\'' +
                ", cardOfferRequestMessage=" + cardOfferRequestMessage +
                ", replyTo=" + replyTo +
                '}';
    }
}
