package rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class MqLuyouP {
    private static final String EXCHANGE_NAME = "db.ye";
    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "direct",true);
        //第二个参数就是routingkey
        channel.basicPublish(EXCHANGE_NAME, "error", MessageProperties.PERSISTENT_TEXT_PLAIN, "这是错误信息".getBytes("UTF-8"));
        channel.basicPublish(EXCHANGE_NAME, "info", MessageProperties.PERSISTENT_TEXT_PLAIN, "这是程序运行信息".getBytes("UTF-8"));
        channel.basicPublish(EXCHANGE_NAME, "warning", MessageProperties.PERSISTENT_TEXT_PLAIN, "这是警告".getBytes("UTF-8"));
        System.out.println("[X] sent 6 message");
        channel.close();
        connection.close();

    }
}
