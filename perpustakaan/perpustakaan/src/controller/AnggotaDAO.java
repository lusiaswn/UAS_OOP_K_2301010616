/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.*;
import java.util.*;
import model.Anggota;
import koneksi.Koneksi;

/**
 *
 * @author HP
 */

public class AnggotaDAO {

    private final Connection conn;

    public AnggotaDAO() {
        conn = Koneksi.getKoneksi();
    }

    public void tambahAnggota(Anggota anggota) {
        String sql = "INSERT INTO anggota (nama, kelas, alamat) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, anggota.getNama());
            stmt.setString(2, anggota.getKelas());
            stmt.setString(3, anggota.getAlamat());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Anggota> getAllAnggota() {
        List<Anggota> list = new ArrayList<>();
        String sql = "SELECT * FROM anggota";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Anggota a = new Anggota();
                a.setIdAnggota(rs.getInt("id_anggota"));
                a.setNama(rs.getString("nama"));
                a.setKelas(rs.getString("kelas"));
                a.setAlamat(rs.getString("alamat"));
                list.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void updateAnggota(Anggota anggota) {
        String sql = "UPDATE anggota SET nama=?, kelas=?, alamat=? WHERE id_anggota=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, anggota.getNama());
            stmt.setString(2, anggota.getKelas());
            stmt.setString(3, anggota.getAlamat());
            stmt.setInt(4, anggota.getIdAnggota());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void hapusAnggota(int idAnggota) {
        String sql = "DELETE FROM anggota WHERE id_anggota=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idAnggota);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ✅ Ambil nama anggota berdasarkan ID
    public String getNamaAnggotaById(int idAnggota) {
        String nama = "";
        String sql = "SELECT nama FROM anggota WHERE id_anggota = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idAnggota);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                nama = rs.getString("nama");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nama;
    }

    // ✅ Reset Auto Increment ke 1
    public void resetAutoIncrement() {
        String sql = "ALTER TABLE anggota AUTO_INCREMENT = 1";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ✅ Hapus semua data anggota dan reset ID ke 1
    public void hapusSemuaAnggota() {
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM anggota");
            stmt.executeUpdate("ALTER TABLE anggota AUTO_INCREMENT = 1");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
