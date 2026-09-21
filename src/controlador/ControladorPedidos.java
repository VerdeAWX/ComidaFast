package controlador;

import modelo.Pedido;
import modelo.Repartidor;

import java.util.ArrayList;
import java.util.List;

public class ControladorPedidos {

    private final List<Pedido> pedidos;
    private final List<Repartidor> repartidores;

    public ControladorPedidos() {
        pedidos = new ArrayList<>();
        repartidores = new ArrayList<>();

        cargarRepartidores();
    }

    private void cargarRepartidores() {
        repartidores.add(new Repartidor("R001", "Juan Pérez"));
        repartidores.add(new Repartidor("R002", "María González"));
        repartidores.add(new Repartidor("R003", "Carlos Soto"));
    }

    public boolean agregarPedido(Pedido pedido) {

        if (buscarPedidoPorId(pedido.getId()) != null) {
            return false;
        }

        pedidos.add(pedido);
        return true;
    }

    public List<Pedido> obtenerPedidos() {
        return pedidos;
    }

    public Pedido buscarPedidoPorId(String id) {

        for (Pedido pedido : pedidos) {
            if (pedido.getId().equalsIgnoreCase(id)) {
                return pedido;
            }
        }

        return null;
    }

    public List<Repartidor> obtenerRepartidores() {
        return repartidores;
    }

    public boolean asignarRepartidor(String idPedido, Repartidor repartidor) {

        Pedido pedido = buscarPedidoPorId(idPedido);

        if (pedido != null) {
            pedido.setRepartidor(repartidor.getNombre());
            return true;
        }

        return false;
    }

    public boolean iniciarEntrega(String idPedido) {

        Pedido pedido = buscarPedidoPorId(idPedido);

        if (pedido != null && !pedido.getRepartidor().equals("Sin asignar")) {
            pedido.setEstado("En entrega");
            return true;
        }

        return false;
    }
}
