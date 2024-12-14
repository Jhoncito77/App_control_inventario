package Modelo;

public class gasto extends Producto{
    private int id;
    private String nombreGasto;
    private String clasificacionGasto;

    public gasto(){}
    public gasto(int id,String nombre, int cantidad, int precioVenta, int costoVenta, String clasificacionGasto){
        super(id,nombre,cantidad,precioVenta,costoVenta);

        this.clasificacionGasto = clasificacionGasto;
    }



    public String getNombreGasto() {
        return nombreGasto;
    }

    public void setNombreGasto(String nombreGasto) {
        this.nombreGasto = nombreGasto;
    }
}
