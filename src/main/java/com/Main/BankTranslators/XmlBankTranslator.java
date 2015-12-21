package com.Main.BankTranslators;

import com.Model.LoanObject;
import com.Util.RabbitMQUtil;
import com.Util.StringByteHelper;
import com.rabbitmq.client.*;
import org.json.JSONObject;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class XmlBankTranslator {

    static RabbitMQUtil rabbitMQUtil = new RabbitMQUtil();
    private final static String QUEUE_NAME_RECEIVE = "bankTranslator";
    private final static String EXCHANGE_NAME_SEND = "cphbusiness.bankJSON";

    public static void main(String[] argv) throws IOException {
        Channel channel = rabbitMQUtil.createQueue(QUEUE_NAME_RECEIVE);
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                try {
                    LoanObject loanObject = (LoanObject) StringByteHelper.fromByteArrayToObject(body);
                    System.out.println(" [x] Received '" + loanObject.toString() + "'");
                    startSendToMQ(loanObject);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    return;
                }

            }
        };
        channel.basicConsume(QUEUE_NAME_RECEIVE, true, consumer);
    }

    public static byte[] createXml(LoanObject loanObject) {
        try {
                 DocumentBuilderFactory dbFactory =
                 DocumentBuilderFactory.newInstance();
                 DocumentBuilder dBuilder =
                    dbFactory.newDocumentBuilder();
                 Document doc = dBuilder.newDocument();
                 // root element
                 Element rootElement = doc.createElement("LoanRequest");
                 doc.appendChild(rootElement);

                 // setting attribute to element
                 Attr attr = doc.createAttribute("ssn");
                 attr.setValue(loanObject.getSsn());

            Attr creditScore = doc.createAttribute("creditScore");
            creditScore.setValue(loanObject.getCreditScore());

                Attr loanAmount = doc.createAttribute("loanAmount");
            loanAmount.setValue(loanObject.getLoanAmount());

                Attr loanDuration = doc.createAttribute("loanDuration");
            loanDuration.setValue(loanObject.getLoanAmount());

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            ByteArrayOutputStream bos=new ByteArrayOutputStream();
            DOMSource source = new DOMSource(doc);
             StreamResult result=new StreamResult(bos);
             transformer.transform(source, result);
         return bos.toByteArray();
        } catch (Exception e) {
                e.printStackTrace();
            return null;
             }


    }

    public static void startSendToMQ(LoanObject loanObject) throws IOException {
        RabbitMQUtil rabbitMQUtil = new RabbitMQUtil();
        Channel channel = rabbitMQUtil.createExchange(EXCHANGE_NAME_SEND);

        AMQP.BasicProperties.Builder properties = new AMQP.BasicProperties().builder();
        properties.replyTo("xmlReply4");

        channel.basicPublish(EXCHANGE_NAME_SEND, "", properties.build(), createXml(loanObject));
        try {
            channel.close();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
