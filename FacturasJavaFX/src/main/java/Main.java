import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

/**
 * Aplicación principal de gestión de facturas con interfaz JavaFX
 */
public class Main extends Application {

    /**
     * Inicia la interfaz principal de la aplicación
     */
    @Override
    public void start(Stage stage) {
        Label titulo = new Label("MENÚ PRINCIPAL");
        titulo.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");

        Button btnCrear = new Button("Crear factura");
        Button btnDni = new Button("Buscar por DNI");
        Button btnDireccion = new Button("Buscar por dirección");
        Button btnSalir = new Button("Salir");

        btnSalir.setOnAction(e -> Platform.exit());

        VBox root = new VBox(10, titulo, btnCrear, btnDni, btnDireccion, btnSalir);
        root.setStyle("-fx-padding: 20;");

        stage.setScene(new Scene(root, 400, 300));
        stage.setTitle("Gestión de Facturas");
        stage.show();

        btnCrear.setOnAction(e -> abrirCrearFactura(stage));
        btnDni.setOnAction(e -> abrirBuscarDni(stage));
        btnDireccion.setOnAction(e -> abrirBuscarDireccion(stage));
    }

    /**
     * Abre ventana para buscar facturas por dirección
     */
    private void abrirBuscarDireccion(Stage stage) {
        Stage ventana = new Stage();

        Label lblCiudad = new Label("Ciudad (vacío para ignorar):");
        javafx.scene.control.TextField txtCiudad = new javafx.scene.control.TextField();

        Label lblCalle = new Label("Calle (vacío para ignorar):");
        javafx.scene.control.TextField txtCalle = new javafx.scene.control.TextField();

        Label lblNumero = new Label("Número (vacío para ignorar):");
        javafx.scene.control.TextField txtNumero = new javafx.scene.control.TextField();

        Button btnBuscar = new Button("Buscar");
        javafx.scene.control.TextArea resultado = new javafx.scene.control.TextArea();
        resultado.setEditable(false);

        btnBuscar.setOnAction(e -> {
            GestionFacturas gf = new GestionFacturas();
            List<Factura> facturas = gf.buscarPorDireccion(txtCiudad.getText(), txtCalle.getText(), txtNumero.getText());
            resultado.clear();
            if (facturas.isEmpty()) {
                resultado.setText("No se encontraron facturas.");
            } else {
                for (Factura f : facturas) {
                    resultado.appendText(f.toString() + "\n\n");
                }
            }
        });

        VBox root = new VBox(10, lblCiudad, txtCiudad, lblCalle, txtCalle, lblNumero, txtNumero, btnBuscar, resultado);
        root.setStyle("-fx-padding: 20;");

        ventana.setTitle("Buscar por dirección");
        ventana.setScene(new Scene(root, 400, 450));
        ventana.show();
    }

    /**
     * Abre ventana para buscar facturas por DNI
     */
    private void abrirBuscarDni(Stage stage) {
        Stage ventana = new Stage();

        Label lblDni = new Label("Introduce DNI:");
        javafx.scene.control.TextField txtDni = new javafx.scene.control.TextField();

        Button btnBuscar = new Button("Buscar");
        javafx.scene.control.TextArea resultado = new javafx.scene.control.TextArea();
        resultado.setEditable(false);

        btnBuscar.setOnAction(e -> {
            GestionFacturas gf = new GestionFacturas();
            List<Factura> facturas = gf.buscarPorDni(txtDni.getText());
            resultado.clear();
            if (facturas.isEmpty()) {
                resultado.setText("No se encontraron facturas.");
            } else {
                for (Factura f : facturas) {
                    resultado.appendText(f.toString() + "\n\n");
                }
            }
        });

        VBox root = new VBox(10, lblDni, txtDni, btnBuscar, resultado);
        root.setStyle("-fx-padding: 20;");

        ventana.setTitle("Buscar por DNI");
        ventana.setScene(new Scene(root, 400, 400));
        ventana.show();
    }
    /**
     * Muestra una ventana con el resumen de la factura
     */
    private void mostrarResumen(Factura factura) {
        Stage ventana = new Stage();

        Label resumen = new Label(factura.toString());

        Button btnCerrar = new Button("Cerrar");
        btnCerrar.setOnAction(e -> ventana.close());

        VBox root = new VBox(10, resumen, btnCerrar);
        root.setStyle("-fx-padding: 20;");

        ventana.setTitle("Resumen de factura");
        ventana.setScene(new Scene(root, 400, 400));
        ventana.show();
    }

