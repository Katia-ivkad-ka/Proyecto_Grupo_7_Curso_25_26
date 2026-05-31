

import java.util.ArrayList;
import java.util.List;

/**
 * Representa una factura asociada a un cliente.
 * Gestiona la información de máquinas, dirección y costes adicionales.
 */
public class Factura {
    private Cliente cliente;
    private List<Maquina> maquinas;
    private String ciudad;
    private String calle;
    private String numero;
    private double metroAdicional;
    private double gasExtra;
    private double iva;

    public Factura(Cliente cliente) {
        this.maquinas = new ArrayList<>();
        this.cliente = cliente;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public double getMetroAdicional() {
        return metroAdicional;
    }

    public void setMetroAdicional(double metroAdicional) {
        this.metroAdicional = metroAdicional;
    }

    public double getGasExtra() {
        return gasExtra;
    }

    public void setGasExtra(double gasExtra) {
        this.gasExtra = gasExtra;
    }

    public void addMaquina(Maquina m) {
        maquinas.add(m);
    }

    public double precioMaquinas() {
        double totalMaquinas = 0;
        for (Maquina m : maquinas) {
            totalMaquinas = m.getInstalacion() + totalMaquinas;
        }
        return totalMaquinas;
    }

    public double costeMetroAdicional() {
        double costeMetroAdicional = 0;

        if (metroAdicional > 0) {
            return costeMetroAdicional = metroAdicional * 45;
        } else {
            return costeMetroAdicional = 0;
        }
    }

    public double costeGas() {
        double costeGas = 0;
        if (gasExtra > 0) {
            return costeGas = gasExtra * 5;
        } else {
            return costeGas = 0;
        }
    }

    public double costeIVA() {
        double totalMaquinas = precioMaquinas();
        double costeMetroAdicional = costeMetroAdicional();
        double costeGas = costeGas();
        double costeIVA;
        costeIVA = (totalMaquinas + costeGas + costeMetroAdicional) * 0.21;
        return costeIVA;
    }

    public double CalcularFactura() {
        double totalMaquinas = precioMaquinas();
        double costeMetroAdicional = costeMetroAdicional();
        double costeGas = costeGas();
        double iva = costeIVA();
        double totalFactura = totalMaquinas + costeGas + costeMetroAdicional + iva;
        return totalFactura;
    }

    public String listarModelos() {
        String resultado = "";

        for (Maquina m : maquinas) {
            resultado += "- " + m.getModelo() + "\n";
        }

        return resultado;
    }

    public String toFile() {
        String modelos = "";

        for (Maquina m : maquinas) {
            modelos += m.getModelo() + ",";
        }

        // quitar última coma si existe
        if (!modelos.isEmpty()) {
            modelos = modelos.substring(0, modelos.length() - 1);
        }

        return cliente.getName() + ";" +
                cliente.getDni() + ";" +
                cliente.getTelefono() + ";" +
                ciudad + ";" +
                calle + ";" +
                numero + ";" +
                modelos + ";" +
                metroAdicional + ";" +
                gasExtra;
    }

    @Override
    public String toString() {
        return "Nombre: " + cliente.getName() + "\n" + "DNI: " + cliente.getDni() + "\n" +
                "Teléfono: " + cliente.getTelefono() + "\n" +
                "Dirección: " + getCiudad() + ", " + getCalle() + ", " + getNumero() + "\n" +
                "Máquinas: \n" + listarModelos() + "\n" +
                "Instalación coste total : " + precioMaquinas() + "€" + "\n" +
                "Metros adicionales: " + getMetroAdicional() + "  " + costeMetroAdicional() + "€" + "\n" +
                "Gas extra: " + getGasExtra() + "  " + costeGas() + "€" + "\n" +
                "IVA: " + "21% " + costeIVA() + "€" + "\n\n" +
                "Total factura: " + CalcularFactura() + "€";
    }
}

