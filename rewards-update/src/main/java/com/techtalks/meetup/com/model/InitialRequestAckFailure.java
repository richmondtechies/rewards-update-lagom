package com.techtalks.meetup.com.model;

import akka.actor.ActorRef;

/**
 * Created by tki214 on 12/7/16.
 */
public class InitialRequestAckFailure extends InitialRequestAck {

    private final String message;
    private ActorRef replyTo;

    public String getMessage() {
        return message;
    }

    public ActorRef getReplyTo() {
        return replyTo;
    }

    public InitialRequestAckFailure(String message, ActorRef replyTo) {
        this.message = message;
        this.replyTo = replyTo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()){ return false;}

        InitialRequestAckFailure that = (InitialRequestAckFailure) o;

        return message != null ? message.equals(that.message) : that.message == null;

    }

    @Override
    public int hashCode() {
        return message != null ? message.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "InitialRequestAckFailure{" +
                "message='" + message + '\'' +
                '}';
    }
}
