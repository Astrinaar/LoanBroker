
package com.Client.Client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.Client.Client package. 
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

    private final static QName _LoanRequestResponse_QNAME = new QName("http://LoanRequestWebservice.Webservice.com/", "loanRequestResponse");
    private final static QName _IOException_QNAME = new QName("http://LoanRequestWebservice.Webservice.com/", "IOException");
    private final static QName _LoanRequest_QNAME = new QName("http://LoanRequestWebservice.Webservice.com/", "loanRequest");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.Client.Client
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link IOException }
     * 
     */
    public IOException createIOException() {
        return new IOException();
    }

    /**
     * Create an instance of {@link LoanRequest }
     * 
     */
    public LoanRequest createLoanRequest() {
        return new LoanRequest();
    }

    /**
     * Create an instance of {@link LoanRequestResponse }
     * 
     */
    public LoanRequestResponse createLoanRequestResponse() {
        return new LoanRequestResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LoanRequestResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://LoanRequestWebservice.Webservice.com/", name = "loanRequestResponse")
    public JAXBElement<LoanRequestResponse> createLoanRequestResponse(LoanRequestResponse value) {
        return new JAXBElement<LoanRequestResponse>(_LoanRequestResponse_QNAME, LoanRequestResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IOException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://LoanRequestWebservice.Webservice.com/", name = "IOException")
    public JAXBElement<IOException> createIOException(IOException value) {
        return new JAXBElement<IOException>(_IOException_QNAME, IOException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LoanRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://LoanRequestWebservice.Webservice.com/", name = "loanRequest")
    public JAXBElement<LoanRequest> createLoanRequest(LoanRequest value) {
        return new JAXBElement<LoanRequest>(_LoanRequest_QNAME, LoanRequest.class, null, value);
    }

}
