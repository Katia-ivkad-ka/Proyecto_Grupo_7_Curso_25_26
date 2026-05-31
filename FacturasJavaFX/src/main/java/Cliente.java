import java.util.Objects;

/**
 * Clase que representa un cliente con información identificativa.
 * Contiene datos personales como nombre, DNI y teléfono.
 */
public class Cliente {
    private String name;
    private String dni;
    private String telefono;

    public Cliente(String name, String dni, String telefono) {
        this.dni = dni;
        this.name = name;
        this.telefono = telefono;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public int hashCode() {
        return Objects  .hash(dni);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(dni, cliente.dni);
    }

}
