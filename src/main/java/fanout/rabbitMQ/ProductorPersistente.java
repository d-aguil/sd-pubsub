package fanout.rabbitMQ;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class ProductorPersistente {

    private static final String EXCHANGE_NAME = "fanout_logs";

    public static void main(String[] argv) throws Exception {
        // Configurar la conexión a RabbitMQ
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("172.17.0.2");
        try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()) {
            // Declarar el exchange de tipo fanout
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

            // Enviar mensajes de prueba, con propiedad de persistencia
            for (int i = 1; i <= 20; i++) {
                String message = "Mensaje persistente número " + i;
                // Publicar mensaje persistente
                channel.basicPublish(EXCHANGE_NAME, "", MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes("UTF-8"));
                System.out.println(" [x] Enviado: '" + message + "'");
                Thread.sleep(5000);
            }
        }
    }
}
