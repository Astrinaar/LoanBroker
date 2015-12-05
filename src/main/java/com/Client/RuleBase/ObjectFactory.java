
package com.Client.RuleBase;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.Client.RuleBase package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetRuleBase_QNAME = new QName("http://RuleBaseWebservice.Webservice.com/", "getRuleBase");
    private final static QName _IOException_QNAME = new QName("http://RuleBaseWebservice.Webservice.com/", "IOException");
    private final static QName _GetRuleBaseResponse_QNAME = new QName("http://RuleBaseWebservice.Webservice.com/", "getRuleBaseResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.Client.RuleBase
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetRuleBase }
     * 
     */
    public GetRuleBase createGetRuleBase() {
        return new GetRuleBase();
    }

    /**
     * Create an instance of {@link IOException }
     * 
     */
    public IOException createIOException() {
        return new IOException();
    }

    /**
     * Create an instance of {@link GetRuleBaseResponse }
     * 
     */
    public GetRuleBaseResponse createGetRuleBaseResponse() {
        return new GetRuleBaseResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetRuleBase }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://RuleBaseWebservice.Webservice.com/", name = "getRuleBase")
    public JAXBElement<GetRuleBase> createGetRuleBase(GetRuleBase value) {
        return new JAXBElement<GetRuleBase>(_GetRuleBase_QNAME, GetRuleBase.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IOException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://RuleBaseWebservice.Webservice.com/", name = "IOException")
    public JAXBElement<IOException> createIOException(IOException value) {
        return new JAXBElement<IOException>(_IOException_QNAME, IOException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetRuleBaseResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://RuleBaseWebservice.Webservice.com/", name = "getRuleBaseResponse")
    public JAXBElement<GetRuleBaseResponse> createGetRuleBaseResponse(GetRuleBaseResponse value) {
        return new JAXBElement<GetRuleBaseResponse>(_GetRuleBaseResponse_QNAME, GetRuleBaseResponse.class, null, value);
    }

}
