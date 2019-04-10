package rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

public class MqProducter {
    static String QUEUE_NAME="MAILQueue";
    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        //本机LinuxIP
        factory.setHost("127.0.0.1");
        factory.setPort(5672);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
       /* Map map = new HashMap();
        //接收邮件的邮箱
        map.put("sendto", "");
        //邮件标题
        map.put("subject", "测试邮件");
        //邮件内容
        map.put("content", "注册成功你的验证码是135469");
        channel.basicPublish("",QUEUE_NAME,null,ser(map));*/
        for(int i=0;i<10;i++){
            channel.basicPublish("", QUEUE_NAME, null, ("这是："+i).getBytes("UTF-8")); //注意发送和接受段相同字符集否则出现乱码
        }
        System.out.println("发布完成");
    }
    public static byte[] ser(Object obj) throws IOException {
        //接收被写入的字节数组
        ByteArrayOutputStream bos= new ByteArrayOutputStream();
        //把对象序列化成字节数组
        ObjectOutputStream oos= new ObjectOutputStream(bos);
        //写入
        oos.writeObject(obj);
        return bos.toByteArray();
    }

}
