package recibos.amqp.v2;

import com.rabbitmq.client.*;
import java.io.IOException;
import java.util.Random;

public class ReceiptConsumer {
    private final static String QUEUE_NAME = "recibos";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("172.17.0.2");  // Cambia según tu configuración de RabbitMQ

        // Crear la conexión y el canal manualmente fuera del try-with-resources
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // Declarar la cola (duradera)
        boolean durable = true;
        channel.queueDeclare(QUEUE_NAME, durable, false, false, null);

        System.out.println(" [*] Esperando mensajes. Para salir presiona CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Recibido '" + message + "'");

            try {
                // Simular error aleatorio en la generación de recibos
                if (new Random().nextInt(10) < 2) {  // Error en el 20% de los casos
                    throw new Exception("Error al generar recibo");
                }

                // Simular generación de recibo (0.5 segundos)
                System.out.println("Generando recibo para: " + message);
                Thread.sleep(500);

                // Simular notificación por correo (0.1 segundos)
                System.out.println("Enviando notificación por correo...");
                Thread.sleep(100);

                System.out.println("Recibo generado y notificación enviada para: " + message);
                // Confirmar procesamiento exitoso
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);

            } catch (Exception e) {
                System.out.println("Error procesando el recibo: " + e.getMessage());
                // Rechazar el mensaje y volver a ponerlo en la cola para reintento
                channel.basicNack(delivery.getEnvelope().getDeliveryTag(), false, true);
            }
        };

        boolean autoAck = false; // Acknowledge manual
        channel.basicConsume(QUEUE_NAME, autoAck, deliverCallback, consumerTag -> { });

        // El programa continuará esperando mensajes indefinidamente
    }
}
