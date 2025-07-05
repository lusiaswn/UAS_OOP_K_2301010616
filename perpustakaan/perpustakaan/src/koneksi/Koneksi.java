/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package koneksi;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author HP
 */




public class Koneksi {

    private static Connection koneksi;

    public static Connection getKoneksi() {
        if (koneksi == null) {
            try {
                String url = "jdbc:mysql://localhost:3306/Perpustakaan"; // Ganti sesuai nama database
                String user = "root";  // Ganti jika MySQL kamu pakai username lain
                String pass = "";      // Ganti jika MySQL kamu pakai password

                // Daftarkan driver MySQL
                Class.forName("com.mysql.cj.jdbc.Driver");

                // Buat koneksi
                koneksi = DriverManager.getConnection(url, user, pass);
                System.out.println("Koneksi ke database berhasil.");
            } catch (ClassNotFoundException | SQLException e) {
                System.err.println("Koneksi gagal: " + e.getMessage());
            }
        }
        return koneksi;
    }
}
