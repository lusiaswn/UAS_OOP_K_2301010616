/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.AnggotaDAO;
import model.Anggota;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
/**
 *
 * @author HP
 */


public class FrmAnggota extends JFrame {

    private AnggotaDAO dao = new AnggotaDAO();
    private DefaultTableModel model;
    private int selectedId = -1;

    public FrmAnggota() {
        initComponents();
        setLocationRelativeTo(null);
        setTitle("Form Data Anggota");
        tampilData();
    }

    private void initComponents() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JLabel lblNama = new JLabel("Nama:");
        JLabel lblKelas = new JLabel("Kelas:");
        JLabel lblAlamat = new JLabel("Alamat:");

        txtNama = new JTextField();
        txtKelas = new JTextField();
        txtAlamat = new JTextField();

        btnSimpan = new JButton("Simpan");
        btnUpdate = new JButton("Update");
        btnHapus = new JButton("Hapus");
        btnReset = new JButton("Reset");
        btnKembali = new JButton("Kembali");
        btnResetSemua = new JButton("Reset Semua");

        tabel = new JTable();
        JScrollPane scrollPane = new JScrollPane(tabel);

        model = new DefaultTableModel(new String[]{"ID", "Nama", "Kelas", "Alamat"}, 0);
        tabel.setModel(model);
        tabel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        setLayout(null);

        lblNama.setBounds(20, 20, 100, 25);
        txtNama.setBounds(130, 20, 200, 25);
        lblKelas.setBounds(20, 55, 100, 25);
        txtKelas.setBounds(130, 55, 200, 25);
        lblAlamat.setBounds(20, 90, 100, 25);
        txtAlamat.setBounds(130, 90, 200, 25);

        btnSimpan.setBounds(350, 20, 90, 25);
        btnUpdate.setBounds(350, 55, 90, 25);
        btnHapus.setBounds(350, 90, 90, 25);
        btnReset.setBounds(350, 125, 90, 25);
        btnKembali.setBounds(350, 160, 90, 25);
        btnResetSemua.setBounds(350, 195, 120, 25);

        scrollPane.setBounds(20, 240, 540, 200);

        add(lblNama); add(txtNama);
        add(lblKelas); add(txtKelas);
        add(lblAlamat); add(txtAlamat);
        add(btnSimpan); add(btnUpdate);
        add(btnHapus); add(btnReset); add(btnKembali);
        add(btnResetSemua);
        add(scrollPane);

        setSize(600, 500);

        // Event
        btnSimpan.addActionListener(e -> simpanAnggota());
        btnUpdate.addActionListener(e -> updateAnggota());
        btnHapus.addActionListener(e -> hapusAnggota());
        btnReset.addActionListener(e -> resetForm());
        btnKembali.addActionListener(e -> {
            dispose();
            new FrmMainMenu().setVisible(true);
        });

        // ✅ Reset Semua
        btnResetSemua.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Yakin ingin menghapus semua data dan mereset ID?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                dao.hapusSemuaAnggota(); // method di AnggotaDAO.java
                tampilData();
                resetForm();
                JOptionPane.showMessageDialog(this, "Semua data dihapus dan ID direset ke 1.");
            }
        });

        tabel.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabel.getSelectedRow() != -1) {
                int row = tabel.getSelectedRow();
                selectedId = Integer.parseInt(tabel.getValueAt(row, 0).toString());
                txtNama.setText(tabel.getValueAt(row, 1).toString());
                txtKelas.setText(tabel.getValueAt(row, 2).toString());
                txtAlamat.setText(tabel.getValueAt(row, 3).toString());
            }
        });
    }

    private void tampilData() {
        model.setRowCount(0);
        List<Anggota> list = dao.getAllAnggota();
        for (Anggota a : list) {
            model.addRow(new Object[]{
                a.getIdAnggota(), a.getNama(), a.getKelas(), a.getAlamat()
            });
        }
    }

    private void simpanAnggota() {
        Anggota a = new Anggota();
        a.setNama(txtNama.getText());
        a.setKelas(txtKelas.getText());
        a.setAlamat(txtAlamat.getText());
        dao.tambahAnggota(a);
        resetForm();
        tampilData();
    }

    private void updateAnggota() {
        if (selectedId != -1) {
            Anggota a = new Anggota();
            a.setIdAnggota(selectedId);
            a.setNama(txtNama.getText());
            a.setKelas(txtKelas.getText());
            a.setAlamat(txtAlamat.getText());
            dao.updateAnggota(a);
            resetForm();
            tampilData();
        }
    }

    private void hapusAnggota() {
        if (selectedId != -1) {
            dao.hapusAnggota(selectedId);
            resetForm();
            tampilData();
        }
    }

    private void resetForm() {
        txtNama.setText("");
        txtKelas.setText("");
        txtAlamat.setText("");
        selectedId = -1;
        tabel.clearSelection();
    }

    // Komponen
    private JTextField txtNama, txtKelas, txtAlamat;
    private JButton btnSimpan, btnUpdate, btnHapus, btnReset, btnKembali, btnResetSemua;
    private JTable tabel;
}
