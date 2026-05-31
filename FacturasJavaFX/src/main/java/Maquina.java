
/** Máquina con modelo y coste de instalación. */
public class Maquina {
    /** Modelo. */
    private String modelo;

    /** Coste de instalación. */
    private double instalacion;

    /** Crea una máquina. */
    public Maquina(String modelo, double instalacion){
        this.modelo = modelo;
        this.instalacion = instalacion;
    }

    /** Devuelve el coste de instalación. */
    public double getInstalacion() {
        return instalacion;
    }

    /** Devuelve el modelo. */
    public String getModelo() {
        return modelo;
    }
}
