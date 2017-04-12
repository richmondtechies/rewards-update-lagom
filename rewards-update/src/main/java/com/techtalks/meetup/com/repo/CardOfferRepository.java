package com.techtalks.meetup.com.repo;

import com.techtalks.meetup.com.api.CardOfferRequestMessage;

import java.util.concurrent.CompletableFuture;

/**
 * Created by tki214 on 12/7/16.
 */
public interface CardOfferRepository {
    public CompletableFuture<InitialInsert> initialInsert(CardOfferRequestMessage message, String ackId);

    public CompletableFuture<String> successfulUpdate(String ackId, int creditScore, String loanStatus, String crimeStatus, String approvalStatus, String cardOffered, String offerDetails);

    public static interface InitialInsert{
        public String getResult();
    }

    public  class SuccessfulInsert implements InitialInsert {

        private final String result;

        public SuccessfulInsert(String result) {
            this.result = result;
        }

        @Override
        public String getResult() {
            return result;
        }
    }


    public  class FailedInsert implements InitialInsert {
        private final String result;

        public FailedInsert(String result) {
            this.result = result;
        }

        @Override
        public String getResult() {
            return result;
        }

    }




}
