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


public class Anggota {
    private int idAnggota;
    private String nama;
    private String kelas;
    private String alamat;

    public Anggota() {
    }

    public Anggota(int idAnggota, String nama, String kelas, String alamat) {
        this.idAnggota = idAnggota;
        this.nama = nama;
        this.kelas = kelas;
        this.alamat = alamat;
    }

    public int getIdAnggota() {
        return idAnggota;
    }

    public void setIdAnggota(int idAnggota) {
        this.idAnggota = idAnggota;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
}
