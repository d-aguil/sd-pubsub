package recibos.amqp;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Channel;

public class Productor {
    private final static String QUEUE_NAME = "recibos_queue";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            // Generar 2000 empleados
            for (int i = 1; i <= 2000; i++) {
                Empleado empleado = new Empleado("Empleado" + i, i, 3000, "empleado" + i + "@ejemplo.com");
                String message = empleado.getNombre() + "," + empleado.getLegajo() + "," + empleado.getSalario() + "," + empleado.getCorreo();
                channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
                System.out.println("Enviado: " + message);
            }
        }
    }
}
