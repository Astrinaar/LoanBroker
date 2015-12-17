package com.Webservice.RuleBaseWebservice;

import javax.jws.WebService;
import java.util.ArrayList;
import java.util.List;


@WebService(endpointInterface = "com.Webservice.RuleBaseWebservice.RuleBase")
public class RuleBaseImpl implements RuleBase {

	@Override
	public List<String> getRuleBase(int creditScore) {
        List<String> strings = new ArrayList<String>();
        if (bankJson(creditScore)){
            strings.add("bankJson");
        }
        if (bankXml(creditScore)) {
            strings.add("bankXml");
        }
        if (bankWebservice(creditScore)) {
            strings.add("bankWebservice");
        }
        if (bankBob(creditScore)) {
            strings.add("bankRabbitMQ");
        }
        return strings;
    }

    public boolean bankJson(int creditScore) {
        return creditScore >= 100;
    }
    public boolean bankXml(int creditScore) {
        return creditScore >= 300;
    }
    public boolean bankWebservice(int creditScore) {
        return creditScore >= 500;
    }
    public boolean bankBob(int creditScore) {
        return creditScore >= 100;
    }

}
