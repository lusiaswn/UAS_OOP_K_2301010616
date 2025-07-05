/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.BukuDAO;
import model.Buku;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import controller.KategoriDAO;
import model.Kategori;

/**
 *
 * @author HP
 */

// ... (bagian atas tetap sama)
public class FrmBuku extends javax.swing.JFrame {

    private final BukuDAO dao = new BukuDAO();
    private final KategoriDAO kategoriDAO = new KategoriDAO();
    private DefaultTableModel model;
    private JComboBox<String> cmbKategori;
    private List<Kategori> listKategori;

    public FrmBuku() {
        initComponents();
        setLocationRelativeTo(null);
        setTitle("Form Data Buku");
        tampilData();
        isiKategori();
    }

    private void initComponents() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JLabel lblJudul = new JLabel("Judul:");
        JLabel lblPengarang = new JLabel("Pengarang:");
        JLabel lblTahun = new JLabel("Tahun Terbit:");
        JLabel lblStatus = new JLabel("Status:");
        JLabel lblKategori = new JLabel("Kategori:");

        txtJudul = new JTextField();
        txtPengarang = new JTextField();
        txtTahun = new JTextField();

        cmbStatus = new JComboBox<>(new String[]{"Tersedia", "Dipinjam"});
        cmbKategori = new JComboBox<>();

        btnSimpan = new JButton("Simpan");
        btnUpdate = new JButton("Update");
        btnHapus = new JButton("Hapus");
        btnReset = new JButton("Reset");
        btnKembali = new JButton("Kembali");
        btnHapusSemua = new JButton("Hapus Semua"); // ✅ Tambahan

        tabel = new JTable();
        JScrollPane scrollPane = new JScrollPane(tabel);

        model = new DefaultTableModel(new String[]{"ID", "Judul", "Pengarang", "Tahun", "Status", "Kategori"}, 0);
        tabel.setModel(model);
        tabel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Layout
        setLayout(null);

        lblJudul.setBounds(20, 20, 100, 25);
        txtJudul.setBounds(130, 20, 200, 25);
        lblPengarang.setBounds(20, 55, 100, 25);
        txtPengarang.setBounds(130, 55, 200, 25);
        lblTahun.setBounds(20, 90, 100, 25);
        txtTahun.setBounds(130, 90, 200, 25);
        lblStatus.setBounds(20, 125, 100, 25);
        cmbStatus.setBounds(130, 125, 200, 25);
        lblKategori.setBounds(20, 160, 100, 25);
        cmbKategori.setBounds(130, 160, 200, 25);

        btnSimpan.setBounds(350, 20, 90, 25);
        btnUpdate.setBounds(350, 55, 90, 25);
        btnHapus.setBounds(350, 90, 90, 25);
        btnReset.setBounds(350, 125, 90, 25);
        btnKembali.setBounds(350, 160, 90, 25);
        btnHapusSemua.setBounds(350, 195, 120, 25); // ✅ Tambahan

        scrollPane.setBounds(20, 230, 540, 200);

        add(lblJudul); add(txtJudul);
        add(lblPengarang); add(txtPengarang);
        add(lblTahun); add(txtTahun);
        add(lblStatus); add(cmbStatus);
        add(lblKategori); add(cmbKategori);
        add(btnSimpan); add(btnUpdate);
        add(btnHapus); add(btnReset); add(btnKembali);
        add(btnHapusSemua); // ✅ Tambahan
        add(scrollPane);

        setSize(600, 490); // Ukuran form diperbesar sedikit

        btnSimpan.addActionListener(e -> simpanBuku());
        btnUpdate.addActionListener(e -> updateBuku());
        btnHapus.addActionListener(e -> hapusBuku());
        btnReset.addActionListener(e -> resetForm());
        btnKembali.addActionListener(e -> {
            dispose();
            new FrmMainMenu().setVisible(true);
        });

        btnHapusSemua.addActionListener(e -> { // ✅ Tambahan
            int confirm = JOptionPane.showConfirmDialog(this, "Yakin ingin menghapus semua buku?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                dao.hapusSemuaBuku(); // ✅ panggil DAO
                tampilData();
                resetForm();
            }
        });

        tabel.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabel.getSelectedRow() != -1) {
                int row = tabel.getSelectedRow();
                selectedId = Integer.parseInt(tabel.getValueAt(row, 0).toString());
                txtJudul.setText(tabel.getValueAt(row, 1).toString());
                txtPengarang.setText(tabel.getValueAt(row, 2).toString());
                txtTahun.setText(tabel.getValueAt(row, 3).toString());
                cmbStatus.setSelectedItem(tabel.getValueAt(row, 4).toString());
                cmbKategori.setSelectedItem(tabel.getValueAt(row, 5).toString());
            }
        });
    }

    private void isiKategori() {
        cmbKategori.removeAllItems();
        listKategori = kategoriDAO.getAllKategori();
        for (Kategori k : listKategori) {
            cmbKategori.addItem(k.getNamaKategori());
        }
    }

    private void tampilData() {
        model.setRowCount(0);
        List<Buku> list = dao.getAllBuku();
        for (Buku b : list) {
            model.addRow(new Object[]{
                b.getIdBuku(), b.getJudul(), b.getPengarang(), b.getTahunTerbit(), b.getStatus(), b.getNamaKategori()
            });
        }
    }

    private void simpanBuku() {
        Buku b = new Buku();
        b.setJudul(txtJudul.getText());
        b.setPengarang(txtPengarang.getText());
        b.setTahunTerbit(txtTahun.getText());
        b.setStatus(cmbStatus.getSelectedItem().toString());
        b.setIdKategori(listKategori.get(cmbKategori.getSelectedIndex()).getIdKategori());
        dao.tambahBuku(b);
        resetForm();
        tampilData();
    }

    private void updateBuku() {
        if (selectedId != -1) {
            Buku b = new Buku();
            b.setIdBuku(selectedId);
            b.setJudul(txtJudul.getText());
            b.setPengarang(txtPengarang.getText());
            b.setTahunTerbit(txtTahun.getText());
            b.setStatus(cmbStatus.getSelectedItem().toString());
            b.setIdKategori(listKategori.get(cmbKategori.getSelectedIndex()).getIdKategori());
            dao.updateBuku(b);
            resetForm();
            tampilData();
        }
    }

    private void hapusBuku() {
        if (selectedId != -1) {
            dao.hapusBuku(selectedId);
            resetForm();
            tampilData();
        }
    }

    private void resetForm() {
        txtJudul.setText("");
        txtPengarang.setText("");
        txtTahun.setText("");
        cmbStatus.setSelectedIndex(0);
        cmbKategori.setSelectedIndex(0);
        selectedId = -1;
        tabel.clearSelection();
    }

    private JTextField txtJudul, txtPengarang, txtTahun;
    private JComboBox<String> cmbStatus;
    private JButton btnSimpan, btnUpdate, btnHapus, btnReset, btnKembali, btnHapusSemua; // ✅ Tambahan
    private JTable tabel;
    private int selectedId = -1;
}
