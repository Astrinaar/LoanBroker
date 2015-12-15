package com.Util;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitMQUtil {
    public Connection connectToRabbitMQ() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("datdb.cphbusiness.dk");
        factory.setPort(5672);
        factory.setUsername("student");
        factory.setPassword("cph");
        try {
            return factory.newConnection();

        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Channel createQueue (String QUEUE_NAME_RECEIVE) {
        Connection connection = connectToRabbitMQ();
        Channel channel;
        try {
            channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME_RECEIVE, false, false, false, null);

            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

            return channel;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    public Channel createExchange (String EXCHANGE_NAME){
        Connection connection = connectToRabbitMQ();
        Channel channel;

        try {
            channel = connection.createChannel();
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
            return channel;
        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }
}
