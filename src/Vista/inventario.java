package Vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import Modelo.conexion;
import controlador.productoControlador;
import Modelo.Producto;

public class inventario {
    private JFrame ventana;
    private JPanel panel;
    private JLabel titulo;
    private JTable tabla;
    private DefaultTableModel modelo;
    private JScrollPane paneltabla;
    private JButton botonAgregar, botonEditar, botonEliminar, botonActualizar;

    public inventario() {
        this.ventana = new JFrame("Inventario");
        this.ventana.setBounds(501, 0, 500, 500);
        this.ventana.setVisible(true);
        this.ventana.setLayout(null);
        this.ventana.setResizable(false);
        this.ventana.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        this.panel = new JPanel();
        this.panel.setBounds(2, 0, 480, 460);
        this.panel.setLayout(null);
        this.panel.setBackground(Color.BLACK);
        this.ventana.add(panel);
        this.panel.setVisible(true);

        this.titulo = new JLabel("INVENTARIO");
        this.titulo.setBounds(0, 20, 482, 40);
        this.panel.add(titulo);
        this.titulo.setFont(new Font("Tahoma", Font.BOLD, 35));
        this.titulo.setHorizontalAlignment(SwingConstants.CENTER);
        this.titulo.setVerticalAlignment(SwingConstants.CENTER);
        this.titulo.setForeground(Color.white);

        this.tabla = new JTable();

        this.modelo = (DefaultTableModel) tabla.getModel();
        this.modelo.addColumn("Producto");
        this.modelo.addColumn("Cantidad");
        this.modelo.addColumn("Precio Venta");
        cargarTabla();
        this.paneltabla = new JScrollPane(tabla);
        this.paneltabla.setBounds(40, 80, 400, 300);
        this.tabla.setBounds(0, 0, 400, 300);
        this.panel.add(paneltabla);

        this.botonActualizar = new JButton("Actualizar");
        this.botonActualizar.setBounds(20, 400, 96, 30);
        this.botonActualizar.setFont(new Font("Tahoma", Font.BOLD, 15));
        this.panel.add(botonActualizar);
        this.botonAgregar = new JButton("Agregar");
        this.botonAgregar.setBounds(135, 400, 96, 30);
        this.botonAgregar.setFont(new Font("Tahoma", Font.BOLD, 15));
        this.panel.add(botonAgregar);
        this.botonEditar = new JButton("Editar");
        this.botonEditar.setBounds(250, 400, 96, 30);
        this.botonEditar.setFont(new Font("Tahoma", Font.BOLD, 15));
        this.panel.add(botonEditar);
        this.botonEliminar = new JButton("Eliminar");
        this.botonEliminar.setBounds(365, 400, 96, 30);
        this.botonEliminar.setFont(new Font("Tahoma", Font.BOLD, 15));
        this.panel.add(botonEliminar);

        ActionListener actualizar = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarTabla();
                cargarTabla();
            }
        };
        this.botonActualizar.addActionListener(actualizar);

        this.botonEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (targetRow()) {
                    JOptionPane.showMessageDialog(ventana, "Por favor, elije un item para editar.");
                    return;
                }
                String nombre = (String) modelo.getValueAt(tabla.getSelectedRow(), 0);
                try {
                    Producto productoACambiar = new productoControlador(new conexion().conectar()).buscarProductoPorNombre(nombre);
                    for (int i = 0; i < 4; i++) {
                        if (i == 0) {
                            productoACambiar.setNombre(JOptionPane.showInputDialog(ventana,
                                    "Desea cambiar el nombre?", productoACambiar.getNombre()));
                        } else if (i == 1) {
                            productoACambiar.setCantidad(Integer.parseInt(JOptionPane.showInputDialog(ventana,
                                    "Desea cambiar la cantidad?", productoACambiar.getCantidad())));
                        } else if (i == 2) {
                            productoACambiar.setPrecioVenta(Integer.parseInt(JOptionPane.showInputDialog(ventana,
                                    "Desea cambiar el precio de venta?", productoACambiar.getPrecioVenta())));
                        } else {
                            productoACambiar.setCostoVenta(Integer.parseInt(JOptionPane.showInputDialog(ventana,
                                    "Desea cambiar el costo de venta?", productoACambiar.getCostoVenta())));
                        }
                    }
                    new productoControlador(new conexion().conectar()).editarProducto(productoACambiar);
                    JOptionPane.showMessageDialog(ventana, "Producto modificado con exito");
                    limpiarTabla();
                    cargarTabla();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        this.botonAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Producto producto = new Producto();
                for (int i = 0; i < 4; i++) {
                    if (i == 0) {
                        producto.setNombre(JOptionPane.showInputDialog(ventana,
                                "Nombre del producto"));
                    } else if (i == 1) {
                        producto.setCantidad(Integer.parseInt(JOptionPane.showInputDialog(ventana,
                                "Cantidad de producto")));
                    } else if (i == 2) {
                        producto.setPrecioVenta(Integer.parseInt(JOptionPane.showInputDialog(ventana,
                                "Precio de venta")));
                    } else {
                        producto.setCostoVenta(Integer.parseInt(JOptionPane.showInputDialog(ventana,
                                "Costo de venta")));
                    }
                }
                try {
                    new productoControlador(new conexion().conectar()).ingresarProducto(producto);
                    JOptionPane.showMessageDialog(ventana, "Producto ingresado con exito");
                    limpiarTabla();
                    cargarTabla();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        this.botonEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //funcionalidad para eliminar producto en base de datos
                if (targetRow()) {
                    JOptionPane.showMessageDialog(ventana, "Por favor, elije un item para eliminar.");
                    return;
                }
                String nombre = (String) modelo.getValueAt(tabla.getSelectedRow(), 0);
                try {
                    Producto productoAEliminar = new productoControlador(new conexion().conectar()).buscarProductoPorNombre(nombre);
                    new productoControlador(new conexion().conectar()).eliminarProducto(productoAEliminar);
                    JOptionPane.showMessageDialog(ventana, "Producto Eliminado con exito");
                    limpiarTabla();
                    cargarTabla();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
        private void cargarTabla () {
            try {
                List<Producto> productos = new productoControlador(new conexion().conectar()).listarProductos();
                try {
                    productos.forEach(producto -> modelo.addRow(new Object[]{producto.getNombre(), producto.getCantidad(),
                            producto.getPrecioVenta()}));
                } catch (Exception e) {
                    throw e;
                }
            } catch (Exception f) {
                throw new RuntimeException(f);
            }

        }

        public boolean targetRow () {
            return tabla.getSelectedRowCount() == 0 || tabla.getSelectedColumnCount() == 0;
        }

        public void limpiarTabla () {
            int aux = modelo.getRowCount();
            for (int i = 0; i < aux; i++) {
                modelo.removeRow(0);
            }
        }

    }
