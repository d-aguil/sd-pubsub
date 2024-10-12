package estacion.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class TestingMqttClient {

    public static void main(String[] args) {
        String broker = "tcp://localhost:1883"; // Dirección del broker (localhost si Docker está en tu máquina)
        String clientId = "SensorTemperatura";  // ID del cliente, puede ser único para cada sensor
        String topic = "temperatura";
        String content = "22.5";  // Ejemplo de dato de temperatura
        int qos = 2;  // Quality of Service nivel 2 (exactamente una vez)

        try {
            // Crear el cliente MQTT
            MqttClient mqttClient = new MqttClient(broker, clientId);

            // Configurar las opciones de conexión
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);

            // Conectar al broker
            System.out.println("Conectando al broker: " + broker);
            mqttClient.connect(connOpts);
            System.out.println("Conectado");

            // Crear el mensaje
            MqttMessage message = new MqttMessage(content.getBytes());
            message.setQos(qos);

            // Publicar el mensaje al tema
            System.out.println("Publicando mensaje: " + content);
            mqttClient.publish(topic, message);
            System.out.println("Mensaje publicado");

            // Desconectar
            mqttClient.disconnect();
            System.out.println("Desconectado");
        } catch (MqttException me) {
            System.out.println("Error en MQTT: " + me);
            me.printStackTrace();
        }
    }
}
