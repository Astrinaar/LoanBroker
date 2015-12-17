
package com.Client.MessageClient;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for replyObject complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="replyObject">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="bankName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="intrestRate" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="ssn" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "replyObject", propOrder = {
    "bankName",
    "intrestRate",
    "ssn"
})
public class ReplyObject {

    protected String bankName;
    protected BigDecimal intrestRate;
    protected int ssn;

    /**
     * Gets the value of the bankName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBankName() {
        return bankName;
    }

    /**
     * Sets the value of the bankName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBankName(String value) {
        this.bankName = value;
    }

    /**
     * Gets the value of the intrestRate property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getIntrestRate() {
        return intrestRate;
    }

    /**
     * Sets the value of the intrestRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setIntrestRate(BigDecimal value) {
        this.intrestRate = value;
    }

    /**
     * Gets the value of the ssn property.
     * 
     */
    public int getSsn() {
        return ssn;
    }

    /**
     * Sets the value of the ssn property.
     * 
     */
    public void setSsn(int value) {
        this.ssn = value;
    }

    @Override
    public String toString() {
        return "ReplyObject{" +
                "intrestRate=" + intrestRate +
                ", ssn=" + ssn +
                ", bankName='" + bankName + '\'' +
                '}';
    }
    
    

}
