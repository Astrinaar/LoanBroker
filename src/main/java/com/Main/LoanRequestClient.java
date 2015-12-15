package com.Main;

import com.Client.MessageClient.IOException_Exception;
import com.Client.MessageClient.Message;
import com.Client.MessageClient.MessageImplService;
import com.Model.LoanObject;
import com.Model.ReplyObject;

public class LoanRequestClient {
    public static void main(String[] argv) {
        MessageImplService sis = new MessageImplService();
        Message si = sis.getMessageImplPort();
        String ssn = "080878-0808";
        LoanObject loanObject = new LoanObject(ssn,null,"100","36",null);

        try {
            System.out.println(" [x] Sending loanObject");
           ReplyObject replyObject  =  si.loanRequest(ssn, loanObject.getLoanAmount(), loanObject.getLoanDuration());

        } catch (IOException_Exception e) {
            e.printStackTrace();
        }
    }
}
