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

public class Pengembalian {

    private int idKembali;
    private int idPinjam;
    private Date tglDikembalikan;
    private int denda;

    // Tambahan untuk tampilan GUI
    private String namaBuku;
    private String namaAnggota;
    private String tglPengembalian;

    public Pengembalian() {
    }

    public Pengembalian(int idKembali, int idPinjam, Date tglDikembalikan, int denda) {
        this.idKembali = idKembali;
        this.idPinjam = idPinjam;
        this.tglDikembalikan = tglDikembalikan;
        this.denda = denda;
    }

    public int getIdKembali() {
        return idKembali;
    }

    public void setIdKembali(int idKembali) {
        this.idKembali = idKembali;
    }

    public int getIdPinjam() {
        return idPinjam;
    }

    public void setIdPinjam(int idPinjam) {
        this.idPinjam = idPinjam;
    }

    public Date getTglDikembalikan() {
        return tglDikembalikan;
    }

    public void setTglDikembalikan(Date tglDikembalikan) {
        this.tglDikembalikan = tglDikembalikan;
    }

    public int getDenda() {
        return denda;
    }

    public void setDenda(int denda) {
        this.denda = denda;
    }

    public String getIdPengembalian() {
        return String.valueOf(idKembali);
    }

    public String getNamaBuku() {
        return namaBuku;
    }

    public void setNamaBuku(String namaBuku) {
        this.namaBuku = namaBuku;
    }

    public String getTglPengembalian() {
        return tglPengembalian;
    }

    public void setTglPengembalian(String tglPengembalian) {
        this.tglPengembalian = tglPengembalian;
    }

    public String getNamaAnggota() {
        return namaAnggota;
    }

    public void setNamaAnggota(String namaAnggota) {
        this.namaAnggota = namaAnggota;
    }
}
