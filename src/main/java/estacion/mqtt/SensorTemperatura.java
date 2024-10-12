package estacion.mqtt;

public class SensorTemperatura extends Sensor {

    public SensorTemperatura(String brokerUrl, String topic) {
        super("Temperatura", brokerUrl, topic);
    }

    @Override
    protected double generarDato() {
        return -10 + (40 * Math.random());  // Genera temperatura entre -10°C y 40°C
    }
}
