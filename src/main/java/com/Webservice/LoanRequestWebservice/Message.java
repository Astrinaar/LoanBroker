package com.Webservice.LoanRequestWebservice;

import com.Model.ReplyObject;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.io.IOException;

@WebService
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL) //optional
public interface Message {
    @WebMethod
    ReplyObject loanRequest(String ssn, String loanAmount, String loanDurationInMonths) throws IOException;
}
