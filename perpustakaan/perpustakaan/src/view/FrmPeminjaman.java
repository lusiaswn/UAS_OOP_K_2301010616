/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.PeminjamanDAO;
import model.Peminjaman;
import controller.BukuDAO;
import controller.AnggotaDAO;
import model.Buku;
import model.Anggota;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 * @author HP
 */


public class FrmPeminjaman extends JFrame {

    private final PeminjamanDAO pinjamDAO = new PeminjamanDAO();
    private final BukuDAO bukuDAO = new BukuDAO();
    private final AnggotaDAO anggotaDAO = new AnggotaDAO();
    private DefaultTableModel model;

    private JComboBox<String> cmbAnggota, cmbBuku;
    private JTextField txtTglPinjam, txtTglKembali;
    private JButton btnSimpan, btnReset, btnKembali, btnHapusSemua; // ✅ ditambahkan btnHapusSemua
    private JTable tabel;

    public FrmPeminjaman() {
        initComponents();
        setLocationRelativeTo(null);
        setTitle("Form Transaksi Peminjaman");
        loadComboBox();
        tampilData();
    }

    private void initComponents() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null);

        JLabel lblAnggota = new JLabel("Anggota:");
        JLabel lblBuku = new JLabel("Buku:");
        JLabel lblTglPinjam = new JLabel("Tgl Pinjam:");
        JLabel lblTglKembali = new JLabel("Tgl Kembali:");

        cmbAnggota = new JComboBox<>();
        cmbBuku = new JComboBox<>();
        txtTglPinjam = new JTextField(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        txtTglKembali = new JTextField();

        btnSimpan = new JButton("Pinjam");
        btnReset = new JButton("Reset");
        btnKembali = new JButton("Kembali");
        btnHapusSemua = new JButton("Hapus Semua"); // ✅ inisialisasi tombol

        tabel = new JTable();
        JScrollPane scrollPane = new JScrollPane(tabel);
        model = new DefaultTableModel(new String[]{"ID", "Anggota", "Buku", "Tgl Pinjam", "Tgl Kembali"}, 0);
        tabel.setModel(model);

        lblAnggota.setBounds(20, 20, 100, 25);
        cmbAnggota.setBounds(130, 20, 200, 25);
        lblBuku.setBounds(20, 55, 100, 25);
        cmbBuku.setBounds(130, 55, 200, 25);
        lblTglPinjam.setBounds(20, 90, 100, 25);
        txtTglPinjam.setBounds(130, 90, 200, 25);
        lblTglKembali.setBounds(20, 125, 100, 25);
        txtTglKembali.setBounds(130, 125, 200, 25);

        btnSimpan.setBounds(350, 20, 120, 25);
        btnReset.setBounds(350, 55, 120, 25);
        btnKembali.setBounds(350, 90, 120, 25);
        btnHapusSemua.setBounds(350, 125, 120, 25); // ✅ set posisi tombol

        scrollPane.setBounds(20, 170, 560, 200);

        add(lblAnggota); add(cmbAnggota);
        add(lblBuku); add(cmbBuku);
        add(lblTglPinjam); add(txtTglPinjam);
        add(lblTglKembali); add(txtTglKembali);
        add(btnSimpan); add(btnReset); add(btnKembali); add(btnHapusSemua); // ✅ ditambahkan tombol
        add(scrollPane);

        setSize(620, 430);

        btnSimpan.addActionListener(e -> simpan());
        btnReset.addActionListener(e -> resetForm());
        btnKembali.addActionListener(e -> {
            dispose();
            new FrmMainMenu().setVisible(true);
        });

        // ✅ Event tombol "Hapus Semua"
        btnHapusSemua.addActionListener(e -> {
            int konfirmasi = JOptionPane.showConfirmDialog(this,
                "Yakin ingin menghapus semua data peminjaman?",
                "Konfirmasi",
                JOptionPane.YES_NO_OPTION);

            if (konfirmasi == JOptionPane.YES_OPTION) {
                pinjamDAO.hapusSemuaPeminjaman();
                tampilData();     // refresh tabel
                loadComboBox();   // refresh buku yang tersedia
                JOptionPane.showMessageDialog(this, "Semua data peminjaman berhasil dihapus.");
            }
        });
    }

    private void loadComboBox() {
        cmbAnggota.removeAllItems();
        for (Anggota a : anggotaDAO.getAllAnggota()) {
            cmbAnggota.addItem(a.getIdAnggota() + " - " + a.getNama());
        }

        cmbBuku.removeAllItems();
        for (Buku b : bukuDAO.getAllBuku()) {
            if (b.getStatus().equalsIgnoreCase("Tersedia")) {
                cmbBuku.addItem(b.getIdBuku() + " - " + b.getJudul());
            }
        }
    }

    private void tampilData() {
        model.setRowCount(0);
        for (Peminjaman p : pinjamDAO.getAllPeminjaman()) {
            model.addRow(new Object[]{
                p.getIdPinjam(),
                p.getNamaAnggota(),
                p.getJudulBuku(),
                p.getTglPinjam(),
                p.getTglKembali()
            });
        }
    }

    private void simpan() {
        try {
            int idAnggota = Integer.parseInt(cmbAnggota.getSelectedItem().toString().split(" - ")[0]);
            int idBuku = Integer.parseInt(cmbBuku.getSelectedItem().toString().split(" - ")[0]);
            Date tglPinjam = new SimpleDateFormat("yyyy-MM-dd").parse(txtTglPinjam.getText());
            Date tglKembali = new SimpleDateFormat("yyyy-MM-dd").parse(txtTglKembali.getText());

            Buku bukuDipilih = bukuDAO.getBukuById(idBuku);
            if (!bukuDipilih.getStatus().equalsIgnoreCase("Tersedia")) {
                JOptionPane.showMessageDialog(this, "Buku ini sedang dipinjam dan belum tersedia.");
                return;
            }

            Peminjaman p = new Peminjaman();
            p.setIdAnggota(idAnggota);
            p.setIdBuku(idBuku);
            p.setTglPinjam(tglPinjam);
            p.setTglKembali(tglKembali);

            pinjamDAO.tambahPeminjaman(p);

            bukuDipilih.setStatus("Dipinjam");
            bukuDAO.updateBuku(bukuDipilih);

            resetForm();
            tampilData();
            loadComboBox();
            JOptionPane.showMessageDialog(this, "Peminjaman berhasil!");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Format tanggal harus yyyy-MM-dd atau periksa input.");
        }
    }

    private void resetForm() {
        txtTglPinjam.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        txtTglKembali.setText("");
        cmbAnggota.setSelectedIndex(0);
        cmbBuku.setSelectedIndex(0);
    }
}
