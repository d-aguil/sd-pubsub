package estacion.mqtt;

public class Main {

    public static void main(String[] args) {
        String brokerUrl = "tcp://localhost:1883";

        // Crear los tres sensores
        Sensor sensorTemperatura = new SensorTemperatura(brokerUrl, "sensores/temperatura");
        Sensor sensorHumedad = new SensorHumedad(brokerUrl, "sensores/humedad");
        Sensor sensorPresion = new SensorPresion(brokerUrl, "sensores/presion");

        // Iniciar el envío de datos desde los sensores
        sensorTemperatura.iniciarEnvioDatos();
        sensorHumedad.iniciarEnvioDatos();
        sensorPresion.iniciarEnvioDatos();
    }
}
