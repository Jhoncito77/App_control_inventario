package controlador;

import Modelo.gasto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class gastoControlador {
    private final Connection con;
    public gastoControlador(Connection con){
        this.con = con;
    }

    public List<gasto> listarGastos() throws SQLException {
        List<gasto> gastos = new ArrayList<>();
        try(con) {
            final PreparedStatement sql = con.prepareStatement("select * from gastos");
            try (sql) {
                final ResultSet result = sql.executeQuery();
                try (result) {
                    while (result.next()) {
                        gasto gasto = new gasto(result.getInt("id"), result.getString("nombre_gasto"), 1,0,0,"sin clasificacion");
                        gastos.add(gasto);
                    }
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException();
        } finally {
            con.close();
        }
        return gastos;
    }

    public void ingresargasto(gasto gasto) throws SQLException {
        try (con) {
            PreparedStatement st = con.prepareStatement("insert into gastos (nombre_gasto) values(?)");
            try (st) {
                st.setString(1, gasto.getNombreGasto());
                st.execute();
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        } finally {
            con.close();
        }
    }

    public gasto buscarGastoPorNombre(String nombreABuscar) throws SQLException {
        gasto gasto = new gasto();
        try (con) {
            PreparedStatement st = con.prepareStatement("select * from gastos where nombre_gasto=?");
            try (st) {
                st.setString(1, nombreABuscar);
                st.execute();
                final ResultSet result = st.getResultSet();
                try (result) {
                    result.next();
                        gasto.setId(result.getInt("id"));
                        gasto.setNombreGasto(result.getString("nombre_gasto"));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException();
        } finally {
            con.close();
        }
        return gasto;
    }
    public void ingresarDetalleGastos(int idGasto,Date fecha,int valorGasto) throws SQLException {
        try (con) {
            PreparedStatement st = con.prepareStatement("insert into detalle_gastos (id_gasto,fecha_gasto,valor_gasto) values(?,?,?)");
            try (st) {
                st.setInt(1, idGasto);
                st.setDate(2, new java.sql.Date(fecha.getTime()));
                st.setInt(3, valorGasto);
                st.execute();
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        } finally {
            con.close();
        }
    }
}
