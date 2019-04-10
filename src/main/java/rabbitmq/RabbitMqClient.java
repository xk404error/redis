package rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;

public class RabbitMqClient {
    ConnectionFactory factory;
    Connection connection;

    public RabbitMqClient() throws Exception {
        factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setPort(5672);
        connection = factory.newConnection();
    }

    public ConnectionFactory getFactory() {
        return factory;
    }

    public void setFactory(ConnectionFactory factory) {
        this.factory = factory;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }


}
