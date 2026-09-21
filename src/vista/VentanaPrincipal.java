package vista;

import controlador.ControladorPedidos;
import modelo.Pedido;
import modelo.Repartidor;

import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {

    private final ControladorPedidos controlador;

    private JButton btnRegistrar;
    private JButton btnListar;
    private JButton btnAsignar;
    private JButton btnSalir;

    public VentanaPrincipal() {

        controlador = new ControladorPedidos();

        configurarVentana();
        crearComponentes();
    }

    private void configurarVentana() {

        setTitle("SpeedFast - Sistema de Gestión de Entregas");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private void crearComponentes() {

        JLabel titulo = new JLabel(
                "SISTEMA DE GESTIÓN DE ENTREGAS SPEEDFAST",
                SwingConstants.CENTER
        );

        titulo.setFont(new Font("Arial", Font.BOLD, 20));

        add(titulo, BorderLayout.NORTH);

        JPanel panelBotones = new JPanel(new GridLayout(4, 1, 10, 10));

        btnRegistrar = new JButton("Registrar pedido");
        btnListar = new JButton("Listar pedidos");
        btnAsignar = new JButton("Asignar repartidor / Iniciar entrega");
        btnSalir = new JButton("Salir");

        panelBotones.setBorder(
                BorderFactory.createEmptyBorder(30, 100, 30, 100)
        );

        panelBotones.add(btnRegistrar);
        panelBotones.add(btnListar);
        panelBotones.add(btnAsignar);
        panelBotones.add(btnSalir);

        add(panelBotones, BorderLayout.CENTER);

        configurarEventos();
    }

    private void configurarEventos() {

        btnRegistrar.addActionListener(e -> {

            VentanaRegistroPedido ventana =
                    new VentanaRegistroPedido(controlador);

            ventana.setVisible(true);
        });

        btnListar.addActionListener(e -> {

            VentanaListaPedidos ventana =
                    new VentanaListaPedidos(controlador);

            ventana.setVisible(true);
        });

        btnAsignar.addActionListener(e -> asignarRepartidor());

        btnSalir.addActionListener(e -> {

            int opcion = JOptionPane.showConfirmDialog(
                    this,
                    "¿Está seguro de que desea salir?",
                    "Salir",
                    JOptionPane.YES_NO_OPTION
            );

            if (opcion == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
    }

    private void asignarRepartidor() {

        if (controlador.obtenerPedidos().isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "No existen pedidos registrados.",
                    "Información",
                    JOptionPane.INFORMATION_MESSAGE
            );

            return;
        }

        String idPedido = JOptionPane.showInputDialog(
                this,
                "Ingrese el ID del pedido:"
        );

        if (idPedido == null || idPedido.trim().isEmpty()) {
            return;
        }

        Pedido pedido = controlador.buscarPedidoPorId(idPedido);

        if (pedido == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "No se encontró un pedido con ese ID.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        JComboBox<Repartidor> comboRepartidores =
                new JComboBox<>();

        for (Repartidor repartidor :
                controlador.obtenerRepartidores()) {

            comboRepartidores.addItem(repartidor);
        }

        int opcion = JOptionPane.showConfirmDialog(
                this,
                comboRepartidores,
                "Seleccione un repartidor",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (opcion == JOptionPane.OK_OPTION) {

            Repartidor repartidor =
                    (Repartidor) comboRepartidores.getSelectedItem();

            if (repartidor != null) {

                controlador.asignarRepartidor(
                        idPedido,
                        repartidor
                );

                int iniciar = JOptionPane.showConfirmDialog(
                        this,
                        "Repartidor asignado correctamente.\n" +
                                "¿Desea iniciar la entrega?",
                        "Iniciar entrega",
                        JOptionPane.YES_NO_OPTION
                );

                if (iniciar == JOptionPane.YES_OPTION) {

                    controlador.iniciarEntrega(idPedido);

                    JOptionPane.showMessageDialog(
                            this,
                            "La entrega ha sido iniciada.",
                            "Entrega iniciada",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                }
            }
        }
    }
}
