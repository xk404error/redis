package rabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;

public class MqDingYueC {
    private static final String EXCHANGE_NAME ="db.logs" ;

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        //定义创建一个交换器 参数1 名称  参数2 交换器类型 参数3表示将交换器信息永久保存在服务器磁盘上 关闭rabbitmqserver也不会丢失
        //fanout发布订阅模式
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout",true);
        //产生一个随机的队列 该队列用于从交换器获取消息
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, EXCHANGE_NAME, "");
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        //定义回调抓取消息
        Consumer consumer = new DefaultConsumer(channel) {
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                                       byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
                //参数2 true表示确认该队列所有消息  false只确认当前消息 每个消息都有一个消息标记

            }
        };

        //参数2 表示手动确认
        channel.basicConsume(queueName, true, consumer);


    }
}
