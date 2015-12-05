package com.Webservice.LoanRequestWebservice;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.io.IOException;

@WebService
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL) //optional
public interface Message {
    @WebMethod
    void loanRequest(String ssn, int loanAmount, int loanDurationInMonths) throws IOException;
}
