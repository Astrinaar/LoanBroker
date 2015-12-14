package com.Webservice.RuleBaseWebservice;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.io.IOException;
import java.util.List;

@WebService
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL) //optional
public interface RuleBase {
    @WebMethod
    List<String> getRuleBase(int creditScore) throws IOException;
}
