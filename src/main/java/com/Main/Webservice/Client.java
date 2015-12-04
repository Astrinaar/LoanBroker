package com.Main.Webservice;

import com.Main.Client.*;

public class Client {
    public static void main(String[] argv) {
      MessageImplService sis = new MessageImplService();
      		com.Main.Client.Message si = sis.getMessageImplPort();
        int ssn = 1;
        try {
            si.loanRequest(ssn,0,0);
        } catch (IOException_Exception e) {
            e.printStackTrace();
        }


    }
}
