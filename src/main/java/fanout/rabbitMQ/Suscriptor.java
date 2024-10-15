package fanout.rabbitMQ;

import com.rabbitmq.client.*;

import java.nio.charset.StandardCharsets;

public class Suscriptor {

    private final static String EXCHANGE_NAME = "exchange_fanout";

    public static void main(String[] args) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();

        factory.setHost("172.17.0.2");

        int suscriptor;

        //
        if (args.length > 0) {
            suscriptor = Integer.parseInt(args[0]);
        } else {
            suscriptor = 0;
        }

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // Declarar el exchange de tipo fanout
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        // Crear una cola temporal para este suscriptor
        String queueName = channel.queueDeclare().getQueue();
        // Enlazar la cola al exchange de tipo fanout
        channel.queueBind(queueName, EXCHANGE_NAME, "");

        System.out.println(" [*] Esperando mensajes. Para salir presiona CTRL+C");

        // Callback para procesar los mensajes
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] Recibido: '" + message + "'");
        };

        // Consumir mensajes indefinidamente
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
        });
    }
}

