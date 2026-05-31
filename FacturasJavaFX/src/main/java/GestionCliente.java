import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/** Gestiona operaciones con clientes. */
public class GestionCliente {

    /** Carga clientes desde archivo. */
    public List<Cliente> CargarClientes() {
        List<Cliente> lista = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/Clientes.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");
                lista.add(new Cliente(partes[0], partes[1], partes[2]));
            }
        } catch (IOException e) {
            System.out.println("Lista no encontrada");
        }
        return lista;
    }

    /** Agrega un nuevo cliente al archivo. */
    public void NuevoCliente(String nombre, String dni, String telefono) {
        List<Cliente> lista = CargarClientes();

        for (Cliente c : lista) {
            if (c.getDni().equalsIgnoreCase(dni)) {
                System.out.println("El cliente ya existe");
                return;
            }
        }

        try (FileWriter fw = new FileWriter("src/main/resources/Clientes.txt", true)) {
            fw.write(nombre + ";" + dni + ";" + telefono + "\n");
        } catch (IOException e) {
            System.out.println("Error al guardar cliente");
        }
    }
}
