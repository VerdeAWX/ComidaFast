package vista;

import controlador.ControladorPedidos;
import modelo.Pedido;

import javax.swing.*;
import java.awt.*;

public class VentanaRegistroPedido extends JFrame {

    private final ControladorPedidos controlador;

    private JTextField txtId;
    private JTextField txtDireccion;
    private JComboBox<String> cmbTipo;
    private JButton btnGuardar;
    private JButton btnLimpiar;
    private JButton btnCerrar;

    public VentanaRegistroPedido(ControladorPedidos controlador) {

        this.controlador = controlador;

        configurarVentana();
        crearComponentes();
    }

    private void configurarVentana() {

        setTitle("SpeedFast - Registrar Pedido");
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private void crearComponentes() {

        JLabel titulo = new JLabel(
                "REGISTRO DE PEDIDO",
                SwingConstants.CENTER
        );

        titulo.setFont(new Font("Arial", Font.BOLD, 20));

        add(titulo, BorderLayout.NORTH);

        JPanel panelFormulario = new JPanel(
                new GridLayout(4, 2, 10, 10)
        );

        panelFormulario.setBorder(
                BorderFactory.createEmptyBorder(30, 30, 20, 30)
        );

        JLabel lblId = new JLabel("ID del pedido:");
        JLabel lblDireccion = new JLabel("Dirección:");
        JLabel lblTipo = new JLabel("Tipo de pedido:");

        txtId = new JTextField();
        txtDireccion = new JTextField();

        cmbTipo = new JComboBox<>();

        cmbTipo.addItem("Comida");
        cmbTipo.addItem("Encomienda");
        cmbTipo.addItem("Express");

        panelFormulario.add(lblId);
        panelFormulario.add(txtId);

        panelFormulario.add(lblDireccion);
        panelFormulario.add(txtDireccion);

        panelFormulario.add(lblTipo);
        panelFormulario.add(cmbTipo);

        panelFormulario.add(new JLabel(""));
        panelFormulario.add(new JLabel(""));

        add(panelFormulario, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();

        btnGuardar = new JButton("Guardar");
        btnLimpiar = new JButton("Limpiar");
        btnCerrar = new JButton("Cerrar");

        panelBotones.add(btnGuardar);
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnCerrar);

        add(panelBotones, BorderLayout.SOUTH);

        configurarEventos();
    }

    private void configurarEventos() {

        btnGuardar.addActionListener(e -> guardarPedido());

        btnLimpiar.addActionListener(e -> limpiarFormulario());

        btnCerrar.addActionListener(e -> dispose());
    }

    private void guardarPedido() {

        String id = txtId.getText().trim();
        String direccion = txtDireccion.getText().trim();
        String tipo = (String) cmbTipo.getSelectedItem();

        if (id.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe ingresar el ID del pedido.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE
            );

            txtId.requestFocus();
            return;
        }

        if (direccion.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe ingresar la dirección.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE
            );

            txtDireccion.requestFocus();
            return;
        }

        if (controlador.buscarPedidoPorId(id) != null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Ya existe un pedido con el ID ingresado.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            txtId.requestFocus();
            return;
        }

        Pedido nuevoPedido =
                new Pedido(id, direccion, tipo);

        boolean agregado =
                controlador.agregarPedido(nuevoPedido);

        if (agregado) {

            JOptionPane.showMessageDialog(
                    this,
                    "Pedido registrado correctamente.",
                    "Registro exitoso",
                    JOptionPane.INFORMATION_MESSAGE
            );

            limpiarFormulario();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "No fue posible registrar el pedido.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void limpiarFormulario() {

        txtId.setText("");
        txtDireccion.setText("");
        cmbTipo.setSelectedIndex(0);

        txtId.requestFocus();
    }
}
