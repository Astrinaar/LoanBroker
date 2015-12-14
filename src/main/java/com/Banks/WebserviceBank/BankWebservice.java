package com.Banks.WebserviceBank;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.io.IOException;

@WebService
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL) //optional
public interface BankWebservice {
    @WebMethod
    void getLoanRequest(String ssn, String creditScore, String loanAmount, String loanDuration) throws IOException;
}
