package rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class MqDingYueP {
    private static final String EXCHANGE_NAME ="db.logs" ;

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        //定义创建一个交换器 参数1 名称  参数2 交换器类型 参数3表示将交换器信息永久保存在服务器磁盘上 关闭rabbitmqserver也不会丢失
        //fanout发布订阅模式
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout",true);
        String message = null;
        for(int i=0;i<10;i++){
            message="发送第"+i+"消息";
            //第二个参数就是routingkey  不填 默认会转发给所有的订阅者队列
            channel.basicPublish(EXCHANGE_NAME, "", MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes("UTF-8"));
        }

        System.out.println(" [x] Sent 6 message");
        channel.close();
        connection.close();

    }
}
