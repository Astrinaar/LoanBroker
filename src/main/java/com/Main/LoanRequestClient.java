package com.Main;

import com.Client.Client.IOException_Exception;
import com.Client.Client.Message;
import com.Client.Client.MessageImplService;

public class LoanRequestClient {
    public static void main(String[] argv) {
        MessageImplService sis = new MessageImplService();
        Message si = sis.getMessageImplPort();
        String ssn = "080808-0808";
        try {
            System.out.println("Sending SSN: " + ssn);
            si.loanRequest(ssn, 0, 0);

        } catch (IOException_Exception e) {
            e.printStackTrace();
        }
    }
}
