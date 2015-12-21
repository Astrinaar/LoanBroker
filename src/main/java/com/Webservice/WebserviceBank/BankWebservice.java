package com.Webservice.WebserviceBank;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.io.IOException;
import java.math.BigDecimal;

@WebService
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL) //optional
public interface BankWebservice {
    @WebMethod
    BigDecimal getInterestRate(String ssn, String creditScore, String loanAmount, String loanDuration) throws IOException;
}
