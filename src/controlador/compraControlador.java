package controlador;

import Modelo.Producto;
import Modelo.conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class compraControlador {
    private Connection con;
    public compraControlador(){}
    public compraControlador(Connection con){
        this.con = con;
    }


    public void editarInventarioXCompra(String nombre, int cantidad, int valor) throws SQLException {
        Producto producto = new productoControlador(new conexion().conectar()).buscarProductoPorNombre(nombre);
        producto.setCantidad(producto.getCantidad()+cantidad);
        producto.setCostoVenta(valor/cantidad);
        new productoControlador(new conexion().conectar()).editarProducto(producto);
    }

    public int registroNuevaCompra(Date fecha) throws SQLException {
        try{
            final PreparedStatement st = con.prepareStatement("insert into compras (fechaCompra) values (?)");
            try (st) {
                st.setDate(1, new java.sql.Date(fecha.getTime()));
                st.execute();
                return traerIdCompra();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            con.close();
        }
    }

    public int traerIdCompra() throws SQLException {
        try{
            final PreparedStatement st2 =con.prepareStatement("SELECT LAST_INSERT_ID()");
            try (st2) {
                st2.execute();
                final ResultSet result = st2.getResultSet();
                try (result) {
                    result.next();
                    System.out.println(result.getInt(1));
                    return result.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            con.close();
        }
    }
    public void registroDetallesCompra(int idCompra,int idProducto,int cantidad,int precio) throws SQLException {
        try{
            final PreparedStatement st = con.prepareStatement("insert into detalles_compra (id_compra,id_producto,cantidad,precio) values (?,?,?,?);");
            try (st) {
                st.setInt(1, idCompra);
                st.setInt(2, idProducto);
                st.setInt(3, cantidad);
                st.setInt(4, precio);
                st.execute();

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            con.close();
        }
    }

}
