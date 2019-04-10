package rabbitmq;

import com.rabbitmq.client.*;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.io.*;
import java.util.Map;

import static java.lang.Thread.sleep;

public class MqConsuomer {
    //装配发送邮件的类


    //将对象序列化为字节数组
    public static byte[] ser(Object obj) throws IOException {
        //接收被写入的字节数组
        ByteArrayOutputStream bos= new ByteArrayOutputStream();
        //把对象序列化成字节数组
        ObjectOutputStream oos= new ObjectOutputStream(bos);
        //写入
        oos.writeObject(obj);
        return bos.toByteArray();
    }
    //反序列化
    public static Object dser(byte[] src) throws Exception{
        //从字节数组读取数据
        ByteArrayInputStream bis = new ByteArrayInputStream(src);
        //把字节数组反序列化成对象
        ObjectInputStream ois= new ObjectInputStream(bis);
        return ois.readObject();
    }
    //消费者消费的队列名称
    private static String QUEUE_NAME="MAILQueue";


    public static void main(String[] args) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        //消费者也需要定义队列 有可能消费者先于生产者启动
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        //定义回调抓取消息
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                                       byte[] body) throws IOException {
                try {
                    System.out.println(new String(body,"UTF-8"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        //sleep(1000);
        channel.basicConsume(QUEUE_NAME, true, consumer);
    }



}
