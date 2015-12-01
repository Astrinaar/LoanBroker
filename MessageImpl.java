package Main;

import javax.jws.WebService;

@WebService(endpointInterface = "com.Main.Message")
public class MessageImpl implements Message {

	@Override
	public int loanRequest(int ssn, int loanAmount, int loanDurationInMonths) {

        return ssn;
    }

    @Override
   	public int getCreditScore(int ssn){
        //GET SCORE FOR BANK!

        int bankScoreCredit = 0;
        return bankScoreCredit;
    }
}
