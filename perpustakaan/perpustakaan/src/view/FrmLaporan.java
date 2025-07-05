/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.PeminjamanDAO;
import controller.PengembalianDAO;
import model.Peminjaman;
import model.Pengembalian;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
/**
 *
 * @author HP
 */


public class FrmLaporan extends JFrame {
    private JTable tabelPeminjaman, tabelPengembalian;
    private DefaultTableModel modelPeminjaman, modelPengembalian;
    private JButton btnKembali;

    private PeminjamanDAO peminjamanDAO = new PeminjamanDAO();
    private PengembalianDAO pengembalianDAO = new PengembalianDAO();

    public FrmLaporan() {
        initComponents();
        setLocationRelativeTo(null);
        setTitle("Laporan Transaksi Perpustakaan");
        tampilData();
    }

    private void initComponents() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null);

        JLabel lblJudul = new JLabel("Laporan Transaksi Peminjaman dan Pengembalian Buku");
        lblJudul.setFont(new Font("Arial", Font.BOLD, 16));
        lblJudul.setBounds(100, 10, 600, 30);
        add(lblJudul);

        // === TABEL PEMINJAMAN ===
        JLabel lblPeminjaman = new JLabel("Tabel Peminjaman");
        lblPeminjaman.setFont(new Font("Arial", Font.BOLD, 14));
        lblPeminjaman.setBounds(20, 50, 200, 20);
        add(lblPeminjaman);

        modelPeminjaman = new DefaultTableModel(new String[]{"ID", "Nama", "Judul Buku", "Tanggal Pinjam", "Tanggal Kembali"}, 0);
        tabelPeminjaman = new JTable(modelPeminjaman);
        JScrollPane scrollPinjam = new JScrollPane(tabelPeminjaman);
        scrollPinjam.setBounds(20, 75, 660, 150);
        add(scrollPinjam);

        // === TABEL PENGEMBALIAN ===
        JLabel lblPengembalian = new JLabel("Tabel Pengembalian");
        lblPengembalian.setFont(new Font("Arial", Font.BOLD, 14));
        lblPengembalian.setBounds(20, 235, 200, 20);
        add(lblPengembalian);

        modelPengembalian = new DefaultTableModel(new String[]{"ID", "Judul Buku", "Tanggal Kembali", "Denda"}, 0);
        tabelPengembalian = new JTable(modelPengembalian);
        JScrollPane scrollKembali = new JScrollPane(tabelPengembalian);
        scrollKembali.setBounds(20, 260, 660, 150);
        add(scrollKembali);

        // Tombol kembali
        btnKembali = new JButton("Kembali");
        btnKembali.setBounds(580, 420, 100, 30);
        add(btnKembali);

        btnKembali.addActionListener(e -> {
            dispose();
            new FrmMainMenu().setVisible(true);
        });

        setSize(720, 500);
    }

    private void tampilData() {
        modelPeminjaman.setRowCount(0);
        for (Peminjaman p : peminjamanDAO.getAllPeminjaman()) {
            modelPeminjaman.addRow(new Object[]{
                p.getIdPinjam(),
                p.getNamaAnggota(),
                p.getJudulBuku(),
                p.getTglPinjam(),
                p.getTglKembali()
            });
        }

        modelPengembalian.setRowCount(0);
        for (Pengembalian pg : pengembalianDAO.getAllPengembalian()) {
            modelPengembalian.addRow(new Object[]{
                pg.getIdPengembalian(),
                pg.getNamaBuku(),
                pg.getTglPengembalian(),
                pg.getDenda()
            });
        }
    }
}