    /**
     * Abre formulario para ingresar datos de dirección e instalación
     */
    private void abrirFormularioDireccion(Factura factura) {
        Stage ventana = new Stage();

        Label lblCiudad = new Label("Ciudad:");
        javafx.scene.control.TextField txtCiudad = new javafx.scene.control.TextField();

        Label lblCalle = new Label("Calle:");
        javafx.scene.control.TextField txtCalle = new javafx.scene.control.TextField();

        Label lblNumero = new Label("Número:");
        javafx.scene.control.TextField txtNumero = new javafx.scene.control.TextField();

        Label lblMetros = new Label("Metros adicionales:");
        javafx.scene.control.TextField txtMetros = new javafx.scene.control.TextField();

        Label lblGas = new Label("Gas extra:");
        javafx.scene.control.TextField txtGas = new javafx.scene.control.TextField();

        Button btnGuardar = new Button("Guardar factura");
        btnGuardar.setOnAction(e -> {
            factura.setCiudad(txtCiudad.getText());
            factura.setCalle(txtCalle.getText());
            factura.setNumero(txtNumero.getText());
            factura.setMetroAdicional(Double.parseDouble(txtMetros.getText()));
            factura.setGasExtra(Double.parseDouble(txtGas.getText()));

            GestionFacturas gf = new GestionFacturas();
            gf.GuardarFactura(factura);

            ventana.close();
            mostrarResumen(factura);
        });

        VBox root = new VBox(10, lblCiudad, txtCiudad, lblCalle, txtCalle, lblNumero, txtNumero,
                lblMetros, txtMetros, lblGas, txtGas, btnGuardar);
        root.setStyle("-fx-padding: 20;");

        ventana.setTitle("Dirección e instalación");
        ventana.setScene(new Scene(root, 300, 350));
        ventana.show();
    }

