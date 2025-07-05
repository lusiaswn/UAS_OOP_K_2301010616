/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author HP
 */
public class Pustakawan {
    
    private int idPustakawan;
    private String username;
    private String password;

    public Pustakawan() {
    }

    public Pustakawan(int idPustakawan, String username, String password) {
        this.idPustakawan = idPustakawan;
        this.username = username;
        this.password = password;
    }

    public int getIdPustakawan() {
        return idPustakawan;
    }

    public void setIdPustakawan(int idPustakawan) {
        this.idPustakawan = idPustakawan;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
