package estacion.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public abstract class Sensor {
    private String tipoSensor;
    private String brokerUrl;
    private String topic;
    private MqttClient client;
    private Timer timer;
    private Random random;

    public Sensor(String tipoSensor, String brokerUrl, String topic) {
        this.tipoSensor = tipoSensor;
        this.brokerUrl = brokerUrl;
        this.topic = topic;
        this.random = new Random();

        try {
            client = new MqttClient(brokerUrl, MqttClient.generateClientId());
            client.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.timer = new Timer();
    }

    // Método abstracto para la generación de datos
    protected abstract double generarDato();

    // Inicia el envío periódico de datos
    public void iniciarEnvioDatos() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    double dato = generarDato();
                    enviarDatoMQTT(dato);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 0, 3000); // Envía datos cada 3 segundos
    }

    // Publica el dato al topic MQTT correspondiente
    private void enviarDatoMQTT(double dato) throws Exception {
        String mensaje = tipoSensor + ": " + dato;
        MqttMessage mqttMessage = new MqttMessage(mensaje.getBytes());
        client.publish(topic, mqttMessage);

        System.out.println("Sensor " + tipoSensor + " envió: " + mensaje);
    }

    public void detenerEnvioDatos() {
        timer.cancel();
    }
}
