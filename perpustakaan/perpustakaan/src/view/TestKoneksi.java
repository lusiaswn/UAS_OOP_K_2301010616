/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.sql.Connection;
import koneksi.Koneksi;
/**
 *
 * @author HP
 */


public class TestKoneksi {
    public static void main(String[] args) {
        Connection conn = Koneksi.getKoneksi();
        if (conn != null) {
            System.out.println("Tes koneksi: Sukses!");
        } else {
            System.out.println("Tes koneksi: Gagal!");
        }
    }
}
