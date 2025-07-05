/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;
import java.sql.*;
import java.util.*;
import model.Peminjaman;
import koneksi.Koneksi;
import java.util.Date;

/**
 *
 * @author HP
 */



public class PeminjamanDAO {
    private Connection conn;

    public PeminjamanDAO() {
        conn = Koneksi.getKoneksi();
    }

    public void tambahPeminjaman(Peminjaman p) {
        String sql = "INSERT INTO peminjaman (id_anggota, id_buku, tgl_pinjam, tgl_kembali) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, p.getIdAnggota());
            stmt.setInt(2, p.getIdBuku());
            stmt.setDate(3, new java.sql.Date(p.getTglPinjam().getTime()));
            stmt.setDate(4, new java.sql.Date(p.getTglKembali().getTime()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Peminjaman> getAllPeminjaman() {
        List<Peminjaman> list = new ArrayList<>();
        String sql = "SELECT p.id_pinjam, a.nama AS nama_anggota, b.judul AS judul_buku, " +
                     "p.tgl_pinjam, p.tgl_kembali " +
                     "FROM peminjaman p " +
                     "JOIN anggota a ON p.id_anggota = a.id_anggota " +
                     "JOIN buku b ON p.id_buku = b.id_buku";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Peminjaman p = new Peminjaman();
                p.setIdPinjam(rs.getInt("id_pinjam"));
                p.setNamaAnggota(rs.getString("nama_anggota"));
                p.setJudulBuku(rs.getString("judul_buku"));
                p.setTglPinjam(rs.getDate("tgl_pinjam"));
                p.setTglKembali(rs.getDate("tgl_kembali"));
                list.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void hapusPeminjaman(int idPinjam) {
        String sql = "DELETE FROM peminjaman WHERE id_pinjam=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idPinjam);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ✅ Tambahan: Hapus semua peminjaman & reset ID
    public void hapusSemuaPeminjaman() {
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM peminjaman");
            stmt.executeUpdate("ALTER TABLE peminjaman AUTO_INCREMENT = 1");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ✅ Tambahan: Dapatkan batas waktu pengembalian
    public Date getBatasPengembalian(int idPinjam) {
        String sql = "SELECT tgl_kembali FROM peminjaman WHERE id_pinjam = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idPinjam);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDate("tgl_kembali");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
