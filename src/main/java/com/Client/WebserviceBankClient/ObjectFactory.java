
package com.Client.WebserviceBankClient;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.Client.WebserviceBankClient package. 
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

    private final static QName _IOException_QNAME = new QName("http://WebserviceBank.Webservice.com/", "IOException");
    private final static QName _GetInterestRateResponse_QNAME = new QName("http://WebserviceBank.Webservice.com/", "getInterestRateResponse");
    private final static QName _GetInterestRate_QNAME = new QName("http://WebserviceBank.Webservice.com/", "getInterestRate");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.Client.WebserviceBankClient
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetInterestRate }
     * 
     */
    public GetInterestRate createGetInterestRate() {
        return new GetInterestRate();
    }

    /**
     * Create an instance of {@link GetInterestRateResponse }
     * 
     */
    public GetInterestRateResponse createGetInterestRateResponse() {
        return new GetInterestRateResponse();
    }

    /**
     * Create an instance of {@link IOException }
     * 
     */
    public IOException createIOException() {
        return new IOException();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IOException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://WebserviceBank.Webservice.com/", name = "IOException")
    public JAXBElement<IOException> createIOException(IOException value) {
        return new JAXBElement<IOException>(_IOException_QNAME, IOException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetInterestRateResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://WebserviceBank.Webservice.com/", name = "getInterestRateResponse")
    public JAXBElement<GetInterestRateResponse> createGetInterestRateResponse(GetInterestRateResponse value) {
        return new JAXBElement<GetInterestRateResponse>(_GetInterestRateResponse_QNAME, GetInterestRateResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetInterestRate }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://WebserviceBank.Webservice.com/", name = "getInterestRate")
    public JAXBElement<GetInterestRate> createGetInterestRate(GetInterestRate value) {
        return new JAXBElement<GetInterestRate>(_GetInterestRate_QNAME, GetInterestRate.class, null, value);
    }

}
