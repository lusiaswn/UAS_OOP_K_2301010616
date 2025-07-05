/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 *
 * @author HP
 */


public class Main {
    public static void main(String[] args) {
        // Jalankan GUI di Event Dispatch Thread (EDT) - best practice untuk Swing
        SwingUtilities.invokeLater(() -> {
            try {
                // Gunakan tampilan default dari sistem operasi
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                System.err.println("Gagal mengatur Look and Feel: " + e.getMessage());
            }

            // Tampilkan form login
            new FrmLogin().setVisible(true);
        });
    }
}
