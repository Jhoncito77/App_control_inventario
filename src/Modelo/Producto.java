package Modelo;

public class Producto {
    private int id;
    private String nombre;
    private int cantidad;
    private int precioVenta;
    private int costoVenta;

    public Producto(){}

    public Producto(int id,String nombre,int cantidad,int precioVenta, int costoVenta){
        this.id = id;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precioVenta = precioVenta;
        this.costoVenta = costoVenta;
    }

    public int getId() { return id; }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(int precioVenta) {
        this.precioVenta = precioVenta;
    }

    public int getCostoVenta() {
        return costoVenta;
    }

    public void setCostoVenta(int costoVenta) {
        this.costoVenta = costoVenta;
    }

}
