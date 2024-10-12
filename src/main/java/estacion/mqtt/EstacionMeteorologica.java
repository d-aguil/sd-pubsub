package estacion.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class EstacionMeteorologica {

    private String brokerUrl;

    public EstacionMeteorologica(String brokerUrl) {
        this.brokerUrl = brokerUrl;
    }

    // Método para suscribirse a los temas
    public void suscribirseATemas() {
        try {
            MqttClient client = new MqttClient(brokerUrl, MqttClient.generateClientId());
            client.connect();

            // Escuchar el tema de Temperatura
            client.subscribe("sensores/temperatura", new IMqttMessageListener() {
                @Override
                public void messageArrived(String topic, MqttMessage message) {
                    System.out.println("Estación Meteorológica recibió (Temperatura): " + new String(message.getPayload()));
                }
            });

            // Escuchar el tema de Humedad
            client.subscribe("sensores/humedad", new IMqttMessageListener() {
                @Override
                public void messageArrived(String topic, MqttMessage message) {
                    System.out.println("Estación Meteorológica recibió (Humedad): " + new String(message.getPayload()));
                }
            });

            // Escuchar el tema de Presión
            client.subscribe("sensores/presion", new IMqttMessageListener() {
                @Override
                public void messageArrived(String topic, MqttMessage message) {
                    System.out.println("Estación Meteorológica recibió (Presión): " + new String(message.getPayload()));
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Broker MQTT local
        String brokerUrl = "tcp://localhost:1883";
        EstacionMeteorologica estacion = new EstacionMeteorologica(brokerUrl);
        estacion.suscribirseATemas();
    }
}
