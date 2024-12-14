package Vista;

import Modelo.Producto;
import Modelo.conexion;
import Modelo.gasto;
import controlador.productoControlador;
import controlador.compraControlador;
import controlador.gastoControlador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class compras {
    private JFrame ventana;
    private JPanel panel;
    private JLabel titulo, subtitulo1, subtitulo2;
    private JComboBox<String> producto1, producto2, producto3, gasto1, gasto2;
    private JTextField cant1,cant2 ,cant3, cant4, cant5, precio1,precio2,precio3;
    private String[] nombreProductos, nombreGastos;
    private List<Producto> inventario;
    private List<gasto> gastos;
    private JButton botonCompra, botonGasto, botonNewGasto;
    public compras(){
        try{
            this.gastos = new gastoControlador(new conexion().conectar()).listarGastos();
            this.inventario = new productoControlador(new conexion().conectar()).listarProductos();
            this.nombreProductos = new String[inventario.size()+1];
            this.nombreProductos[0] = "---";
            for(int i=0;i<inventario.size();i++){
                nombreProductos[i+1] = inventario.get(i).getNombre();
            }
            this.nombreGastos = new String[gastos.size()+1];
            this.nombreGastos[0] = "---";
            for(int i=0;i<gastos.size();i++){
                nombreGastos[i+1] = gastos.get(i).getNombreGasto();
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        this.ventana = new JFrame("Compras");
        this.ventana.setLayout(null);
        this.ventana.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.ventana.setResizable(false);
        this.ventana.setSize(400,400);
        this.ventana.setVisible(true);

        this.panel = new JPanel(null);
        this.ventana.add(panel);
        this.panel.setBackground(Color.BLACK);
        this.panel.setBounds(2,0,380,360);
        this.panel.setVisible(true);

        this.producto1 =new JComboBox<String>(nombreProductos);
        this.producto2 =new JComboBox<String>(nombreProductos);
        this.producto3 =new JComboBox<String>(nombreProductos);
        this.gasto1 =new JComboBox<String>(nombreGastos);
        this.gasto2 =new JComboBox<String>(nombreGastos);
        this.cant1 = new JTextField();
        this.cant2 = new JTextField();
        this.cant3 = new JTextField();
        this.cant4 = new JTextField();
        this.cant5 = new JTextField();
        this.precio1 = new JTextField();
        this.precio2 = new JTextField();
        this.precio3 = new JTextField();

        this.titulo = new JLabel("Registro de compras");
        this.titulo.setBounds(15,10,350,30);
        this.panel.add(titulo);
        this.titulo.setFont(new Font("Tahoma", Font.BOLD, 25));
        this.titulo.setHorizontalAlignment(SwingConstants.CENTER);
        this.titulo.setVerticalAlignment(SwingConstants.CENTER);
        this.titulo.setForeground(Color.white);

        this.subtitulo1 = new JLabel("Compra de productos en inventario");
        this.subtitulo1.setBounds(15,40,350,20);
        this.panel.add(subtitulo1);
        this.subtitulo1.setFont(new Font("Tahoma", Font.PLAIN, 20));
        this.subtitulo1.setForeground(Color.white);
        this.subtitulo1.setVerticalAlignment(SwingConstants.CENTER);

        ponerEtiquetasTabla(15,60,"Producto");
        ponerEtiquetasTabla(130,60,"Cantidad");
        ponerEtiquetasTabla(245,60,"Valor");

        ponerEtiquetasCompra(this.producto1,this.cant1,this.precio1,90);
        ponerEtiquetasCompra(this.producto2,this.cant2,this.precio2,120);
        ponerEtiquetasCompra(this.producto3,this.cant3,this.precio3,150);

        this.subtitulo2 = new JLabel("Registrar gasto");
        this.subtitulo2.setBounds(15,225,350,30);
        this.panel.add(subtitulo2);
        this.subtitulo2.setFont(new Font("Tahoma", Font.PLAIN, 20));
        this.subtitulo2.setForeground(Color.white);
        this.subtitulo2.setVerticalAlignment(SwingConstants.CENTER);

        ponerEtiquetasTabla(15,255,"Gasto");
        ponerEtiquetasTabla(130,255,"Valor");

        ponerEtiquetasCompra(this.gasto1,this.cant4,285);
        ponerEtiquetasCompra(this.gasto2,this.cant5,315);

        this.botonCompra = new JButton("Registrar compra");
        this.botonCompra.setBounds(105,190,170,30);
        this.panel.add(botonCompra);
        this.botonCompra.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String mensaje ="Compra de:\n";
                int idCompra =0;
                try {
                    idCompra = new compraControlador(new conexion().conectar()).registroNuevaCompra(new Date());
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                mensaje+= registroCompras(producto1.getItemAt(producto1.getSelectedIndex()),cant1.getText(),precio1.getText(),idCompra);
                mensaje+= registroCompras(producto2.getItemAt(producto2.getSelectedIndex()),cant2.getText(),precio2.getText(),idCompra);
                mensaje+= registroCompras(producto3.getItemAt(producto3.getSelectedIndex()),cant3.getText(),precio3.getText(),idCompra);

                JOptionPane.showMessageDialog(ventana,mensaje);
                producto1.getItemAt(0);
                producto2.getItemAt(0);
                producto3.getItemAt(0);
                cant1.setText("");
                cant2.setText("");
                cant3.setText("");
                precio1.setText("");
                precio2.setText("");
                precio3.setText("");
            }
        });

        this.botonGasto = new JButton("Registrar gasto");
        this.botonGasto.setBounds(240,285,125,30);
        this.panel.add(botonGasto);
        this.botonGasto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String mensaje = "";
                mensaje+= registroGasto(gasto1.getItemAt(gasto1.getSelectedIndex()),cant4.getText());
                mensaje+= registroGasto(gasto2.getItemAt(gasto2.getSelectedIndex()),cant5.getText());
                JOptionPane.showMessageDialog(ventana,mensaje);
                gasto1.getItemAt(0);
                gasto2.getItemAt(0);
                cant4.setText("");
                cant5.setText("");
            }
        });

        this.botonNewGasto = new JButton("Nuevo gasto");
        this.botonNewGasto.setBounds(240,320,125,30);
        this.panel.add(botonNewGasto);
        this.botonNewGasto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gasto g= new gasto();
                g.setNombreGasto(JOptionPane.showInputDialog(ventana,
                        "Nombre del gasto"));
                try {
                    new gastoControlador(new conexion().conectar()).ingresargasto(g);
                    JOptionPane.showMessageDialog(ventana,
                            "El gasto de "+g.getNombreGasto()+" se ingreso a la base de datos con exito.");
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(ventana,
                            ex.getMessage());
                }
            }
        });

    }

    public void ponerEtiquetasCompra(JComboBox<String> producto,JTextField cantidad,JTextField precio,int y){
        ponerEtiquetasCompra(producto,cantidad,y);
        //precio = new JTextField();
        precio.setBounds(245,y,100,20);
        panel.add(precio);
    }

    public void ponerEtiquetasCompra(JComboBox<String> gasto,JTextField valor, int y){
        //gasto = new JComboBox<String>(nombreProductos);
        gasto.setBounds(15,y,100,20);
        panel.add(gasto);
        //valor = new JTextField();
        valor.setBounds(130,y,100,20);
        panel.add(valor);
    }

    public void ponerEtiquetasTabla(int x,int y,String text){
        JLabel label = new JLabel(text);
        label.setBounds(x,y,100,30);
        label.setFont(new Font("Tahoma", Font.PLAIN, 15));
        label.setForeground(Color.white);
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(label);
    }

    public String generarCompraProducto(String nombre, String cantidad, String valor){
        String mensaje ="";
        if(!cantidad.isEmpty() && !valor.isEmpty() && (!nombre.equals("---") && Integer.parseInt(cantidad)>0
                && Integer.parseInt(valor)>0)){
            try {
                new compraControlador().editarInventarioXCompra(nombre,Integer.parseInt(cantidad),Integer.parseInt(valor));
                mensaje = cantidad+" unidades de "+nombre+" ingresadas con exito.\n";
            } catch (SQLException ex) {
                mensaje = ex.getMessage()+"\n";
            }
        }else{
            mensaje = "No fue posible ingresar la compra de " +nombre+ ", valide los datos ingresados.\n";
        }
        return mensaje;
    }

    public String generarGasto(String nombre, String valor){
        String mensaje ="";
        if(!valor.isEmpty() && (!nombre.equals("---") && Integer.parseInt(valor)>0)){
            try {
                gasto g = new gastoControlador(new conexion().conectar()).buscarGastoPorNombre(nombre);
                new gastoControlador(new conexion().conectar()).ingresarDetalleGastos(g.getId(),new Date(),Integer.parseInt(valor));
                mensaje = "El gasto de "+nombre+ " por "+valor+" se ingreso con exito.\n";
            } catch (SQLException ex) {
                mensaje = ex.getMessage()+"\n";
            }
        }else{
            mensaje = "No fue posible ingresar el gasto de " +nombre+ ", valide los datos ingresados.\n";
        }
        return mensaje;
    }

    public String registroCompras (String nombre,String cantidad,String precio, int idCompra){
        String mensaje="";
        if(!nombre.equals("---")){
            mensaje+= generarCompraProducto(nombre,cantidad,precio);
            try{
                int idproducto = new productoControlador(new conexion().conectar()).buscarProductoPorNombre(nombre).getId();
                new compraControlador(new conexion().conectar()).registroDetallesCompra(idCompra,idproducto,Integer.parseInt(cantidad),Integer.parseInt(precio));
            }catch (SQLException ex){
                System.out.println(ex.getMessage());
            }
        }
        return mensaje;
    }

    public String registroGasto (String nombre,String precio){
        String mensaje="";
        if(!nombre.equals("---")){
            mensaje+= generarGasto(nombre,precio);
        }
        return mensaje;
    }

}

