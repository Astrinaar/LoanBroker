package Main;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL) //optional
public interface Message {
    @WebMethod
    int loanRequest(int ssn, int loanAmount, int loanDurationInMonths);

    @WebMethod
    int getCreditScore(int ssn);

}
