package recibos.amqp;

import com.rabbitmq.client.*;

import java.nio.charset.StandardCharsets;
import java.util.Random;

public class Consumidor {
    private final static String QUEUE_NAME = "recibos_queue";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            System.out.println("Esperando mensajes...");

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                String[] datos = message.split(",");
                String nombre = datos[0];
                int legajo = Integer.parseInt(datos[1]);
                double salario = Double.parseDouble(datos[2]);
                String correo = datos[3];

                try {
                    generarRecibo(nombre, legajo, salario);
                    enviarNotificacion(correo, nombre);
                } catch (Exception e) {
                    System.out.println("Error al procesar el recibo para " + nombre + ": " + e.getMessage());
                    // Reintentar después de 2 segundos
                    try {
                        Thread.sleep(2000);
                        channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
                        System.out.println("Reintentando el recibo para " + nombre);
                    } catch (Exception retryException) {
                        System.out.println("No se pudo reintentar: " + retryException.getMessage());
                    }
                }
            };

            channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {});
        }
    }

    private static void generarRecibo(String nombre, int legajo, double salario) throws Exception {
        // Simular el tiempo de generación del recibo
        Thread.sleep(500); // 0.5 segundos
        // Simular fallo aleatorio en la generación
        if (new Random().nextInt(10) < 1) { // 10% de probabilidad de fallo
            throw new Exception("Fallo en la generación del recibo.");
        }
        System.out.println("Recibo generado para " + nombre);
    }

    private static void enviarNotificacion(String correo, String nombre) throws InterruptedException {
        // Simular el tiempo de envío del correo
        Thread.sleep(100); // 0.1 segundos
        System.out.println("Notificación enviada a " + correo);
    }
}
