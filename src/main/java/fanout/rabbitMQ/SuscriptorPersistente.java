package fanout.rabbitMQ;

import com.rabbitmq.client.*;

import java.nio.charset.StandardCharsets;

public class SuscriptorPersistente {

    private static final String EXCHANGE_NAME = "fanout_logs";

    public static void main(String[] argv) throws Exception {
        // Configurar la conexión a RabbitMQ
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("172.17.0.2");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // Declarar el exchange de tipo fanout
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        // Crear una cola duradera
        String queueName = "durable_queue_" + System.currentTimeMillis(); // Un nombre único para cada cola
        channel.queueDeclare(queueName, true, false, false, null); // Cola duradera
        channel.queueBind(queueName, EXCHANGE_NAME, "");

        System.out.println(" [*] Esperando mensajes. Para salir presiona CTRL+C");

        // Callback para procesar los mensajes
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] Recibido: '" + message + "'");
        };

        // Consumir mensajes indefinidamente
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });
    }
}
