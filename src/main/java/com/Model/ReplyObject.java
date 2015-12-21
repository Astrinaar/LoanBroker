package com.Model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Sean Emerson on 15-12-2015.
 */
public class ReplyObject implements Serializable {

    private BigDecimal interestRate;
    private String ssn;
    private String bankName;
    private long timestamp;

    public ReplyObject(String bankName, String ssn, BigDecimal interestRate) {
        this.bankName = bankName;
        this.ssn = ssn;
        this.interestRate = interestRate;
        this.timestamp = 0;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setIntrestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }
    
    public long getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp (long timestamp) {
        this.timestamp = timestamp;
    }
    
    @Override
    public String toString() {
        return "ReplyObject{" +
                "interestRate=" + interestRate +
                ", ssn=" + ssn +
                ", bankName='" + bankName + '\'' +
                '}';
    }
}
