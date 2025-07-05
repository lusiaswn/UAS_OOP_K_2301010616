/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import koneksi.Koneksi;
import java.sql.Connection;
import java.sql.*;
import java.util.*;
import model.Buku;

/**
 *
 * @author HP
 */

public class BukuDAO {

    private Connection conn;

    public BukuDAO() {
        conn = Koneksi.getKoneksi();
    }

    public List<Buku> getAllBuku() {
        List<Buku> list = new ArrayList<>();
        String sql = "SELECT b.*, k.nama_kategori FROM buku b " +
                     "JOIN kategori k ON b.id_kategori = k.id_kategori";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Buku buku = new Buku();
                buku.setIdBuku(rs.getInt("id_buku"));
                buku.setJudul(rs.getString("judul"));
                buku.setPengarang(rs.getString("pengarang"));
                buku.setTahunTerbit(rs.getString("tahun_terbit")); // ✅ Tahun terbit
                buku.setPenerbit(rs.getString("penerbit"));
                buku.setStok(rs.getInt("stok"));
                buku.setIdKategori(rs.getInt("id_kategori"));
                buku.setNamaKategori(rs.getString("nama_kategori"));
                buku.setStatus(rs.getString("status"));
                list.add(buku);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void tambahBuku(Buku buku) {
        String sql = "INSERT INTO buku (judul, pengarang, tahun_terbit, penerbit, stok, id_kategori, status) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, buku.getJudul());
            stmt.setString(2, buku.getPengarang());
            stmt.setString(3, buku.getTahunTerbit());
            stmt.setString(4, buku.getPenerbit());
            stmt.setInt(5, buku.getStok());
            stmt.setInt(6, buku.getIdKategori());
            stmt.setString(7, buku.getStatus());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateBuku(Buku buku) {
        String sql = "UPDATE buku SET judul = ?, pengarang = ?, tahun_terbit = ?, penerbit = ?, stok = ?, " +
                     "id_kategori = ?, status = ? WHERE id_buku = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, buku.getJudul());
            stmt.setString(2, buku.getPengarang());
            stmt.setString(3, buku.getTahunTerbit());
            stmt.setString(4, buku.getPenerbit());
            stmt.setInt(5, buku.getStok());
            stmt.setInt(6, buku.getIdKategori());
            stmt.setString(7, buku.getStatus());
            stmt.setInt(8, buku.getIdBuku());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void hapusBuku(int id) {
        String sql = "DELETE FROM buku WHERE id_buku = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Buku getBukuById(int id) {
        String sql = "SELECT * FROM buku WHERE id_buku = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Buku buku = new Buku();
                buku.setIdBuku(rs.getInt("id_buku"));
                buku.setJudul(rs.getString("judul"));
                buku.setPengarang(rs.getString("pengarang"));
                buku.setTahunTerbit(rs.getString("tahun_terbit"));
                buku.setPenerbit(rs.getString("penerbit"));
                buku.setStok(rs.getInt("stok"));
                buku.setIdKategori(rs.getInt("id_kategori"));
                buku.setStatus(rs.getString("status"));
                return buku;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // ✅ Method Tambahan untuk menghapus semua buku dan reset ID ke 1
    public void hapusSemuaBuku() {
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM buku");
            stmt.executeUpdate("ALTER TABLE buku AUTO_INCREMENT = 1");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
