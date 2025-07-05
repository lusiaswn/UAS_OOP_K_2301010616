/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;
import java.util.Date;
/**
 *
 * @author HP
 */


public class Peminjaman {

    private int idPinjam;
    private int idAnggota;
    private int idBuku;
    private Date tglPinjam;
    private Date tglKembali;

    private String namaAnggota;   // tambahan
    private String judulBuku;     // tambahan

    public Peminjaman() {
    }

    public Peminjaman(int idPinjam, int idAnggota, int idBuku, Date tglPinjam, Date tglKembali) {
        this.idPinjam = idPinjam;
        this.idAnggota = idAnggota;
        this.idBuku = idBuku;
        this.tglPinjam = tglPinjam;
        this.tglKembali = tglKembali;
    }

    public int getIdPinjam() {
        return idPinjam;
    }

    public void setIdPinjam(int idPinjam) {
        this.idPinjam = idPinjam;
    }

    public int getIdAnggota() {
        return idAnggota;
    }

    public void setIdAnggota(int idAnggota) {
        this.idAnggota = idAnggota;
    }

    public int getIdBuku() {
        return idBuku;
    }

    public void setIdBuku(int idBuku) {
        this.idBuku = idBuku;
    }

    public Date getTglPinjam() {
        return tglPinjam;
    }

    public void setTglPinjam(Date tglPinjam) {
        this.tglPinjam = tglPinjam;
    }

    public Date getTglKembali() {
        return tglKembali;
    }

    public void setTglKembali(Date tglKembali) {
        this.tglKembali = tglKembali;
    }

    // tambahan getter/setter untuk keperluan laporan
    public String getNamaAnggota() {
        return namaAnggota;
    }

    public void setNamaAnggota(String namaAnggota) {
        this.namaAnggota = namaAnggota;
    }

    public String getJudulBuku() {
        return judulBuku;
    }

    public void setJudulBuku(String judulBuku) {
        this.judulBuku = judulBuku;
    }

    // method tambahan opsional untuk laporan
    public Object getTanggalPinjam() {
        return tglPinjam;
    }

    public Object getId() {
        return idPinjam;
    }
}
