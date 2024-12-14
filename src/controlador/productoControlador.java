package controlador;

import Modelo.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class productoControlador {
    final private Connection con;

    public productoControlador(Connection con) {
        this.con = con;
    }


    public List<Producto> listarProductos() throws SQLException {
        List<Producto> productos = new ArrayList<>();
        try (con) {
            final PreparedStatement sql = con.prepareStatement("select * from productos");
            try (sql) {
                final ResultSet result = sql.executeQuery();
                try (result) {
                    while (result.next()) {
                        Producto producto = new Producto(result.getInt("id"), result.getString("nombreProducto"),
                                result.getInt("cantidad"), result.getInt("precioVenta"), result.getInt("costoVenta"));
                        productos.add(producto);
                    }
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException();
        } finally {
            con.close();
        }
        return productos;
    }

    public void editarProducto(Producto producto) {
        try (con) {

            final PreparedStatement st = con
                    .prepareStatement("update productos set nombreProducto=?, cantidad=?," +
                            " precioVenta=?, costoVenta=? where id=?");
            try (st) {
                st.setString(1, producto.getNombre());
                st.setInt(2, producto.getCantidad());
                st.setInt(3, producto.getPrecioVenta());
                st.setInt(4, producto.getCostoVenta());
                st.setInt(5, producto.getId());
                st.execute();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Producto buscarProductoPorNombre(String nombreABuscar) throws SQLException {
        Producto producto = new Producto();
        try (con) {
            PreparedStatement st = con.prepareStatement("select * from productos where nombreProducto=?");
            try (st) {
                st.setString(1, nombreABuscar);
                st.execute();
                final ResultSet result = st.getResultSet();
                try (result) {
                    while (result.next()) {
                        producto.setId(result.getInt("id"));
                        producto.setNombre(result.getString("nombreProducto"));
                        producto.setCantidad(result.getInt("cantidad"));
                        producto.setPrecioVenta(result.getInt("precioVenta"));
                        producto.setCostoVenta(result.getInt("costoVenta"));
                    }

                }
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        } finally {
            con.close();
        }
        return producto;
    }

    public void ingresarProducto(Producto producto) throws SQLException {
        try (con) {
            PreparedStatement st = con.prepareStatement("insert into productos (nombreProducto,cantidad,precioVenta,costoVenta) values(?,?,?,?)");
            try (st) {
                st.setString(1, producto.getNombre());
                st.setInt(2, producto.getCantidad());
                st.setInt(3, producto.getPrecioVenta());
                st.setInt(4, producto.getCostoVenta());
                st.execute();
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        } finally {
            con.close();
        }
    }

    public void eliminarProducto(Producto producto) throws SQLException {
        try (con) {
            PreparedStatement st = con.prepareStatement("delete from productos where id=?");
            try(st){
                st.setInt(1,producto.getId());
                st.execute();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException();

        } finally {
            con.close();
        }
    }
}


