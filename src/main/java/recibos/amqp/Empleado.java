package recibos.amqp;

public class Empleado {
    private String nombre;
    private int legajo;
    private double salario;
    private String correo;

    public Empleado(String nombre, int legajo, double salario, String correo) {
        this.nombre = nombre;
        this.legajo = legajo;
        this.salario = salario;
        this.correo = correo;
    }

    // Getters
    public String getNombre() { return nombre; }
    public int getLegajo() { return legajo; }
    public double getSalario() { return salario; }
    public String getCorreo() { return correo; }
}
