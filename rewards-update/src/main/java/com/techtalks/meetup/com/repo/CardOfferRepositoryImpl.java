package com.techtalks.meetup.com.repo;

import com.techtalks.meetup.com.api.CardOfferRequestMessage;
import lombok.extern.slf4j.Slf4j;
import play.db.Database;

import javax.inject.Inject;
import java.sql.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Created by tki214 on 12/7/16.
 */
@Slf4j
public class CardOfferRepositoryImpl implements CardOfferRepository {
    private Database db;
    private static ExecutorService service = Executors.newCachedThreadPool();
    //Adding an empty string to handle sensitive data for 2/10 release
    public static final String empty_Status_details = "";


    @Inject
    public CardOfferRepositoryImpl(Database db) {
        this.db = db;
        try{

            Connection con = db.getConnection();

            Statement stmnt = con.createStatement();
            String sql = "create table if NOT EXISTS CREDIT_CARD_OFFER_DETAILS (REQUEST_ID varchar (40) not NULL , " +
                    "CARDHOLDER_NAME varchar (40), SSN varchar (40), DOB varchar (40), CREDIT_SCORE INTEGER, " +
                    "LOAN_HIST varchar (40), CRIME_HIST varchar (40), " +
                    "approval_status varchar(40), card_offered varchar(40), OFFER_DETAILS varchar(200), STATUS varchar (40) , " +
                    "PRIMARY KEY(REQUEST_ID)"+ ")";

            stmnt.execute(sql);
            stmnt.close();
            con.close();

        }catch(Exception ex){
            log.error("Error while creating connection::"+ex.getMessage());
        }
    }
    
    public void retrieveFailureRecords(){
    	
    }



    @Override
    public CompletableFuture<InitialInsert> initialInsert(CardOfferRequestMessage message, String requestId){



         return CompletableFuture.supplyAsync( () -> {
             Connection con = null;
             PreparedStatement ps = null;
             try{
                 con = db.getConnection();
                 String insertTableSQL = "INSERT INTO CREDIT_CARD_OFFER_DETAILS"
                         + "(REQUEST_ID, CARDHOLDER_NAME, SSN, DOB, STATUS) VALUES"
                         + "(?,?,?,?,?)";

                 Timestamp ts = new Timestamp(System.currentTimeMillis());

                 log.info("initialInsert  SQL IS:"+insertTableSQL);

                 ps = con.prepareStatement(insertTableSQL);
                 ps.setString(1, requestId);
                 ps.setString(2, message.getName());
                 ps.setString(3, message.getSsn());
                 ps.setString(4, message.getDob());
                 ps.setString(5, "INITIAL_INSERT");
                 ps.executeUpdate();


                 return new SuccessfulInsert(requestId);

             }catch (Exception e){
                 log.error("Error while initialInsert::"+e.getMessage());
                 return new FailedInsert(e.getMessage());
             }finally {
                 try {
                     ps.close();
                     con.close();

                 } catch (SQLException e) {
                     log.error("Error while closing connection object::"+e.getMessage());
                 }
             }
         },service);



    }


    @Override
    public CompletableFuture<String> successfulUpdate(String requestId, int creditScore, String loanStatus,
                                                      String crimeStatus, String approvalStatus, String cardOffered, String offerDetails) {
        return CompletableFuture.supplyAsync( () -> {
            Connection con = null;
            PreparedStatement ps = null;
            try{
                con = db.getConnection();
                String sqlUpdate ="update CREDIT_CARD_OFFER_DETAILS set status = ?,  credit_score = ?, loan_hist = ? , crime_hist = ?, " +
                        "approval_status = ?, card_offered = ?, offer_details = ? where request_id =?" ;


                Timestamp ts = new Timestamp(System.currentTimeMillis());

                log.info("successfulUpdate UPDATE SQL IS:"+sqlUpdate);

                ps = con.prepareStatement(sqlUpdate);
                ps.setString(1, "COMPLETED");
                ps.setInt(2, creditScore);
                ps.setString(3, loanStatus);
                ps.setString(4, crimeStatus);
                ps.setString(5, approvalStatus);
                ps.setString(6, cardOffered);
                ps.setString(7, offerDetails);
                ps.setString(8, requestId);
                ps.executeUpdate();


                return "SUCCESS";

            }catch (Exception e){
                log.error("error while updating successful :"+e.getMessage());
                return"FAILURE";
            }
            finally {
                try {
                    ps.close();
                    con.close();

                } catch (SQLException e) {
                    log.error("Error while closing connection object::"+e.getMessage());
                }
            }
        },service);
    }


}
