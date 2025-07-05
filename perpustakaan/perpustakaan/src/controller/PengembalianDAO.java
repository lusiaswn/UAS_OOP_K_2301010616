/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.*;
import java.util.*;
import model.Pengembalian;
import koneksi.Koneksi;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 *
 * @author HP
 */


public class PengembalianDAO {

    private Connection conn;

    public PengembalianDAO() {
        conn = Koneksi.getKoneksi();
    }

    // Tambah pengembalian buku
    public void tambahPengembalian(Pengembalian p) {
        String insertSql = "INSERT INTO pengembalian (id_pinjam, tgl_dikembalikan, denda) VALUES (?, ?, ?)";
        String updateStatusSql = "UPDATE buku SET status = 'Tersedia' WHERE id_buku = (SELECT id_buku FROM peminjaman WHERE id_pinjam = ?)";

        try (
            PreparedStatement insertStmt = conn.prepareStatement(insertSql);
            PreparedStatement updateStmt = conn.prepareStatement(updateStatusSql)
        ) {
            insertStmt.setInt(1, p.getIdPinjam());
            insertStmt.setDate(2, new java.sql.Date(p.getTglDikembalikan().getTime()));
            insertStmt.setInt(3, p.getDenda());
            insertStmt.executeUpdate();

            updateStmt.setInt(1, p.getIdPinjam());
            updateStmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Ambil semua data pengembalian
    public List<Pengembalian> getAllPengembalian() {
        List<Pengembalian> list = new ArrayList<>();

        String sql = "SELECT pg.id_kembali, pg.id_pinjam, pg.tgl_dikembalikan, pg.denda, " +
                     "a.nama AS nama_anggota, b.judul AS nama_buku " +
                     "FROM pengembalian pg " +
                     "JOIN peminjaman p ON pg.id_pinjam = p.id_pinjam " +
                     "JOIN anggota a ON p.id_anggota = a.id_anggota " +
                     "JOIN buku b ON p.id_buku = b.id_buku";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Pengembalian p = new Pengembalian();
                p.setIdKembali(rs.getInt("id_kembali"));
                p.setIdPinjam(rs.getInt("id_pinjam"));
                p.setTglDikembalikan(rs.getDate("tgl_dikembalikan"));
                p.setDenda(rs.getInt("denda"));
                p.setNamaAnggota(rs.getString("nama_anggota"));
                p.setNamaBuku(rs.getString("nama_buku"));

                // Untuk tampil di JTable
                p.setTglPengembalian(rs.getDate("tgl_dikembalikan").toString());

                list.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // Hapus satu pengembalian berdasarkan ID
    public void hapusPengembalian(int id) {
        String sql = "DELETE FROM pengembalian WHERE id_kembali = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ✅ Hapus semua data dan reset ID ke 1
    public void hapusSemuaPengembalian() {
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM pengembalian");
            stmt.executeUpdate("ALTER TABLE pengembalian AUTO_INCREMENT = 1");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
