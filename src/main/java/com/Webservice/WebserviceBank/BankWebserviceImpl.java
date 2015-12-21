package com.Webservice.WebserviceBank;

import javax.jws.WebService;
import java.math.BigDecimal;


@WebService(endpointInterface = "com.Webservice.WebserviceBank.BankWebservice")
public class BankWebserviceImpl implements BankWebservice {

	@Override
	public BigDecimal getInterestRate(String ssn, String creditScore, String loanAmount, String loanDuration) {
        int loanDurationInteger = Integer.parseInt(loanDuration);
        int creditScoreInteger = Integer.parseInt(creditScore);
        int loanAmountInteger = Integer.parseInt(loanAmount);
        return calcInterestRate(loanDurationInteger,creditScoreInteger, loanAmountInteger);
    }

    public static BigDecimal calcInterestRate(int duration, int creditScore, int amount){
       BigDecimal interestRate = BigDecimal.valueOf(creditScore * amount / duration / 1000);
        return interestRate;
    }
}
