
package com.Client.RuleBase;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.FaultAction;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.7-b01 
 * Generated source version: 2.2
 * 
 */
@WebService(name = "RuleBase", targetNamespace = "http://RuleBaseWebservice.Webservice.com/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface RuleBase {


    /**
     * 
     * @param arg0
     * @throws IOException_Exception
     */
    @WebMethod
    @RequestWrapper(localName = "getRuleBase", targetNamespace = "http://RuleBaseWebservice.Webservice.com/", className = "com.Client.RuleBase.GetRuleBase")
    @ResponseWrapper(localName = "getRuleBaseResponse", targetNamespace = "http://RuleBaseWebservice.Webservice.com/", className = "com.Client.RuleBase.GetRuleBaseResponse")
    @Action(input = "http://RuleBaseWebservice.Webservice.com/RuleBase/getRuleBaseRequest", output = "http://RuleBaseWebservice.Webservice.com/RuleBase/getRuleBaseResponse", fault = {
        @FaultAction(className = IOException_Exception.class, value = "http://RuleBaseWebservice.Webservice.com/RuleBase/getRuleBase/Fault/IOException")
    })
    public void getRuleBase(
        @WebParam(name = "arg0", targetNamespace = "")
        int arg0)
        throws IOException_Exception
    ;

}