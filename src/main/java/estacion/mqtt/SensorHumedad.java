package estacion.mqtt;

public class SensorHumedad extends Sensor {

    public SensorHumedad(String brokerUrl, String topic) {
        super("Humedad", brokerUrl, topic);
    }

    @Override
    protected double generarDato() {
        return 0 + (100 * Math.random());  // Genera humedad entre 0% y 100%
    }
}
