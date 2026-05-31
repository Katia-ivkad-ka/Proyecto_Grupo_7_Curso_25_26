import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que gestiona las operaciones de facturas en el sistema.
 */
public class GestionFacturas {

    /**
     * Guarda una factura en el archivo de facturas.
     */
    public void GuardarFactura(Factura factura){
        try (FileWriter fw = new FileWriter("src/main/resources/Facturas.txt", true)) {
            fw.write(factura.toFile() + "\n");
        } catch (IOException e) {
            System.out.println("Error al guardar factura");
        }
    }

    /**
     * Carga las facturas desde el archivo.
     */
    public List<String> cargarFacturas() {
        List<String> lista = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/Facturas.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                lista.add(linea);
            }
        } catch (IOException e) {
            System.out.println("No se encontraron facturas");
        }

        return lista;
    }

    /**
     * Obtiene una lista de objetos Factura desde el archivo.
     */
    public List<Factura> obtenerFacturas() {
        List<String> lineas = cargarFacturas();
        List<Factura> factura = new ArrayList<>();

        Catalogo catalogo = new Catalogo();
        List<Maquina> maquinas = catalogo.cargarMaquinas();

        for (String l : lineas) {
            String[] partes = l.split(";");
            Cliente c = new Cliente(partes[0], partes[1], partes[2]);
            Factura f = new Factura(c);
            f.setCiudad(partes[3]);
            f.setCalle(partes[4]);
            f.setNumero(partes[5]);
            String[] modelos = partes[6].split(",");
            for (String modelo : modelos) {
                for (Maquina m : maquinas) {
                    if (m.getModelo().equalsIgnoreCase(modelo)) {
                        f.addMaquina(m);
                    }
                }
            }
            f.setMetroAdicional(Double.parseDouble(partes[7]));
            f.setGasExtra(Double.parseDouble(partes[8]));

            factura.add(f);
        }
        return factura;
    }

    /**
     * Busca facturas por el DNI del cliente.
     */
    public List<Factura> buscarPorDni(String dni) {
        List<Factura> todas = obtenerFacturas();
        List<Factura> resultado = new ArrayList<>();

        for (Factura f : todas) {
            if (f.getCliente().getDni().equalsIgnoreCase(dni)) {
                resultado.add(f);
            }
        }

        return resultado;
    }

    /**
     * Busca facturas por criterios de dirección (ciudad, calle, número).
     */
    public List<Factura> buscarPorDireccion(String ciudad, String calle, String numero) {
        List<Factura> todas = obtenerFacturas();
        List<Factura> resultado = new ArrayList<>();

        for (Factura f : todas) {
            if ((ciudad == null || ciudad.isEmpty() || ciudad.equalsIgnoreCase(f.getCiudad())) &&
                    (calle == null || calle.isEmpty() || calle.equalsIgnoreCase(f.getCalle())) &&
                    (numero == null || numero.isEmpty() || numero.equalsIgnoreCase(f.getNumero()))) {
                resultado.add(f);
            }
        }
        return resultado;
    }

}
