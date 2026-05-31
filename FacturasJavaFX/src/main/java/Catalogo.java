
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Gestiona la carga del catálogo de máquinas desde archivo.
 */
public class Catalogo {

    /**
     * Carga la lista de máquinas desde el archivo de recursos.
     */
    public List<Maquina> cargarMaquinas() {
        List<Maquina> lista = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/Catalogo.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");
                lista.add(new Maquina(partes[0],Double.parseDouble(partes[1])));
            }
        } catch (IOException e) {
            System.out.println("Lista no encontrada");
        }
        return lista;
    }
}

