/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;
import java.sql.*;
import java.util.*;
import model.Kategori;
import koneksi.Koneksi;
/**
 *
 * @author HP
 */
public class KategoriDAO {
    
    private Connection conn;

    public KategoriDAO() {
        conn = Koneksi.getKoneksi();
    }

    public void tambahKategori(Kategori kategori) {
        String sql = "INSERT INTO kategori (nama_kategori) VALUES (?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, kategori.getNamaKategori());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Kategori> getAllKategori() {
        List<Kategori> list = new ArrayList<>();
        String sql = "SELECT * FROM kategori";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Kategori k = new Kategori();
                k.setIdKategori(rs.getInt("id_kategori"));
                k.setNamaKategori(rs.getString("nama_kategori"));
                list.add(k);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void hapusKategori(int idKategori) {
        String sql = "DELETE FROM kategori WHERE id_kategori=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idKategori);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateKategori(Kategori k) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
