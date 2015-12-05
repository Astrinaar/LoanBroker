package com.Main;

import com.Client.RuleBase.IOException_Exception;
import com.Client.RuleBase.RuleBase;
import com.Client.RuleBase.RuleBaseImplService;
import com.Util.RabbitMQUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

public class GetBanks {
    private final static String Routing_Key = "SSN";

    public static void main(String[] argv)
            throws java.io.IOException,
            java.lang.InterruptedException {

        RabbitMQUtil util = new RabbitMQUtil();
        Connection connection = util.connectToRabbitMQ();
        Channel channel = connection.createChannel();

        channel.queueDeclare(Routing_Key, false, false, false, null);

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
                sendBankToRuleBase(Integer.parseInt(message));
            }
        };
        channel.basicConsume(Routing_Key, true, consumer);
    }

    public static void sendBankToRuleBase(int SSN) {
        RuleBaseImplService sis = new RuleBaseImplService();
        RuleBase si = sis.getRuleBaseImplPort();
        try {
            si.getRuleBase(SSN);

        } catch (IOException_Exception e) {
            e.printStackTrace();
        }
    }

}
