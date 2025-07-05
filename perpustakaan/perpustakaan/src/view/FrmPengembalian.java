/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.BukuDAO;
import controller.PengembalianDAO;
import controller.PeminjamanDAO; // ⬅️ Tambahkan baris ini
import model.Buku;
import model.Peminjaman;
import model.Pengembalian;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 *
 * @author HP
 */
// ... import tetap sama

public class FrmPengembalian extends JFrame {
    private PengembalianDAO pengembalianDAO = new PengembalianDAO();
    private PeminjamanDAO peminjamanDAO = new PeminjamanDAO();
    private DefaultTableModel model;

    private JComboBox<String> cmbPinjaman;
    private JTextField txtBuku, txtAnggota, txtTglPengembalian;
    private JButton btnSimpan, btnHapus, btnReset, btnKembali, btnHapusSemua; // ✅ tombol baru
    private JTable tabel;
    private int selectedId = -1;

    public FrmPengembalian() {
        initComponents();
        setLocationRelativeTo(null);
        setTitle("Form Pengembalian Buku");
        tampilData();
        isiComboPinjaman();
    }

    private void initComponents() {
        JLabel lblPinjaman = new JLabel("ID Pinjam:");
        JLabel lblBuku = new JLabel("Buku:");
        JLabel lblAnggota = new JLabel("Anggota:");
        JLabel lblTgl = new JLabel("Tanggal Pengembalian:");

        cmbPinjaman = new JComboBox<>();
        txtBuku = new JTextField(); txtBuku.setEditable(false);
        txtAnggota = new JTextField(); txtAnggota.setEditable(false);
        txtTglPengembalian = new JTextField();

        btnSimpan = new JButton("Simpan");
        btnHapus = new JButton("Hapus");
        btnReset = new JButton("Reset");
        btnKembali = new JButton("Kembali");
        btnHapusSemua = new JButton("Hapus Semua"); // ✅ inisialisasi tombol

        model = new DefaultTableModel(new String[]{"ID", "Anggota", "Buku", "Tgl Kembali", "Denda"}, 0);
        tabel = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(tabel);

        setLayout(null);

        lblPinjaman.setBounds(20, 20, 150, 25);
        cmbPinjaman.setBounds(180, 20, 200, 25);
        lblBuku.setBounds(20, 55, 150, 25);
        txtBuku.setBounds(180, 55, 200, 25);
        lblAnggota.setBounds(20, 90, 150, 25);
        txtAnggota.setBounds(180, 90, 200, 25);
        lblTgl.setBounds(20, 125, 150, 25);
        txtTglPengembalian.setBounds(180, 125, 200, 25);

        btnSimpan.setBounds(400, 20, 150, 25);
        btnHapus.setBounds(400, 55, 150, 25);
        btnReset.setBounds(400, 90, 150, 25);
        btnKembali.setBounds(400, 125, 150, 25);
        btnHapusSemua.setBounds(400, 160, 150, 25); // ✅ tombol baru

        scrollPane.setBounds(20, 200, 540, 200);

        add(lblPinjaman); add(cmbPinjaman);
        add(lblBuku); add(txtBuku);
        add(lblAnggota); add(txtAnggota);
        add(lblTgl); add(txtTglPengembalian);
        add(btnSimpan); add(btnHapus); add(btnReset); add(btnKembali); add(btnHapusSemua); // ✅ ditambahkan
        add(scrollPane);

        setSize(600, 460);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        btnSimpan.addActionListener(e -> simpan());
        btnHapus.addActionListener(e -> hapus());
        btnReset.addActionListener(e -> resetForm());
        btnKembali.addActionListener(e -> {
            dispose();
            new FrmMainMenu().setVisible(true);
        });

        btnHapusSemua.addActionListener(e -> { // ✅ aksi tombol
            int konfirmasi = JOptionPane.showConfirmDialog(this,
                "Yakin ingin menghapus semua data pengembalian?",
                "Konfirmasi",
                JOptionPane.YES_NO_OPTION);

            if (konfirmasi == JOptionPane.YES_OPTION) {
                pengembalianDAO.hapusSemuaPengembalian();
                tampilData();
                JOptionPane.showMessageDialog(this, "Semua data pengembalian berhasil dihapus.");
            }
        });

        cmbPinjaman.addActionListener(e -> updateFields());

        tabel.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabel.getSelectedRow() != -1) {
                int row = tabel.getSelectedRow();
                selectedId = Integer.parseInt(tabel.getValueAt(row, 0).toString());
            }
        });
    }

    private void isiComboPinjaman() {
        cmbPinjaman.removeAllItems();
        for (Peminjaman p : peminjamanDAO.getAllPeminjaman()) {
            cmbPinjaman.addItem(p.getIdPinjam() + " - " + p.getNamaAnggota() + " - " + p.getJudulBuku());
        }
    }

    private void updateFields() {
        if (cmbPinjaman.getSelectedItem() != null) {
            String[] parts = cmbPinjaman.getSelectedItem().toString().split(" - ");
            if (parts.length >= 3) {
                txtAnggota.setText(parts[1]);
                txtBuku.setText(parts[2]);
            }
        }
    }

    private void tampilData() {
        model.setRowCount(0);
        List<Pengembalian> list = pengembalianDAO.getAllPengembalian();
        for (Pengembalian p : list) {
            model.addRow(new Object[]{
                p.getIdPengembalian(), p.getNamaAnggota(), p.getNamaBuku(), p.getTglPengembalian(), p.getDenda()
            });
        }
    }

    private int hitungDenda(Date batas, Date kembali) {
        long selisih = kembali.getTime() - batas.getTime();
        long hariTerlambat = selisih / (1000 * 60 * 60 * 24);
        return (hariTerlambat > 0) ? (int) hariTerlambat * 1000 : 0;
    }

    private void simpan() {
        if (cmbPinjaman.getSelectedItem() == null || txtTglPengembalian.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua field harus diisi!");
            return;
        }

        try {
            String[] parts = cmbPinjaman.getSelectedItem().toString().split(" - ");
            int idPinjam = Integer.parseInt(parts[0]);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date tglKembali = sdf.parse(txtTglPengembalian.getText());

            Date tglBatas = peminjamanDAO.getBatasPengembalian(idPinjam);

            int denda = hitungDenda(tglBatas, tglKembali);

            Pengembalian p = new Pengembalian();
            p.setIdPinjam(idPinjam);
            p.setTglDikembalikan(tglKembali);
            p.setTglPengembalian(txtTglPengembalian.getText());
            p.setDenda(denda);

            pengembalianDAO.tambahPengembalian(p);
            tampilData();
            resetForm();

            JOptionPane.showMessageDialog(this, "Pengembalian disimpan. Denda: Rp " + denda);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Format tanggal salah (gunakan yyyy-MM-dd)!");
        }
    }

    private void hapus() {
        if (selectedId != -1) {
            pengembalianDAO.hapusPengembalian(selectedId);
            tampilData();
            resetForm();
        }
    }

    private void resetForm() {
        if (cmbPinjaman.getItemCount() > 0) cmbPinjaman.setSelectedIndex(0);
        txtBuku.setText("");
        txtAnggota.setText("");
        txtTglPengembalian.setText("");
        selectedId = -1;
        tabel.clearSelection();
    }
}