    /**
     * Abre ventana para seleccionar máquinas del catálogo
     */
    private void abrirSeleccionMaquinas(Cliente cliente, Factura factura) {
        Stage ventana = new Stage();

        Catalogo catalogo = new Catalogo();
        List<Maquina> maquinas = catalogo.cargarMaquinas();

        VBox root = new VBox(10);
        root.setStyle("-fx-padding: 20;");
        root.getChildren().add(new Label("Selecciona máquinas:"));

        for (Maquina m : maquinas) {
            Label lblMaquina = new Label(m.getModelo());
            javafx.scene.control.TextField txtCantidad = new javafx.scene.control.TextField("0");
            txtCantidad.setPrefWidth(40);
            txtCantidad.setUserData(m);
            javafx.scene.layout.HBox fila = new javafx.scene.layout.HBox(10, lblMaquina, txtCantidad);
            root.getChildren().add(fila);
        }

        Button btnSiguiente = new Button("Siguiente");
        btnSiguiente.setOnAction(e -> {
            for (javafx.scene.Node node : root.getChildren()) {
                if (node instanceof javafx.scene.layout.HBox fila) {
                    javafx.scene.control.TextField txt = (javafx.scene.control.TextField) fila.getChildren().get(1);
                    Maquina m = (Maquina) txt.getUserData();
                    int cantidad = Integer.parseInt(txt.getText());
                    for (int i = 0; i < cantidad; i++) {
                        factura.addMaquina(m);
                    }
                }
            }
            ventana.close();
            abrirFormularioDireccion(factura);
        });

        root.getChildren().add(btnSiguiente);

        javafx.scene.control.ScrollPane scroll = new javafx.scene.control.ScrollPane(root);
        scroll.setFitToWidth(true);

        ventana.setTitle("Selección de máquinas");
        ventana.setScene(new Scene(scroll, 300, 300));
        ventana.show();
    }
    /**
     * Abre formulario para registrar un nuevo cliente
     */
    private void abrirFormularioNuevoCliente(Stage stage) {
        Stage ventana = new Stage();

        Label lblNombre = new Label("Nombre:");
        javafx.scene.control.TextField txtNombre = new javafx.scene.control.TextField();

        Label lblDni = new Label("DNI:");
        javafx.scene.control.TextField txtDni = new javafx.scene.control.TextField();

        Label lblTelefono = new Label("Teléfono:");
        javafx.scene.control.TextField txtTelefono = new javafx.scene.control.TextField();

        Button btnSiguiente = new Button("Siguiente");
        btnSiguiente.setOnAction(e -> {
            String nombre = txtNombre.getText();
            String dni = txtDni.getText();
            String telefono = txtTelefono.getText();

            Cliente cliente = new Cliente(nombre, dni, telefono);
            GestionCliente gc = new GestionCliente();
            gc.NuevoCliente(nombre, dni, telefono);

            ventana.close();
            abrirSeleccionMaquinas(cliente, new Factura(cliente));
        });

        VBox root = new VBox(10, lblNombre, txtNombre, lblDni, txtDni, lblTelefono, txtTelefono, btnSiguiente);
        root.setStyle("-fx-padding: 20;");

        ventana.setTitle("Nuevo cliente");
        ventana.setScene(new Scene(root, 300, 250));
        ventana.show();
    }
    /**
     * Abre ventana para seleccionar un cliente existente
     */
    private void abrirSeleccionClienteExistente() {
        Stage ventana = new Stage();

        Label lblDni = new Label("Introduce DNI:");
        javafx.scene.control.TextField txtDni = new javafx.scene.control.TextField();

        Button btnBuscar = new Button("Buscar");
        Label lblResultado = new Label();

        Button btnSiguiente = new Button("Seleccionar");
        btnSiguiente.setVisible(false);

        final Cliente[] clienteEncontrado = {null};

        btnBuscar.setOnAction(e -> {
            GestionCliente gc = new GestionCliente();
            List<Cliente> clientes = gc.CargarClientes();
            clienteEncontrado[0] = null;
            lblResultado.setText("");

            for (Cliente c : clientes) {
                if (c.getDni().equalsIgnoreCase(txtDni.getText())) {
                    clienteEncontrado[0] = c;
                    lblResultado.setText("Encontrado: " + c.getName() + " - " + c.getTelefono());
                    btnSiguiente.setVisible(true);
                    break;
                }
            }
            if (clienteEncontrado[0] == null) {
                lblResultado.setText("No se encontró ningún cliente.");
                btnSiguiente.setVisible(false);
            }
        });

        btnSiguiente.setOnAction(e -> {
            ventana.close();
            abrirSeleccionMaquinas(clienteEncontrado[0], new Factura(clienteEncontrado[0]));
        });

        VBox root = new VBox(10, lblDni, txtDni, btnBuscar, lblResultado, btnSiguiente);
        root.setStyle("-fx-padding: 20;");

        ventana.setTitle("Seleccionar cliente");
        ventana.setScene(new Scene(root, 300, 250));
        ventana.show();
    }
    /**
     * Abre ventana para crear una nueva factura
     */
    private void abrirCrearFactura(Stage stage) {

        Stage ventana = new Stage();

        Button btnNuevoCliente = new Button("Nuevo cliente");
        Button btnClienteExistente = new Button("Cliente ya existente");

        btnNuevoCliente.setOnAction(e -> {
            ventana.close();
            abrirFormularioNuevoCliente(stage);
        });

        VBox root = new VBox(10, new Label("¿Qué tipo de cliente?"), btnNuevoCliente, btnClienteExistente);
        root.setStyle("-fx-padding: 20;");


        ventana.setTitle("Crear factura");
        ventana.setScene(new Scene(root, 300, 150));
        ventana.show();

        btnClienteExistente.setOnAction(e -> {
            ventana.close();
            abrirSeleccionClienteExistente();
        });
    }
    /**
     * Punto de entrada de la aplicación
     */
    public static void main(String[] args) {
        launch(args);
    }
}