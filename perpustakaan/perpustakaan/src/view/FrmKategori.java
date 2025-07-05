/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;
import controller.KategoriDAO;
import model.Kategori;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

/**
 *
 * @author HP
 */

public class FrmKategori extends JFrame {

    private KategoriDAO dao = new KategoriDAO();
    private DefaultTableModel model;
    private int selectedId = -1;

    public FrmKategori() {
        initComponents();
        setLocationRelativeTo(null);
        setTitle("Form Data Kategori");
        tampilData();
    }

    private void initComponents() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JLabel lblNama = new JLabel("Nama Kategori:");

        txtNama = new JTextField();

        btnSimpan = new JButton("Simpan");
        btnUpdate = new JButton("Update");
        btnHapus = new JButton("Hapus");
        btnReset = new JButton("Reset");
        btnKembali = new JButton("Kembali"); // ✅ Tombol Kembali

        tabel = new JTable();
        JScrollPane scrollPane = new JScrollPane(tabel);

        model = new DefaultTableModel(new String[]{"ID", "Nama"}, 0);
        tabel.setModel(model);
        tabel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        setLayout(null);

        lblNama.setBounds(20, 20, 120, 25);
        txtNama.setBounds(140, 20, 200, 25);

        btnSimpan.setBounds(360, 20, 90, 25);
        btnUpdate.setBounds(360, 55, 90, 25);
        btnHapus.setBounds(360, 90, 90, 25);
        btnReset.setBounds(360, 125, 90, 25);
        btnKembali.setBounds(360, 160, 90, 25); // ✅ Posisi tombol kembali

        scrollPane.setBounds(20, 210, 540, 200);

        add(lblNama); add(txtNama);
        add(btnSimpan); add(btnUpdate);
        add(btnHapus); add(btnReset); add(btnKembali); // ✅ Tambahkan ke form
        add(scrollPane);

        setSize(600, 460);

        // Event tombol
        btnSimpan.addActionListener(e -> simpan());
        btnUpdate.addActionListener(e -> update());
        btnHapus.addActionListener(e -> hapus());
        btnReset.addActionListener(e -> resetForm());

        // ✅ Event tombol kembali
        btnKembali.addActionListener(e -> {
            dispose(); // menutup form kategori
            new FrmMainMenu().setVisible(true); // buka halaman utama
        });

        tabel.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabel.getSelectedRow() != -1) {
                int row = tabel.getSelectedRow();
                selectedId = Integer.parseInt(tabel.getValueAt(row, 0).toString());
                txtNama.setText(tabel.getValueAt(row, 1).toString());
            }
        });
    }

    private void tampilData() {
        model.setRowCount(0);
        List<Kategori> list = dao.getAllKategori();
        for (Kategori k : list) {
            model.addRow(new Object[]{k.getIdKategori(), k.getNamaKategori()});
        }
    }

    private void simpan() {
        Kategori k = new Kategori();
        k.setNamaKategori(txtNama.getText());
        dao.tambahKategori(k);
        resetForm();
        tampilData();
    }

    private void update() {
        if (selectedId != -1) {
            Kategori k = new Kategori();
            k.setIdKategori(selectedId);
            k.setNamaKategori(txtNama.getText());
            dao.updateKategori(k);
            resetForm();
            tampilData();
        }
    }

    private void hapus() {
        if (selectedId != -1) {
            dao.hapusKategori(selectedId);
            resetForm();
            tampilData();
        }
    }

    private void resetForm() {
        txtNama.setText("");
        selectedId = -1;
        tabel.clearSelection();
    }

    // Komponen
    private JTextField txtNama;
    private JButton btnSimpan, btnUpdate, btnHapus, btnReset, btnKembali;
    private JTable tabel;
}
