package Vista;

import javax.swing.*;
import java.awt.*;

public class principal {
    private JFrame ventana;
    private JPanel panel;
    private JLabel titulo;
    private JButton botonVenta;
    private JButton botonCompra;
    private JButton botonInventario;
    private JButton botonInformes;
    private JLabel firma;
    private JLabel firma2;

    public principal() {
        this.ventana = new JFrame("Nombre del cliente");
        this.ventana.setBounds(0, 0, 500, 563);
        this.ventana.setLayout(null);
        this.ventana.setResizable(false);

        this.panel = new JPanel();
        this.panel.setBounds(2, 0, 480, 523);
        this.panel.setLayout(null);
        this.panel.setBackground(Color.BLACK);
        this.ventana.add(panel);
        this.panel.setVisible(true);

        this.titulo = new JLabel("Nombre del bar");
        this.titulo.setOpaque(true);
        this.titulo.setBackground(Color.black);
        this.titulo.setFont(new Font("Tahoma", Font.BOLD, 20));
        this.titulo.setForeground(Color.white);
        this.titulo.setBounds(0, 20, 480, 70);
        this.titulo.setHorizontalAlignment(SwingConstants.CENTER);
        this.titulo.setVerticalAlignment(SwingConstants.CENTER);
        this.panel.add(titulo);

        this.ventana.setDefaultCloseOperation(3);
        this.ventana.setVisible(true);

        this.botonVenta = new JButton("Nueva venta");
        this.panel.add(botonVenta);
        this.botonVenta.setBounds(30, 125, 200, 80);
        this.botonVenta.setFont(new Font("Tahoma", Font.BOLD, 20));
        this.botonVenta.addActionListener(e -> new ventas());

        this.botonCompra = new JButton("Compras");
        this.panel.add(botonCompra);
        this.botonCompra.setBounds(250, 125, 200, 80);
        this.botonCompra.setFont(new Font("Tahoma", Font.BOLD, 20));
        this.botonCompra.addActionListener(e -> new compras());

        this.botonInventario = new JButton("Inventario");
        this.panel.add(botonInventario);
        this.botonInventario.setBounds(30, 255, 420, 80);
        this.botonInventario.setFont(new Font("Tahoma", Font.BOLD, 20));
        this.botonInventario.addActionListener(e -> new inventario());

        this.botonInformes = new JButton("Informes");
        this.panel.add(botonInformes);
        this.botonInformes.setBounds(30, 385, 420, 80);
        this.botonInformes.setFont(new Font("Tahoma", Font.BOLD, 20));

        this.firma = new JLabel("Creado por Jhon Jairo Moreno Machado - celular 3005481546");
        this.firma.setOpaque(true);
        this.firma.setBackground(Color.black);
        this.firma.setFont(new Font("Tahoma", Font.ROMAN_BASELINE, 10));
        this.firma.setForeground(Color.white);
        this.firma.setBounds(0, 480, 480, 20);
        this.firma.setHorizontalAlignment(SwingConstants.CENTER);
        this.firma.setVerticalAlignment(SwingConstants.CENTER);
        this.panel.add(firma);

        this.firma2 = new JLabel("Prohibido el uso y distribucion no autorizada");
        this.firma2.setOpaque(true);
        this.firma2.setBackground(Color.black);
        this.firma2.setFont(new Font("Tahoma", Font.ROMAN_BASELINE, 10));
        this.firma2.setForeground(Color.white);
        this.firma2.setBounds(0, 501, 480, 20);
        this.firma2.setHorizontalAlignment(SwingConstants.CENTER);
        this.firma2.setVerticalAlignment(SwingConstants.CENTER);
        this.panel.add(firma2);
    }
}
