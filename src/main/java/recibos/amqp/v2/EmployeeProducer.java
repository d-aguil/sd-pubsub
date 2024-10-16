package recibos.amqp.v2;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class EmployeeProducer {
    private final static String QUEUE_NAME = "recibos";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("172.17.0.2");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            // Declarar la cola como persistente
            boolean durable = true;
            channel.queueDeclare(QUEUE_NAME, durable, false, false, null);

            // Simular generación de 2000 empleados
            for (int i = 1; i <= 2000; i++) {
                String message = "Empleado " + i + ", Legajo: " + i + ", Salario: " + (30000 + i * 100) + ", Email: empleado" + i + "@empresa.com";
                channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
                System.out.println(" [x] Enviado '" + message + "'");

                // Simular un tiempo de generación de recibo de 0.5 segundos
                Thread.sleep(500);
            }
        }
    }
}
