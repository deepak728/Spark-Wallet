package com.Service;

import com.DAO.SparkDAO;
import com.DAO.SparkTrans;
import com.DO.BankEntity;
import com.DO.spark_entity;
import com.Exception.CustomException;
import com.api.DTO.request.DTO3;
import com.api.DTO.request.DTO4;
import com.api.DTO.response.DTO5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class SparkTransServiceImpl implements SparkTransService {
    @Autowired
    SparkTrans sparkTrans;
    @Autowired
    SparkDAO sparkDAO;
    @Autowired
    SparkServiceImpl sparkServiceImpl;


    @Override
    public DTO3 addMoney(String token, float amount) throws CustomException {
        String ph;
        try{
            ph =sparkServiceImpl.authenticateUser(token);
        } catch (Exception e){
              throw new CustomException("Please Login Again",HttpStatus.UNAUTHORIZED);
        }

        BankEntity be;
        spark_entity se;
         try{
              be=sparkTrans.getBankDetail(ph);
              se=sparkDAO.getUserDetails(ph);


         }catch (Exception e ){
             throw new CustomException("Internal Error",HttpStatus.BAD_REQUEST);
         }

             float bank_money=be.getBalance();
             if(bank_money>=amount&&amount>=0){
                 float newWB=se.getWallet()+amount;
                 float newBB=bank_money-amount;

                 float updatedWalletBalance=sparkTrans.updateBalance(ph,newBB,newWB);
                 DTO3 dto3=new DTO3(updatedWalletBalance);
                 return dto3;
             }else {
                 System.out.println("Insufficient balanace");
                 throw new CustomException("Insufficient Balance ",HttpStatus.NOT_ACCEPTABLE);
             }



    }

    @Override
    public DTO5 sendMoney(String token, DTO4 dto4) throws CustomException{
        String ph;
        try{
            ph=sparkServiceImpl.authenticateUser(token);
        }catch (Exception e){
            throw new CustomException("Please Login Again",HttpStatus.UNAUTHORIZED);
        }
        spark_entity sender;
        spark_entity receiver;
        BankEntity senderBank;
        BankEntity receiverBank;
             try{
                  sender=(sparkDAO.getUserDetails(ph));
                  receiver=(sparkDAO.getUserDetails(dto4.getReceiverPh()));
                  senderBank=sparkTrans.getBankDetail(ph);
                  receiverBank=sparkTrans.getBankDetail(dto4.getReceiverPh());

             }catch (Exception e){
            throw new CustomException("Sender or Receiver does not exist",HttpStatus.BAD_REQUEST);

             }

           if (sender.getId()>0&&receiver.getId()>0){


                float senderBalance=sender.getWallet();
                if(senderBalance>=dto4.getAmount()){

                    float updatedSenderBalance=sparkTrans.updateBalance(ph,senderBank.getBalance(),sender.getWallet()-dto4.getAmount());
                    float updatedReceiverBallence=sparkTrans.updateBalance(dto4.getReceiverPh(),receiverBank.getBalance(),receiver.getWallet()+dto4.getAmount());



                    DTO5 dto5 =new DTO5(updatedSenderBalance,updatedReceiverBallence);
                    return dto5;



                }else{
                    if(senderBank.getBalance()>=dto4.getAmount()-sender.getWallet()){
                        sparkTrans.updateBalance(ph,(senderBank.getBalance()-(dto4.getAmount()-sender.getWallet())),dto4.getAmount());
                        System.out.println("Adding "+((dto4.getAmount()-sender.getWallet()))+" from your bank to wallet");
                        DTO5 dto_5=sendMoney(token,dto4);
                        return dto_5;




                    }else {
                        throw new CustomException("You don't have sufficient Balance, Please add "+(dto4.getAmount()-senderBank.getBalance())+ " to your bank",HttpStatus.NOT_ACCEPTABLE);

                    }

                }


            }else {
                throw new CustomException("Receiver Does not exist ",HttpStatus.UNAUTHORIZED);
            }



    }
}
