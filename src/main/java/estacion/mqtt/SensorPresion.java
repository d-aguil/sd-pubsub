package estacion.mqtt;

public class SensorPresion extends Sensor {

    public SensorPresion(String brokerUrl, String topic) {
        super("Presión", brokerUrl, topic);
    }

    @Override
    protected double generarDato() {
        return 950 + (50 * Math.random());  // Genera presión entre 950 hPa y 1000 hPa
    }
}
