package Modelo;

import java.sql.*;

public class conexion{
    private Connection con;
    private String host = "localhost";
    private String usuario = "root";
    private String password = "Wgs82n83q1";
    private String nombreDB = "bar_estefa";
    private String url = "jdbc:mysql://"+host+"/"+nombreDB;

    public Connection conectar(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            this.con= DriverManager.getConnection(this.url,this.usuario,this.password);
        } catch (Exception e) {
            System.out.println("Error de conexión: "+e.getMessage());
        }

        return this.con;
    }

}
