package vista;

import controlador.ControladorPedidos;
import modelo.Pedido;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VentanaListaPedidos extends JFrame {

    private final ControladorPedidos controlador;

    private JTable tablaPedidos;
    private DefaultTableModel modeloTabla;

    private JButton btnActualizar;
    private JButton btnCerrar;

    public VentanaListaPedidos(ControladorPedidos controlador) {

        this.controlador = controlador;

        configurarVentana();
        crearComponentes();
        cargarPedidos();
    }

    private void configurarVentana() {

        setTitle("SpeedFast - Lista de Pedidos");
        setSize(800, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private void crearComponentes() {

        JLabel titulo = new JLabel(
                "LISTADO DE PEDIDOS",
                SwingConstants.CENTER
        );

        titulo.setFont(new Font("Arial", Font.BOLD, 20));

        add(titulo, BorderLayout.NORTH);

        String[] columnas = {
                "ID",
                "Dirección",
                "Tipo",
                "Repartidor",
                "Estado"
        };

        modeloTabla = new DefaultTableModel(columnas, 0) {

            @Override
            public boolean isCellEditable(
                    int row,
                    int column
            ) {
                return false;
            }
        };

        tablaPedidos = new JTable(modeloTabla);

        tablaPedidos.setRowHeight(25);
        tablaPedidos.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollPane =
                new JScrollPane(tablaPedidos);

        add(scrollPane, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();

        btnActualizar = new JButton("Actualizar");
        btnCerrar = new JButton("Cerrar");

        panelBotones.add(btnActualizar);
        panelBotones.add(btnCerrar);

        add(panelBotones, BorderLayout.SOUTH);

        btnActualizar.addActionListener(
                e -> cargarPedidos()
        );

        btnCerrar.addActionListener(
                e -> dispose()
        );
    }

    private void cargarPedidos() {

        modeloTabla.setRowCount(0);

        for (Pedido pedido : controlador.obtenerPedidos()) {

            Object[] fila = {
                    pedido.getId(),
                    pedido.getDireccion(),
                    pedido.getTipo(),
                    pedido.getRepartidor(),
                    pedido.getEstado()
            };

            modeloTabla.addRow(fila);
        }
    }
}
