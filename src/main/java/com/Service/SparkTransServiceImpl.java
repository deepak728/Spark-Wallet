package com.Service;

import com.DAO.SparkDAO;
import com.DAO.SparkTrans;
import com.DO.BankEntity;
import com.DO.SparkEntity;
import com.api.DTO.request.DTO3;
import com.api.DTO.request.DTO4;
import com.api.DTO.response.DTO5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SparkTransServiceImpl implements SparkTransService {
    @Autowired
    SparkTrans sparkTrans;
    @Autowired
    SparkDAO sparkDAO;


    @Override
    public DTO3 addMoney(String ph, float amount) {
        BankEntity be=sparkTrans.getBankDetail(ph);
        SparkEntity se=sparkDAO.getUserDetails(ph);
        float bank_money=be.getBalance();
        if(bank_money>=amount&&amount>=0){
            float newWB=se.getWallet()+amount;
            float newBB=bank_money-amount;

            float updatedWalletBalance=sparkTrans.updateBalance(ph,newBB,newWB);
            DTO3 dto3=new DTO3(updatedWalletBalance);
            return dto3;
        }else {
            System.out.println("Insufficient Balance");
            DTO3 dto3=new DTO3(se.getWallet());
            return dto3;
        }
    }

    @Override
    public DTO5 sendMoney(String ph, DTO4 dto4) {

           SparkEntity sender=(sparkDAO.getUserDetails(ph));
           SparkEntity receiver=(sparkDAO.getUserDetails(dto4.getReceiverPh()));
        BankEntity senderBank=sparkTrans.getBankDetail(ph);
        BankEntity receiverBank=sparkTrans.getBankDetail(dto4.getReceiverPh());

           if(sparkDAO.getUserId(ph)>0&&sparkDAO.getUserId(dto4.getReceiverPh())>0){


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
                       DTO5 dto_5=this.sendMoney(ph,dto4);
                       return dto_5;




                   }else {
                       System.out.println("You don't have sufficient Balance, Please add "+(dto4.getAmount()-senderBank.getBalance())+ " to your bank");

                   }
                   DTO5 dto5=new DTO5(sparkTrans.updateBalance(ph,senderBank.getBalance(),sender.getWallet()),sparkTrans.updateBalance(dto4.getReceiverPh(),receiverBank.getBalance(),receiver.getWallet()));
                   return dto5;
               }


           }else {
               System.out.print("Sender or Receiver doesn't exist");
               DTO5 dto5=new DTO5(sparkTrans.updateBalance(ph,senderBank.getBalance(),sender.getWallet()),sparkTrans.updateBalance(dto4.getReceiverPh(),receiverBank.getBalance(),receiver.getWallet()));
               return dto5;
           }

    }
}
