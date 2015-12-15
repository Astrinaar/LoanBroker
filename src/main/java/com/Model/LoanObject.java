package com.Model;

import java.io.Serializable;
import java.util.List;

public class LoanObject implements Serializable {
    private static final long serialVersionUID = 6529685098267757690L;
    String ssn;
    String creditScore;
    String loanAmount;
    String loanDuration;
    List<String> banks;

    public LoanObject(String ssn, String creditScore, String loanAmount, String loanDuration, List<String> banks) {
        this.ssn = ssn;
        this.creditScore = creditScore;
        this.loanAmount = loanAmount;
        this.loanDuration = loanDuration;
        this.banks = banks;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(String creditScore) {
        this.creditScore = creditScore;
    }

    public String getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(String loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getLoanDuration() {
        return loanDuration;
    }

    public void setLoanDuration(String loanDuration) {
        this.loanDuration = loanDuration;
    }

    public List<String> getBanks() {
        return banks;
    }

    public void setBanks(List<String> banks) {
        this.banks = banks;
    }

    @Override
    public String toString() {
        return "LoanObject{" +
                "ssn='" + ssn + '\'' +
                ", creditScore='" + creditScore + '\'' +
                ", loanAmount='" + loanAmount + '\'' +
                ", loanDuration='" + loanDuration + '\'' +
                ", banks=" + banks +
                '}';
    }
}
