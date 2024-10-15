package fanout.rabbitMQ;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Productor {

    private final static String EXCHANGE_NAME = "exchange_fanout";

    public static void main(String[] args) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("172.17.0.2");

        try (Connection connection = factory.newConnection(); 
             Channel channel = connection.createChannel()) {

            // Declarar el exchange de tipo fanout
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

            for (int value = 0; value<20; value++) {

                // Mensaje a enviar
                String message = "Mensaje " + value;

                // Publicar el mensaje en el exchange
                channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes("UTF-8"));

                //
                System.out.println("Productor enviado: " + message);

                Thread.sleep(2000);
            }
        }
    }
}
