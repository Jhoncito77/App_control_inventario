package Vista;

import Modelo.Producto;
import controlador.productoControlador;
import Modelo.conexion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ventas {
    private JFrame ventana;
    private JPanel panel;
    private JLabel titulo,labelCliente,cuentaValor;
    private JTextField textCliente;
    private JComboBox <String> p1,p2,p3,p4;
    private List<Producto> inv;
    private String[] productos;
    private JTextField t1,t2,t3,t4;
    private JButton BotonVenta, botonEditarCuenta, BotonCerrarCuenta, BotonPedidoNuevo;

    private Map<String,Integer> pedido = new HashMap<>();

    public ventas(){
        try{
            this.inv = new productoControlador(new conexion().conectar()).listarProductos();
            this.productos = new String[inv.size()+1];
            this.productos[0] = "---";
            for(int i=0;i<inv.size();i++){
                productos[i+1] = inv.get(i).getNombre();
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        this.ventana = new JFrame("Nueva venta");
        this.ventana.setBounds(501, 0, 300, 300);
        this.ventana.setLayout(null);
        this.ventana.setResizable(false);
        this.ventana.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.ventana.setVisible(true);

        this.panel = new JPanel();
        this.panel.setBounds(2, 0, 280, 260);
        this.panel.setLayout(null);
        this.panel.setBackground(Color.BLACK);
        this.ventana.add(panel);
        this.panel.setVisible(true);

        this.titulo = new JLabel("Nueva venta");
        this.titulo.setBounds(15,15,250,20);
        this.panel.add(titulo);
        this.titulo.setFont(new Font("Tahoma", Font.BOLD, 20));
        this.titulo.setHorizontalAlignment(SwingConstants.CENTER);
        this.titulo.setVerticalAlignment(SwingConstants.CENTER);
        this.titulo.setForeground(Color.white);

        this.labelCliente = new JLabel("Nombre:");
        this.labelCliente.setBounds(15,50,100,20);
        this.panel.add(labelCliente);
        this.labelCliente.setFont(new Font("Tahoma", Font.ROMAN_BASELINE, 20));
        this.labelCliente.setForeground(Color.white);

        this.textCliente = new JTextField();
        this.textCliente.setBounds(125,50,140,20);
        this.textCliente.setFont(new Font("Tahoma", Font.BOLD, 20));
        this.panel.add(textCliente);

        this.p1 = new JComboBox<String>(productos);
        this.p1.setBounds(15,90,120,20);
        this.panel.add(p1);

        this.t1 = new JTextField();
        this.t1.setBounds(145,90,120,20);
        this.panel.add(t1);

        this.p2 = new JComboBox<String>(productos);
        this.p2.setBounds(15,120,120,20);
        this.panel.add(p2);

        this.t2 = new JTextField();
        this.t2.setBounds(145,120,120,20);
        this.panel.add(t2);

        this.p3 = new JComboBox<String>(productos);
        this.p3.setBounds(15,150,120,20);
        this.panel.add(p3);

        this.t3 = new JTextField();
        this.t3.setBounds(145,150,120,20);
        this.panel.add(t3);

        this.p4 = new JComboBox<String>(productos);
        this.p4.setBounds(15,180,120,20);
        this.panel.add(p4);

        this.t4 = new JTextField();
        this.t4.setBounds(145,180,120,20);
        this.panel.add(t4);

        this.BotonVenta = new JButton("Crear cuenta");
        this.BotonVenta.setBounds(70,210,140,30);
        this.panel.add(BotonVenta);

        this.BotonVenta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                labelCliente.setText(textCliente.getText());
                titulo.setText("Cuenta de");
                textCliente.setVisible(false);
                ventana.setTitle(titulo.getText()+" "+labelCliente.getText());
                labelCliente.setBounds(15,50,250,20);
                labelCliente.setVerticalAlignment(SwingConstants.CENTER);
                labelCliente.setHorizontalAlignment(SwingConstants.CENTER);

                pedido =tomarPedido();


                pedido.forEach((key,value) -> validarExistenciasInventario(key,value));

                mostrarFields(false);

                List<String> claves = tomarClavesProductos();

                listarPedido(claves,pedido);

                cambiarAVistaPedido(claves);


            }
        }

        );

        this.botonEditarCuenta = new JButton("Editar cuenta");
        this.botonEditarCuenta.setBounds(15,220,120,30);
        this.panel.add(botonEditarCuenta);
        this.botonEditarCuenta.setVisible(false);
        this.botonEditarCuenta.setBackground(Color.green);
        this.botonEditarCuenta.setForeground(Color.black);

        this.botonEditarCuenta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarVentana();
                mostrarFields(true);
                BotonPedidoNuevo.setVisible(true);
                labelCliente.setVisible(true);
                titulo.setVisible(true);

            }
        });

        this.BotonCerrarCuenta = new JButton("Cerrar cuenta");
        this.BotonCerrarCuenta.setBounds(145,220,120,30);
        this.panel.add(BotonCerrarCuenta);
        this.BotonCerrarCuenta.setVisible(false);
        this.BotonCerrarCuenta.setBackground(Color.red);
        this.BotonCerrarCuenta.setForeground(Color.white);
        this.BotonCerrarCuenta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int opt=JOptionPane.showConfirmDialog(ventana, labelCliente.getText()+" va a pagar su cuenta?\n" +
                        cuentaValor.getText(), "Cerrar cuenta de "+labelCliente.getText(), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                System.out.println(opt);
                if(opt==0){
                    ventana.dispose();
                }
            }
        });

        this.cuentaValor = new JLabel();
        this.cuentaValor.setBounds(15,200,250,20);
        this.cuentaValor.setFont(new Font("Tahoma", Font.BOLD, 15));
        this.cuentaValor.setForeground(Color.white);
        this.cuentaValor.setVisible(false);
        this.panel.add(cuentaValor);

        this.BotonPedidoNuevo = new JButton("Añadir");
        this.BotonPedidoNuevo.setBounds(80,220,120,30);
        this.panel.add(BotonPedidoNuevo);
        this.BotonPedidoNuevo.setVisible(false);
        this.BotonPedidoNuevo.setBackground(Color.green);
        this.BotonPedidoNuevo.setForeground(Color.black);

        this.BotonPedidoNuevo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarVentana();
                cambiarAVistaPedido(tomarClavesProductos());
                mostrarFields(false);
                Map<String, Integer> nuevoPedido = tomarPedido();
                System.out.println("cuenta "+pedido.toString());
                System.out.println("nuevo pedido "+nuevoPedido.toString());
                pedido.forEach((key, value) ->{
                    nuevoPedido.merge(key,value,(v1,v2)->v1+v2);

                });

                pedido = nuevoPedido;
                listarPedido(tomarClavesProductos(),pedido);
                System.out.println("cuenta total "+pedido.toString());
                BotonPedidoNuevo.setVisible(false);
                labelCliente.setVisible(true);
                titulo.setVisible(true);
                cuentaValor.setText("Total = "+String.valueOf(totalPedido(pedido,tomarClavesProductos())));
            }
        });

    }

    public int totalPedido(Map<String,Integer> pedido, List<String> claves){
        AtomicInteger totalpedido = new AtomicInteger();
        for(int i=0;i<pedido.size();i++){
            int finalI = i;
            inv.forEach(a -> {
                if(a.getNombre().equals(claves.get(finalI))){
                    totalpedido.addAndGet(a.getPrecioVenta() * pedido.get(claves.get(finalI)));
                }
            });
        }
        return totalpedido.get();
    }

    public void listarPedido(List<String> claves,Map<String,Integer> pedido){
        int x = 15;
        int y = 90;
        int w = 200;
        int h = 20;

        for(int i=0;i<pedido.size();i++){
            JLabel pr = new JLabel();
            pr.setBounds(x,y,w,h);
            y+=20;
            panel.add(pr);
            pr.setForeground(Color.white);
            pr.setText(claves.get(i) +"  =  "+pedido.get(claves.get(i)));
        }
    }

    public void mostrarFields(boolean estado){
        p1.setVisible(estado);
        p2.setVisible(estado);
        p3.setVisible(estado);
        p4.setVisible(estado);
        t1.setVisible(estado);
        t2.setVisible(estado);
        t3.setVisible(estado);
        t4.setVisible(estado);
    }

    public Map<String, Integer> tomarPedido(){
        Map<String, Integer> pedido = new HashMap<>();
        if(!p1.getItemAt(p1.getSelectedIndex()).equals("---")){
            if(validarExistenciasInventario(p1.getItemAt(p1.getSelectedIndex()),Integer.valueOf(t1.getText()))){
                pedido.put(p1.getItemAt(p1.getSelectedIndex()),Integer.valueOf(t1.getText()));
                sacarDelInventario(p1.getItemAt(p1.getSelectedIndex()),Integer.valueOf(t1.getText()));
            }else{
                JOptionPane.showMessageDialog(ventana,
                        "No hay "+p1.getItemAt(p1.getSelectedIndex()) +" suficiente para realizar" +
                                " el pedido, solo quedan "+existenciaProducto(p1.getItemAt(p1.getSelectedIndex())));
            }
        }
        if(!p2.getItemAt(p2.getSelectedIndex()).equals("---")){
            if(validarExistenciasInventario(p2.getItemAt(p2.getSelectedIndex()),Integer.valueOf(t2.getText()))){
                pedido.put(p2.getItemAt(p2.getSelectedIndex()),Integer.valueOf(t2.getText()));
                sacarDelInventario(p2.getItemAt(p2.getSelectedIndex()),Integer.valueOf(t2.getText()));
            }else{
                JOptionPane.showMessageDialog(ventana,
                        "No hay "+p2.getItemAt(p2.getSelectedIndex()) +" suficiente para realizar" +
                                " el pedido, solo quedan "+existenciaProducto(p2.getItemAt(p2.getSelectedIndex())));
            }
        }
        if(!p3.getItemAt(p3.getSelectedIndex()).equals("---")){
            if(validarExistenciasInventario(p3.getItemAt(p3.getSelectedIndex()),Integer.valueOf(t3.getText()))){
                pedido.put(p3.getItemAt(p3.getSelectedIndex()),Integer.valueOf(t3.getText()));
                sacarDelInventario(p3.getItemAt(p3.getSelectedIndex()),Integer.valueOf(t3.getText()));
            }else{
                JOptionPane.showMessageDialog(ventana,
                        "No hay "+p3.getItemAt(p3.getSelectedIndex()) +" suficiente para realizar" +
                                " el pedido, solo quedan "+existenciaProducto(p3.getItemAt(p3.getSelectedIndex())));
            }
        }
        if(!p4.getItemAt(p4.getSelectedIndex()).equals("---")){
            if(validarExistenciasInventario(p4.getItemAt(p4.getSelectedIndex()),Integer.valueOf(t4.getText()))){
                pedido.put(p4.getItemAt(p4.getSelectedIndex()),Integer.valueOf(t4.getText()));
                sacarDelInventario(p4.getItemAt(p4.getSelectedIndex()),Integer.valueOf(t4.getText()));
            }else{
                JOptionPane.showMessageDialog(ventana,
                        "No hay "+p4.getItemAt(p4.getSelectedIndex()) +" suficiente para realizar" +
                                " el pedido, solo quedan "+existenciaProducto(p4.getItemAt(p4.getSelectedIndex())));
            }
        }

        return pedido;
    }

    public void cambiarAVistaPedido(List<String> claves){
        cuentaValor.setText("Total = "+String.valueOf(totalPedido(pedido,claves)));
        BotonVenta.setVisible(false);//70,210,140,30
        botonEditarCuenta.setVisible(true);
        BotonCerrarCuenta.setVisible(true);
        cuentaValor.setVisible(true);
    }

    public List<String> tomarClavesProductos(){
        List<String> claves = new ArrayList<>();
        pedido.entrySet().stream().sorted(Comparator.comparing(a -> a.getValue())).
                forEach(a -> claves.add(a.getKey()));

        return claves;
    }

    public void limpiarVentana(){
        Component[] comp=panel.getComponents();
        for (Component component : comp) {
            component.setVisible(false);
        }
    }

    public boolean validarExistenciasInventario(String producto, int cantidad){
        boolean validacion = false;
        try{
            Producto productoValida =new productoControlador(new conexion().conectar()).buscarProductoPorNombre(producto);
            if(productoValida.getCantidad()>=cantidad){
                validacion=true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return validacion;
    }

    public int existenciaProducto(String nombre){
        Producto prod;
        try{
             prod=new productoControlador(new conexion().conectar()).buscarProductoPorNombre(nombre);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return prod.getCantidad();
    }

    public void sacarDelInventario(String nombre,int cantidad){
        Producto prod;
        try{
            prod=new productoControlador(new conexion().conectar()).buscarProductoPorNombre(nombre);
            prod.setCantidad(prod.getCantidad()-cantidad);
            new productoControlador(new conexion().conectar()).editarProducto(prod);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
