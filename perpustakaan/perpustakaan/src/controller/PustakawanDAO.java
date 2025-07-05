/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import koneksi.Koneksi;
/**
 *
 * @author HP
 */

public class PustakawanDAO {

    private Connection conn;

    public PustakawanDAO() {
        conn = Koneksi.getKoneksi();
    }

    public boolean login(String username, String password) {
        String sql = "SELECT * FROM pustakawan WHERE username = ? AND password = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // True jika ditemukan
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
