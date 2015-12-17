package com.Main;

import com.Client.MessageClient.IOException_Exception;
import com.Client.MessageClient.Message;
import com.Client.MessageClient.MessageImplService;
import com.Client.MessageClient.ReplyObject;
import com.Model.LoanObject;
import java.util.Random;

public class LoanRequestClient {

    public static void main(String[] argv) {
        MessageImplService sis = new MessageImplService();
        Message si = sis.getMessageImplPort();
        Random random = new Random();
        //String ssn = "190909-1212";
        String ssn = "" + (random.nextInt(100000) + 100000) + "-" + (random.nextInt(9000) + 1000);
        LoanObject loanObject = new LoanObject(ssn, null, "100", "36", null);

        try {
            System.out.println(" [x] Sending loanObject");
            ReplyObject replyObject = si.loanRequest(ssn, loanObject.getLoanAmount(), loanObject.getLoanDuration());
            System.out.println(replyObject.toString());

        } catch (IOException_Exception e) {
            e.printStackTrace();
        }
    }
}
