package com.Model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Sean Emerson on 15-12-2015.
 */
public class ReplyObject implements Serializable {

    private BigDecimal interestRate;
    private int ssn;
    private String bankName;

    public ReplyObject(String bankName, int ssn, BigDecimal interestRate) {
        this.bankName = bankName;
        this.ssn = ssn;
        this.interestRate = interestRate;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public int getSsn() {
        return ssn;
    }

    public void setSsn(int ssn) {
        this.ssn = ssn;
    }

    public BigDecimal getIntrestRate() {
        return interestRate;
    }

    public void setIntrestRate(BigDecimal intrestRate) {
        this.interestRate = intrestRate;
    }

    @Override
    public String toString() {
        return "ReplyObject{" +
                "intrestRate=" + interestRate +
                ", ssn=" + ssn +
                ", bankName='" + bankName + '\'' +
                '}';
    }
}
