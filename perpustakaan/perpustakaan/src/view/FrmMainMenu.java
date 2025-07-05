/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;
import javax.swing.*;

/**
 *
 * @author HP
 */
public class FrmMainMenu extends javax.swing.JFrame {

    public FrmMainMenu() {
        initComponents();
        setLocationRelativeTo(null);
        setTitle("Menu Utama - Sistem Perpustakaan");
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        // Komponen menu
        menuBar = new JMenuBar();
        menuMaster = new JMenu("Master Data");
        menuTransaksi = new JMenu("Transaksi");
        menuLaporan = new JMenu("Laporan");
        menuLogout = new JMenu("Logout");

        itemBuku = new JMenuItem("Data Buku");
        itemAnggota = new JMenuItem("Data Anggota");
        itemKategori = new JMenuItem("Data Kategori");

        itemPeminjaman = new JMenuItem("Transaksi Peminjaman");
        itemPengembalian = new JMenuItem("Transaksi Pengembalian");

        itemLaporan = new JMenuItem("Laporan");
        itemLogout = new JMenuItem("Keluar");

        // Tambahkan item ke menu
        menuMaster.add(itemBuku);
        menuMaster.add(itemAnggota);
        menuMaster.add(itemKategori);

        menuTransaksi.add(itemPeminjaman);
        menuTransaksi.add(itemPengembalian);

        menuLaporan.add(itemLaporan);
        menuLogout.add(itemLogout);

        menuBar.add(menuMaster);
        menuBar.add(menuTransaksi);
        menuBar.add(menuLaporan);
        menuBar.add(menuLogout);

        setJMenuBar(menuBar);

        // Aksi menu
        itemBuku.addActionListener(e -> {
            try {
                new FrmBuku().setVisible(true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Form Buku belum tersedia.");
            }
        });

        itemAnggota.addActionListener(e -> {
            try {
                new FrmAnggota().setVisible(true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Form Anggota belum tersedia.");
            }
        });

        itemKategori.addActionListener(e -> {
            try {
                new FrmKategori().setVisible(true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Form Kategori belum tersedia.");
            }
        });

        itemPeminjaman.addActionListener(e -> {
            try {
                new FrmPeminjaman().setVisible(true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Form Peminjaman belum tersedia.");
            }
        });

        itemPengembalian.addActionListener(e -> {
            try {
                new FrmPengembalian().setVisible(true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Form Pengembalian belum tersedia.");
            }
        });

        itemLaporan.addActionListener(e -> {
            try {
                new FrmLaporan().setVisible(true);
            } catch (Exception ex) {
                ex.printStackTrace(); // debug jika error
                JOptionPane.showMessageDialog(this, "Form Laporan belum tersedia.");
            }
        });

        itemLogout.addActionListener(e -> {
            dispose();
            new FrmLogin().setVisible(true);
        });

        // Layout isi window
        JLabel labelWelcome = new JLabel("Selamat Datang di Sistem Perpustakaan", SwingConstants.CENTER);
        labelWelcome.setFont(labelWelcome.getFont().deriveFont(18f)); // Ukuran font lebih besar

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 400);
        setLayout(new java.awt.BorderLayout());
        add(labelWelcome, java.awt.BorderLayout.CENTER);
    }

    // Komponen menu
    private JMenuBar menuBar;
    private JMenu menuMaster, menuTransaksi, menuLaporan, menuLogout;
    private JMenuItem itemBuku, itemAnggota, itemKategori;
    private JMenuItem itemPeminjaman, itemPengembalian, itemLaporan, itemLogout;
}
